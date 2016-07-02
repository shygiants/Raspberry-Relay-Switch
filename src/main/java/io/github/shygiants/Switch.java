package io.github.shygiants;

import com.pi4j.io.gpio.*;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther Sanghoon Yoon (iDBLab, shygiants@gmail.com)
 * @date 2016. 7. 2.
 * @see
 */
public class Switch implements GpioPinListenerDigital {

    public interface OnClickListener {
        void onClick();
    }

    private final Logger logger = LoggerFactory.getLogger(Switch.class);

    private final GpioController gpioController = GpioFactory.getInstance();
    private final GpioPinDigitalInput pin;

    private final OnClickListener callback;

    private PinState currentState = PinState.LOW;

    public Switch(Pin input, OnClickListener onClickListener) {
        logger.info("Switch is created");

        pin = gpioController.provisionDigitalInputPin(input, "Switch", PinPullResistance.PULL_DOWN);
        callback = onClickListener;

        pin.addListener(this);
    }

    @Override
    public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
        logger.info("Pin State Changed: {}", event.getState());

        if (currentState == PinState.HIGH && event.getState() == PinState.LOW) {
            callback.onClick();
        }

        currentState = event.getState();
    }
}
