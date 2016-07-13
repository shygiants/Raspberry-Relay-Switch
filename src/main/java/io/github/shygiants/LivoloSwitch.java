package io.github.shygiants;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther Sanghoon Yoon (iDBLab, shygiants@gmail.com)
 * @date 2016. 7. 13.
 * @see
 */
public class LivoloSwitch implements GpioPinListenerDigital {

    public interface OnClickListener {
        void onClick(int buttonNum);
    }

    private static final Logger logger = LoggerFactory.getLogger(LivoloSwitch.class);

    private final GpioController gpioController = GpioFactory.getInstance();

    private final GpioPinDigitalInput button2;
    private final GpioPinDigitalInput button3;

    private final GpioPinDigitalInput pin2;
    private final GpioPinDigitalInput pin3;

    private boolean button2IsRed;
    private boolean button3IsRed;

    private final OnClickListener listener;

    public LivoloSwitch(OnClickListener listener) {
        logger.info("Created");

        this.listener = listener;

        button2 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_00, "Button2");
        button3 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_01, "Button3");
        button2IsRed = button2.isHigh();
        button3IsRed = button3.isHigh();

        pin2 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_02, "Pin2");
        pin3 = gpioController.provisionDigitalInputPin(RaspiPin.GPIO_03, "Pin3");

        button2.addListener(this);
        button3.addListener(this);

        pin2.addListener(this);
        pin3.addListener(this);
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        GpioPin pin = event.getPin();
        String pinName = pin.getName();
        PinState state = event.getState();
        logger.info("Pin {}: State: {}", pin, state);

        synchronized (input) {
            if (!input.isStarted) {
                input.start();
                new Thread(new InterruptHandler()).start();
            }
            switch (pinName) {
                case "Button2":
                    input.onButton2(state.isHigh());
                    break;
                case "Button3":
                    input.onButton3(state.isHigh());
                    break;
            }
        }
    }

    private Input input = new Input();

    private class Input {

        private boolean isStarted = false;
        private boolean button2IsChanged = false;
        private boolean button3IsChanged = false;

        public void onButton2(boolean isHigh) {
            button2IsChanged = button2IsRed != isHigh;
        }

        public void onButton3(boolean isHigh) {
            button3IsChanged = button3IsRed != isHigh;
        }

        public void start() {
            isStarted = true;
        }

        public void updateState() {
            if (button2IsChanged) {
                button2IsRed = !button2IsRed;
                logger.info("Button2 Clicked!");
                listener.onClick(2);
            } else if (button3IsChanged) {
                button3IsRed = !button3IsRed;
                logger.info("Button3 Clicked!");
                listener.onClick(3);
            } else {
                logger.info("Button1 Clicked!");
                listener.onClick(1);
            }

            button2IsRed = button2.isHigh();
            button3IsRed = button3.isHigh();

            button2IsChanged = false;
            button3IsChanged = false;
            isStarted = false;
        }
    }

    private class InterruptHandler implements Runnable {

        @Override
        public void run() {
            logger.info("Start");
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }

            synchronized (input) {
                input.updateState();
            }
            logger.info("End");
        }
    }
}
