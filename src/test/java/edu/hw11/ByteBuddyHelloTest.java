package edu.hw11;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import net.bytebuddy.implementation.FixedValue;
import org.junit.jupiter.api.Test;
import static net.bytebuddy.matcher.ElementMatchers.named;
import static org.assertj.core.api.Assertions.assertThat;

public class ByteBuddyHelloTest {

    @Test
    public void testHello() throws IllegalAccessException, InstantiationException {
        // новый класс с использованием ByteBuddy
        Class<?> dynamicType = new ByteBuddy()
            .subclass(Object.class) // новый класс будет подклассом Object
            .method(named("toString")) // выбираем метод toString
            .intercept(FixedValue.value("Hello, ByteBuddy!")) // устанавливаем фиксированное значение для метода
            .make()
            .load(ByteBuddyHelloTest.class.getClassLoader(), ClassLoadingStrategy.Default.WRAPPER)
            .getLoaded();

        // экземпляр нового класса
        Object dynamicObject = dynamicType.newInstance();

        assertThat(dynamicObject.toString()).isEqualTo("Hello, ByteBuddy!");
    }

}
