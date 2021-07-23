package com.commonground;

import ch.qos.logback.classic.Logger;
import com.commonground.logging.DbLogger;
import com.commonground.services.GroupService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CommonGroundApplication {

    public static void main(String[] args) {

        SpringApplication.run(CommonGroundApplication.class, args);

        DbLogger.logger.info("Application started.");
    }

}
