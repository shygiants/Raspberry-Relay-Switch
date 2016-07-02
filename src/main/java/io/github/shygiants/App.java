package io.github.shygiants;

import com.pi4j.io.gpio.RaspiPin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @auther Sanghoon Yoon (iDBLab, shygiants@gmail.com)
 * @date 2016. 7. 2.
 * @see
 */
public class App {

    private final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String [] args) {
        new Controller(1);

        while(true) {
            try {
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
