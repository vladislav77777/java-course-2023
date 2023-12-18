package edu.hw10;

public class MyClass {
    @NotNull
    private String name;

    @Min(1)
    @Max(120)
    private int age;

    public static MyClass create() {
        return new MyClass();
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
