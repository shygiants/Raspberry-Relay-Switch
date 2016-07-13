package io.github.shygiants;

import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @auther Sanghoon Yoon (iDBLab, shygiants@gmail.com)
 * @date 2016. 7. 2.
 * @see
 */
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String [] args) {

        List<Light> lights = new ArrayList<>(3);
        lights.add(new RelayLight(RaspiPin.GPIO_04));
        lights.add(new RelayLight(RaspiPin.GPIO_05));
        lights.add(new RelayLight(RaspiPin.GPIO_06));
        new Controller(lights);



        while(true) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
