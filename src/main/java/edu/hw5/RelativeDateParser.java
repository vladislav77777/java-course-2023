package edu.hw5;

import java.time.LocalDate;
import java.util.Optional;

public class RelativeDateParser extends AbstractDateParserHandler {

    public RelativeDateParser(AbstractDateParserHandler nextHandler) {
        super(nextHandler);
    }

    @Override
    public Optional<LocalDate> handleRequest(String input) {
        int days;
        if (input.equals("tomorrow")) {
            days = 1;
        } else if (input.equals("yesterday")) {
            days = -1;
        } else if (input.equals("today")) {
            days = 0;
        } else if (input.endsWith(" days ago") || input.endsWith(" day ago")) {
            days = Integer.parseInt(input.split(" ")[0]) * -1;
        } else {
            return Optional.empty();
        }

        LocalDate date = LocalDate.now().plusDays(days);
        return Optional.of(date);

    }

}
