package com.mariamkatamashvlii.gym;

import lombok.Generated;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.logging.Level;
import java.util.logging.Logger;


@SpringBootApplication
@Generated
public class GymApplication {
    public static void main(String[] args) throws SecurityException{
        setLoggingLevels();
        SpringApplication.run(GymApplication.class, args);
    }

    private static void setLoggingLevels() {
        Logger springLogger = Logger.getLogger("org.springframework");
        springLogger.setLevel(Level.FINE);

        Logger tomcatLogger = Logger.getLogger("org.apache.catalina");
        tomcatLogger.setLevel(Level.FINE);

        Logger globalLogger = Logger.getLogger("");
        globalLogger.setLevel(Level.INFO);
    }
}
