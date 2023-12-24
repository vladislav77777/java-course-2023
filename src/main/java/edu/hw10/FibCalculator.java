package edu.hw10;

public interface FibCalculator {
    @Cache(persist = true)
    long fib(int number);
}
