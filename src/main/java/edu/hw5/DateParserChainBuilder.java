package edu.hw5;

import java.time.LocalDate;
import java.util.Optional;

public class DateParserChainBuilder {

    private final AbstractDateParserHandler chain;

    public DateParserChainBuilder() {
        this.chain = new DateFormatParser();
        AbstractDateParserHandler shortDate = new ShortDateFormatParser();
        AbstractDateParserHandler relativeDate = new RelativeDateParser(null);
        chain.setNextLogger(shortDate);
        shortDate.setNextLogger(relativeDate);

    }

    public Optional<LocalDate> parseDate(String input) {
        return chain.parse(input);
    }
}

