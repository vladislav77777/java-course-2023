package edu.hw10;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

// caching proxy implementation
class CacheProxy implements InvocationHandler {
    private final Object target;
    private final Map<Method, Map<Integer, Long>> cache;
    private final String cacheDirectory;

    CacheProxy(Object target, String cacheDirectory) {
        this.target = target;
        this.cache = new HashMap<>();
        this.cacheDirectory = cacheDirectory;
    }

    public static <T> T create(T target, Class<?> interfaceType, String cacheDirectory) {
        return (T) Proxy.newProxyInstance(
            interfaceType.getClassLoader(),
            new Class<?>[] {interfaceType},
            new CacheProxy(target, cacheDirectory)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.isAnnotationPresent(Cache.class)) {
            Cache cacheAnnotation = method.getAnnotation(Cache.class);
            if (!cache.containsKey(method)) {
                cache.put(method, new HashMap<>());
            }

            int arg = (int) args[0]; // arg - the ordinal number of the Fibonacci number

            if (cache.get(method).containsKey(arg)) { // already exist
                return cache.get(method).get(arg);
            } else {                                  // if not -> cashing it
                long result = (long) method.invoke(target, args);
                cache.get(method).put(arg, result);

                if (cacheAnnotation.persist()) {
                    persistResult(method, arg, result);
                }

                return result;
            }
        } else {
            return method.invoke(target, args);
        }
    }

    private void persistResult(Method method, int arg, long result) { // in the test we created fib_5 and fib_10.cache
        String fileName = String.format("%s_%d.cache", method.getName(), arg);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(new File(
            cacheDirectory,
            fileName
        )))) {
            outputStream.writeObject(result);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }
}
