package edu.hw10;

public class FibCalculatorImpl implements FibCalculator {
    @Override
    public long fib(int number) {
        if (number <= 0) {
            throw new IllegalArgumentException("Input should be a positive integer");
        }

        if (number == 1 || number == 2) {
            return 1;
        }

        long fib1 = 1;
        long fib2 = 1;

        for (int i = 2 + 1; i <= number; i++) {
            long temp = fib1 + fib2;
            fib1 = fib2;
            fib2 = temp;
        }

        return fib2;
    }
}
