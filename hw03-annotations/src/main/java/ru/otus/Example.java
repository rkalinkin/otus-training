package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.*;

public class Example {

    private static final Logger logger = LoggerFactory.getLogger(Example.class);

    @Before
    public void before_each() {
        logger.info("Before ...");
    }

    @After
    public void after_each() {
        logger.info("After ...");
    }

    @Test
    public void first_test() {
        logger.info("First Test Success");
    }

    @Test
    public void second_test() {
        throw new RuntimeException("Second Test Failed");
    }

    @Test
    public void third_test() {
        logger.info("Third Test Success");
    }
}
