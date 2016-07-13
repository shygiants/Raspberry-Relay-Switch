package io.github.shygiants;

import com.pi4j.io.gpio.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther Sanghoon Yoon (iDBLab, shygiants@gmail.com)
 * @date 2016. 7. 2.
 * @see
 */
public class Power {

    private final Logger logger = LoggerFactory.getLogger(Power.class);

    private final GpioController gpioController = GpioFactory.getInstance();
    private final GpioPinDigitalOutput powerPin;
    private final GpioPinDigitalOutput statusPin;

    private boolean state;

    public Power(Pin powerPin, Pin statusPin) {
        logger.info("Power is created");
        this.statusPin = gpioController.provisionDigitalOutputPin(statusPin, "LED", PinState.LOW);
        this.powerPin = gpioController.provisionDigitalOutputPin(powerPin, "Power", PinState.HIGH);
        state = false;
    }

    public void switchState() {
        setState(!state);
        logger.info("Power state is switched: {}", state? "On" : "Off");
    }

    public boolean setState(boolean state) {
        if (this.state != state) {
            do {
                powerPin.setState(!state);
                statusPin.setState(state);
            } while (powerPin.getState() != (state? PinState.LOW : PinState.HIGH)
                    || statusPin.getState() != (state? PinState.HIGH : PinState.LOW));

            this.state = state;
            return true;
        } else {
            return false;
        }
    }

    public boolean isOn() {
        return state;
    }
}
