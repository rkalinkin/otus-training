package ru.otus;

public class Demo {
    public static void main(String[] args) {
        TestLoggingInterface testLogging = Ioc.createProxy(new TestLogging(), TestLoggingInterface.class);

        testLogging.calculation(1);
        testLogging.calculation(2, 3);
        testLogging.calculation(4, 5, "test");
    }
}
