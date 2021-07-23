package com.commonground;

import ch.qos.logback.classic.Logger;
import com.commonground.services.GroupService;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonGroundApplication {

    final static Logger logger = (Logger) LoggerFactory.getLogger("com.commonground.db.logger");

    public static void main(String[] args) {

        SpringApplication.run(CommonGroundApplication.class, args);

        logger.info("Application started.");
    }

}
