package edu.hw11;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.method.MethodDescription;
import net.bytebuddy.description.modifier.Visibility;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.Implementation;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.Argument;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bytecode.ByteCodeAppender;
import net.bytebuddy.jar.asm.Label;
import net.bytebuddy.jar.asm.MethodVisitor;
import net.bytebuddy.jar.asm.Opcodes;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FibonacciGeneratorTest {

    @Test
    public void testGeneratedClass() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        Class<?> dynamicType = createDynamicType().load(getClass().getClassLoader()).getLoaded();
        Object instance = dynamicType.newInstance();

        Method fibMethod = findMethod(dynamicType, "fib", int.class);
        long result = (long) fibMethod.invoke(instance, 10);

        assertEquals(55, result);
    }

    private DynamicType.Unloaded<?> createDynamicType() {
        return new ByteBuddy()
            .subclass(Object.class)
            .name("FibonacciGenerator")
            .defineMethod("fib", long.class, Visibility.PUBLIC)
            .withParameter(int.class, "n")
            .intercept(MethodDelegation.to(FibonacciInterceptor.class))
            .make();
    }

    private static class FibonacciMethodByteCodeAppender implements ByteCodeAppender {

        @Override
        public @NotNull Size apply(MethodVisitor mv, Implementation.@NotNull Context context, MethodDescription instrumentedMethod) {
            // uенерация кода метода
            mv.visitCode();

            // cоздание переменных
            mv.visitVarInsn(Opcodes.ILOAD, 1); // загрузка аргумента n

            // Создание условия: if (n <= 1)
            Label ifConditionLabel = new Label();
            mv.visitJumpInsn(Opcodes.IFLE, ifConditionLabel);


            mv.visitInsn(Opcodes.LCONST_1); // fib(0) = 1
            mv.visitInsn(Opcodes.LCONST_1); // fib(1) = 1

            // цикл вычисления числа Фибоначчи
            Label loopStartLabel = new Label();
            mv.visitLabel(loopStartLabel);
            mv.visitInsn(Opcodes.LADD); // fib(n) = fib(n-1) + fib(n-2)
            mv.visitVarInsn(Opcodes.IINC, 1); // уувеличение n
            mv.visitVarInsn(Opcodes.ILOAD, 1); // загруз текущего n
            mv.visitJumpInsn(Opcodes.IFLE, loopStartLabel); // повторение цикла если n > 1

            mv.visitLabel(ifConditionLabel);
            mv.visitInsn(Opcodes.LRETURN); // возврващение результата

            mv.visitMaxs(2, 2); // расчет максимального стека и локальных переменных
            mv.visitEnd();

            return new Size(2, instrumentedMethod.getStackSize());
        }
    }

    private Method findMethod(Class<?> type, String name, Class<?>... parameterTypes) {
        for (Method method : type.getDeclaredMethods()) {
            if (method.getName().equals(name) && sameParameters(method.getParameterTypes(), parameterTypes)) {
                return method;
            }
        }
        throw new RuntimeException("Method not found");
    }

    private boolean sameParameters(Class<?>[] a, Class<?>[] b) {
        if (a.length != b.length) {
            return false;
        }
        for (int i = 0; i < a.length; i++) {
            if (!a[i].equals(b[i])) {
                return false;
            }
        }
        return true;
    }

    public static class FibonacciInterceptor {

        @RuntimeType
        public static long fib(@Argument(0) int n) {
            if (n <= 0) {
                new FibonacciMethodByteCodeAppender();
                return 0;
            } else if (n == 1) {
                return 1;
            } else {
                return fib(n - 1) + fib(n - 2);
            }
        }
    }
}
