package edu.hw10;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Task1Test {

    @Test
    public void testGenerateRandomObjectForClassWithFactoryMethod() throws Exception {
        Task1 rog = new Task1();

        MyClass myClass = rog.nextObject(MyClass.class, "create");
        System.out.println(myClass.getName());
        System.out.println(myClass.getAge());
        assertNotNull(myClass);
        validateAnnotations(myClass);
    }

    @Test
    public void testGenerateRandomObjectForRecord() throws Exception {
        Task1 rog = new Task1();

        MyRecord myRecord = rog.nextObject(MyRecord.class);
        System.out.println(myRecord.stringValue());
        System.out.println(myRecord.intValue());

        assertNotNull(myRecord);
        validateAnnotations(myRecord);
    }

    private void validateAnnotations(Object obj) {
        for (Field field : obj.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(NotNull.class)) {
                field.setAccessible(true);
                assertNotNull(getFieldValue(obj, field), "Field " + field.getName() + " is annotated with @NotNull");
            }

            if (field.isAnnotationPresent(Min.class)) {
                field.setAccessible(true);
                assertTrue(
                    (int) getFieldValue(obj, field) >= field.getAnnotation(Min.class).value(),
                    "Field " + field.getName() + " is annotated with @Min"
                );
            }

            if (field.isAnnotationPresent(Max.class)) {
                field.setAccessible(true);
                assertTrue(
                    (int) getFieldValue(obj, field) <= field.getAnnotation(Max.class).value(),
                    "Field " + field.getName() + " is annotated with @Max"
                );
            }
        }
    }

    private Object getFieldValue(Object obj, Field field) {
        try {
            return field.get(obj);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error accessing field value", e);
        }
    }
}
