// src/main/java/Main.java
package ru.otus.logging.ex00;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        System.out.println("Запуск приложения...");

        // Логируем сообщения разных уровней
        logger.trace("TRACE сообщение");
        logger.debug("DEBUG сообщение");
        logger.info("INFO сообщение");
        logger.warn("WARN сообщение");
        logger.error("ERROR сообщение");

        System.out.println("Лог записан в файл logs/application.log");
    }
}
