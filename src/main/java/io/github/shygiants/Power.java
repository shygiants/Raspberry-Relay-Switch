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
    private final GpioPinDigitalOutput pin;

    private boolean state;

    public Power(Pin output) {
        logger.info("Power is created");
        pin = gpioController.provisionDigitalOutputPin(output, "Power", PinState.HIGH);
        state = false;
    }

    public void switchState() {
        setState(!state);
        logger.info("Power state is switched: {}", state? "On" : "Off");
    }

    public boolean setState(boolean state) {
        if (this.state != state) {
            do {
                pin.setState(!state);
            } while (pin.getState() != (state? PinState.LOW : PinState.HIGH));

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
