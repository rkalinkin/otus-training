package ru.otus.testing.example;

import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"java:S2699"})
class LifeCycleTest {
    private static final Logger logger = LoggerFactory.getLogger(LifeCycleTest.class);

    // Подготовительные мероприятия. Метод выполнится один раз, перед всеми тестами
    @BeforeAll
    static void globalSetUp() {
        logger.info("@BeforeAll");
    }

    // Подготовительные мероприятия. Метод выполнится перед каждым тестом
    @BeforeEach
    void setUp() {
        logger.info("\n@BeforeEach. ");
        logger.info("Экземпляр тестового класса: {}", Integer.toHexString(hashCode()));
    }

    // Сам тест
    @Test
    void anyTest1() {
        logger.info("@Test: anyTest1. ");
        logger.info("Экземпляр тестового класса: {}", Integer.toHexString(hashCode()));
    }

    // Еще тест
    @Test
    void anyTest2() {
        logger.info("@Test: anyTest2. ");
        logger.info("Экземпляр тестового класса: {}", Integer.toHexString(hashCode()));
    }

    // Завершающие мероприятия. Метод выполнится после каждого теста
    @AfterEach
    void tearDown() {
        logger.info("@AfterEach. ");
        logger.info("Экземпляр тестового класса: {}", Integer.toHexString(hashCode()));
    }

    // Завершающие мероприятия. Метод выполнится один раз, после всех тестов
    @AfterAll
    static void globalTearDown() {
        logger.info("\n@AfterAll");
    }
}
