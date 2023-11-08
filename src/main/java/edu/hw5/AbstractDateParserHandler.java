package edu.hw5;

import java.time.LocalDate;
import java.util.Optional;

public abstract class AbstractDateParserHandler {
    protected AbstractDateParserHandler nextHandler;

    public AbstractDateParserHandler(AbstractDateParserHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public AbstractDateParserHandler() {
    }

    abstract Optional<LocalDate> handleRequest(String input);

    public Optional<LocalDate> parse(String input) {
        Optional<LocalDate> handleRequest = handleRequest(input);

        if (handleRequest.isEmpty()) {
            if (nextHandler == null) {
                return Optional.empty();
            }
            return nextHandler.parse(input);
        } else {
            return handleRequest(input);
        }
    }

    public void setNextLogger(AbstractDateParserHandler nextHandler) {
        this.nextHandler = nextHandler;
    }
}
