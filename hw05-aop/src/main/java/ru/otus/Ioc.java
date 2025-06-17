package ru.otus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ioc {

    private static final Logger logger = LoggerFactory.getLogger(Ioc.class);

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target, Class<T> interfaceClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(), new Class<?>[] {interfaceClass}, new LoggingInvocationHandler(target));
    }

    private record LoggingInvocationHandler(Object target) implements InvocationHandler {

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Method targetMethod = target.getClass().getMethod(method.getName(), method.getParameterTypes());

            if (targetMethod.isAnnotationPresent(Log.class)) {
                logger.info("executed method: {}, params:{}", method.getName(), Arrays.toString(args));
            }

            return method.invoke(target, args);
        }
    }
}
