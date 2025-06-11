package ru.otus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.annotations.After;
import ru.otus.annotations.Before;
import ru.otus.annotations.Test;

public class Runner {

    private static final Logger logger = LoggerFactory.getLogger(Runner.class);

    private int totalTests = 0;
    private int passedTests = 0;
    private int failedTests = 0;

    public static void run(Class<?> testClass) {
        new Runner().runTests(testClass);
    }

    private void runTests(Class<?> testClass) {
        Method[] methods = testClass.getDeclaredMethods();

        List<Method> beforeMethods = getAnnotatedMethods(methods, Before.class);
        List<Method> afterMethods = getAnnotatedMethods(methods, After.class);
        List<Method> testMethods = getAnnotatedMethods(methods, Test.class);

        totalTests = testMethods.size();

        for (Method testMethod : testMethods) {
            runTest(testClass, beforeMethods, testMethod, afterMethods);
        }

        failedTests = totalTests - passedTests;
        printSummary();
    }

    private List<Method> getAnnotatedMethods(
            Method[] methods, Class<? extends java.lang.annotation.Annotation> annotation) {
        List<Method> result = new ArrayList<>();
        for (Method method : methods) {
            if (method.isAnnotationPresent(annotation)) {
                result.add(method);
            }
        }
        return result;
    }

    private void runTest(Class<?> testClass, List<Method> beforeMethods, Method testMethod, List<Method> afterMethods) {
        try {
            Object instance = testClass.getDeclaredConstructor().newInstance();

            try {
                invokeAll(instance, beforeMethods);

                try {
                    invokeTestMethod(instance, testMethod);
                    passedTests++;
                } catch (InvocationTargetException e) {
                    Throwable cause = e.getCause();
                    logger.error("Test failed: {} - {}", testMethod.getName(), cause.getMessage(), cause);
                } catch (Exception e) {
                    logger.error("Error during test execution: {}", testMethod.getName(), e);
                }

            } finally {
                try {
                    invokeAll(instance, afterMethods);
                } catch (Exception e) {
                    logger.error("Exception in @After methods of '{}'", testMethod.getName(), e);
                }
            }

        } catch (Exception e) {
            logger.error("Failed to create instance or unexpected error in '{}'", testMethod.getName(), e);
        }
    }

    private void invokeAll(Object instance, List<Method> methods) throws Exception {
        for (Method method : methods) {
            method.invoke(instance);
        }
    }

    private void invokeTestMethod(Object instance, Method testMethod) throws Exception {
        testMethod.invoke(instance);
    }

    private void printSummary() {
        logger.info(
                """
                \n--- Test Summary ---
                Total tests: {}
                Passed: {}
                Failed: {}""",
                totalTests,
                passedTests,
                failedTests);
    }
}
