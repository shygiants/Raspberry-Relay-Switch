package io.github.shygiants;

import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.trigger.GpioSyncStateTrigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther Sanghoon Yoon (iDBLab, shygiants@gmail.com)
 * @date 2016. 7. 2.
 * @see
 */
public class Controller {

    private final static class Assign implements Switch.OnClickListener {

        private final Logger logger = LoggerFactory.getLogger(Assign.class);

        private final Switch zwitch;
        private final Power power;

        public Assign(Pin switchPin, Pin powerPin) {
            logger.info("Assign is created");
            power = new Power(powerPin);
            zwitch = new Switch(switchPin, this);
        }

        @Override
        public void onClick() {
            logger.info("Switch {} is clicked", zwitch);
            power.switchState();
        }
    }

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    List<Assign> assigns;

    public Controller(int numberOfSwitches) {
        logger.info("Controller is created");

        assigns = new ArrayList<>(numberOfSwitches);
        for (int i = 0; i < numberOfSwitches; i++) {
            assigns.add(i, new Assign(RaspiPin.GPIO_04, RaspiPin.GPIO_05));
        }
        logger.info("{} switches are created", numberOfSwitches);
    }
}
