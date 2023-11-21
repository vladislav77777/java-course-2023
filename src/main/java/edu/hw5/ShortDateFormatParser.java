package edu.hw5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ShortDateFormatParser extends AbstractDateParserHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/uuuu");

    public ShortDateFormatParser() {
    }

    @Override
    public Optional<LocalDate> handleRequest(String input) {
        try {
            LocalDate date = LocalDate.parse(input, formatter);

            return Optional.ofNullable(date);
        } catch (Exception e) {
            return Optional.empty();
        }

    }

}
