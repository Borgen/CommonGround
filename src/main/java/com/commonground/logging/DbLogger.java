package com.commonground.logging;

import ch.qos.logback.classic.Logger;
import org.slf4j.LoggerFactory;

public class DbLogger {
    public static final Logger logger = (Logger) LoggerFactory.getLogger(DbLogger.class);
}
