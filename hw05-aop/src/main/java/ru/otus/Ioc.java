package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ioc {

    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Class<T> interfaceClass) {
        Set<MethodSignature> logAnnotatedMethods = getAnnotatedMethods(target);
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class<?>[] {interfaceClass},
                new LoggingInvocationHandler(target, logAnnotatedMethods));
    }

    private static <T> Set<MethodSignature> getAnnotatedMethods(T target) {
        return Arrays.stream(target.getClass().getDeclaredMethods())
                .filter(m -> m.isAnnotationPresent(Log.class))
                .map(MethodSignature::new)
                .collect(Collectors.toSet());
    }

    private record MethodSignature(String name, Class<?>[] parameterTypes) {
        public MethodSignature(Method method) {
            this(method.getName(), method.getParameterTypes());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MethodSignature(String name1, Class<?>[] types))) return false;
            return Objects.equals(name, name1) && Arrays.deepEquals(parameterTypes, types);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(name);
            result = 31 * result + Arrays.deepHashCode(parameterTypes);
            return result;
        }
    }

    private record LoggingInvocationHandler(Object target, Set<MethodSignature> logAnnotatedMethods)
            implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            MethodSignature currentMethod = new MethodSignature(method);
            if (logAnnotatedMethods.contains(currentMethod)) {
                logger.info("executed method: {}, params: {}", method.getName(), Arrays.toString(args));
            }

            return method.invoke(target, args);
        }
    }
}
