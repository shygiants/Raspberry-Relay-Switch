package io.github.shygiants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther Sanghoon Yoon (iDBLab, shygiants@gmail.com)
 * @date 2016. 7. 13.
 * @see
 */
public abstract class Light {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    private boolean onOff = false;

    protected abstract void setState(boolean onOff);

    // These return whether light is changed
    public final boolean turnOn() {
        boolean isChanged = !onOff;
        setState(true);
        onOff = true;
        logger.info("On");
        return isChanged;
    }
    public final boolean turnOff() {
        boolean isChanged = onOff;
        setState(false);
        onOff = false;
        logger.info("Off");
        return isChanged;
    }

    // These returns whether light is on
    public final boolean toggle() {
        if (onOff)
            turnOff();
        else
            turnOn();

        return onOff;
    }


    public final boolean isOn() {
        return onOff;
    }
}
