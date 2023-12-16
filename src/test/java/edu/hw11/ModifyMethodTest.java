package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.MethodDelegation;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

public class ModifyMethodTest {
    @Test
    public void testModifyClass() throws IllegalAccessException, InstantiationException {
        // новый класс ArithmeticUtils с измененным поведением
        Class<?> modifiedClass = createModifiedClass(ArithmeticUtils.class);

        // экземпляр нового класса
        Object modifiedInstance = modifiedClass.newInstance();

        // вызываем метод sum с измененным поведением
        ArithmeticUtils modifiedArithmeticUtils = (ArithmeticUtils) modifiedInstance;
        int result = modifiedArithmeticUtils.sum(3, 4);

        assertThat(result).isEqualTo(12);

    }

    private static Class<?> createModifiedClass(Class<?> originalClass) {
        return new ByteBuddy()
            .subclass(originalClass)
            .method(named("sum")).intercept(MethodDelegation.to(SumInterceptor.class))
            .make()
            .load(originalClass.getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
            .getLoaded();
    }

    public static class SumInterceptor {
        public static int intercept(
            @net.bytebuddy.asm.Advice.Argument(0) int a,
            @net.bytebuddy.asm.Advice.Argument(1) int b
        ) {
            // измененное поведение -> уумножаем вместо сложения
            return a * b;
        }
    }

    public static class ArithmeticUtils {
        public int sum(int a, int b) {
            return a + b;
        }
    }
}
