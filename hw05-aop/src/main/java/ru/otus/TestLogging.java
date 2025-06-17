package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLogging implements TestLoggingInterface {
    private static final Logger logger = LoggerFactory.getLogger(TestLogging.class);

    @Log
    public void calculation(int param1) {
        logger.info("calculation(int)");
    }

    @Log
    public void calculation(int param1, int param2) {
        logger.info("calculation(int, int)");
    }

    public void calculation(int param1, int param2, String param3) {
        logger.info("calculation(int, int, String)");
    }
}
