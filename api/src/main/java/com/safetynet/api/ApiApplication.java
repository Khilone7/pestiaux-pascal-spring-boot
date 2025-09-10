package com.safetynet.api;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

    private static final Logger logger = LogManager.getLogger(ApiApplication.class);

    public static void main(String[] args) {
        logger.debug("Test de lancement de l'appli");
        SpringApplication.run(ApiApplication.class, args);
    }

}
