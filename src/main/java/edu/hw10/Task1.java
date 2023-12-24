package edu.hw10;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.Arrays;
import java.util.Random;

public class Task1 {
    private final Random random;

    public Task1() {
        this.random = new Random();
    }

    public <T> T nextObject(Class<T> clazz, String factoryMethod) throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<T> constructor;
        if (clazz.isRecord()) {
            return generateRandomRecord(clazz);
        }
        constructor = clazz.getDeclaredConstructor();
        constructor.setAccessible(true);
        T instance;
        if (factoryMethod != null && !factoryMethod.isEmpty()) {
            Method factory = clazz.getDeclaredMethod(factoryMethod);
            factory.setAccessible(true);
            instance = (T) factory.invoke(null);
        } else {
            instance = constructor.newInstance();
        }

        for (var field : clazz.getDeclaredFields()) {

            if (field.isAnnotationPresent(NotNull.class) || field.isAnnotationPresent(Min.class)
                || field.isAnnotationPresent(Max.class)) {
                field.setAccessible(true);
                Class<?> fieldType = field.getType();
                if (fieldType.equals(int.class) || fieldType.equals(Integer.class)) {
                    field.setInt(instance, generateRandomInt(
                        field.getAnnotation(Min.class),
                        field.getAnnotation(Max.class)
                    ));
                } else if (fieldType.equals(String.class)) {
                    field.set(instance, generateRandomString());
                } // ...
            }
        }

        return instance;
    }

    public <T> T generateRandomRecord(Class<T> clazz)
        throws NoSuchMethodException, SecurityException, InvocationTargetException, InstantiationException,
        IllegalAccessException {
        Class<?>[] componentTypes = Arrays.stream(clazz.getRecordComponents())
            .map(RecordComponent::getType)
            .toArray(Class<?>[]::new);
        var constructor = clazz.getDeclaredConstructor(componentTypes);

        T instance = null;
        for (var field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNull.class) || field.isAnnotationPresent(Min.class)
                || field.isAnnotationPresent(Max.class)) {
                field.setAccessible(true);
                instance = constructor.newInstance(generateRandomInt(
                    field.getAnnotation(Min.class),
                    field.getAnnotation(Max.class)
                ), generateRandomString());
                break;
            }
        }
        return instance;
    }

    public <T> T nextObject(Class<T> clazz) throws NoSuchMethodException,
        IllegalAccessException, InvocationTargetException, InstantiationException {
        return nextObject(clazz, "");
    }

    private String generateRandomString() {
        final int leftLimit = 97; // letter 'a'
        final int rightLimit = 122; // letter 'z'
        final int targetStringLength = 10;
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }

    private int generateRandomInt(Min minAnnotation, Max maxAnnotation) {
        int min = (minAnnotation != null) ? minAnnotation.value() : Integer.MIN_VALUE;
        int max = (maxAnnotation != null) ? maxAnnotation.value() : Integer.MAX_VALUE;
        return random.nextInt(max - min + 1) + min;
    }
}
