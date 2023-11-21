package edu.hw5;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class DateFormatParser extends AbstractDateParserHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DateFormatParser() {
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
