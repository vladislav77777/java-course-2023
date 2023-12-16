package edu.project5;

import java.lang.invoke.CallSite;
import java.lang.invoke.LambdaMetafactory;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

@State(Scope.Thread)
@SuppressWarnings({"checkstyle:uncommentedMain"})
public class ReflectionBenchmark {

    private final static int FIVE = 5;
    private final static int TIME = 240;
    private final static String NAME = "name";

    public static void main(String[] args) throws RunnerException {
        Options options = new OptionsBuilder()
            .include(ReflectionBenchmark.class.getSimpleName())
            .shouldFailOnError(true)
            .shouldDoGC(true)
            .mode(Mode.AverageTime)
            .timeUnit(TimeUnit.NANOSECONDS)
            .forks(1)
            .warmupForks(1)
            .warmupIterations(1)
            .warmupTime(TimeValue.seconds(FIVE))
            .measurementIterations(1)
            .measurementTime(TimeValue.seconds(TIME))
            .build();

        new Runner(options).run();
    }

    private Student student;
    private Method reflectionMethod;
    private MethodHandle methodHandle;
    private GetterInterface lambdaGetter;

    @Setup
    public void setup() throws Throwable {
        student = new Student("Alexander", "Biryukov");
        reflectionMethod = Student.class.getMethod(NAME);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        methodHandle = lookup.findVirtual(Student.class, NAME, MethodType.methodType(String.class));

        // LambdaMetafactory setup
        MethodHandles.Lookup caller = MethodHandles.lookup();
        MethodType getterMethodType = MethodType.methodType(String.class, Student.class);

        CallSite site = LambdaMetafactory.metafactory(
            caller,
            "getName",
            MethodType.methodType(GetterInterface.class),
            getterMethodType,
            caller.findVirtual(Student.class, NAME, MethodType.methodType(String.class)),
            getterMethodType
        );

        lambdaGetter = (GetterInterface) (site.getTarget()).invokeExact();

    }

    @Benchmark
    public void directAccess(Blackhole bh) {
        String name = student.name();
        bh.consume(name);
    }

    @Benchmark
    public void reflection(Blackhole bh) throws InvocationTargetException, IllegalAccessException {
        String name = (String) reflectionMethod.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void methodHandles(Blackhole bh) throws Throwable {
        String name = (String) methodHandle.invoke(student);
        bh.consume(name);
    }

    @Benchmark
    public void lambdaMetafactory(Blackhole bh) {
        String name = lambdaGetter.getName(student);
        bh.consume(name);
    }
}

