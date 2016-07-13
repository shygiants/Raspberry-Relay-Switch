package io.github.shygiants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @auther Sanghoon Yoon (iDBLab, shygiants@gmail.com)
 * @date 2016. 7. 2.
 * @see
 */
public class Controller implements LivoloSwitch.OnClickListener {

    private final Logger logger = LoggerFactory.getLogger(Controller.class);

    private final List<Light> lights;
    private final LivoloSwitch livoloSwitch;

    public Controller(List<Light> lights) {
        logger.info("Created");

        if (lights.size() != 3) {
            logger.error("The number of lights should be 3");
            System.exit(1);
        }

        this.lights = lights;
        livoloSwitch = new LivoloSwitch(this);
    }

    @Override
    public void onClick(int buttonNum) {
        lights.get(buttonNum - 1).toggle();
    }
}
