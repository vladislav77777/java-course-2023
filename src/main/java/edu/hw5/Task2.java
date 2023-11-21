package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public final class Task2 {

    private static final int MONTHS = 12;
    private static final int THIRTEEN = 13;

    private Task2() {
    }

    public static List<LocalDate> findFridaysThe13th(int year) {
        List<LocalDate> fridaysThe13th = new ArrayList<>();

        for (int month = 1; month <= MONTHS; month++) {
            LocalDate date = LocalDate.of(year, month, THIRTEEN);
            if (date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                fridaysThe13th.add(date);
            }
        }

        return fridaysThe13th;
    }

    public static LocalDate findNextFridayThe13th(LocalDate date) {
        return date.with(TemporalAdjusters.next(DayOfWeek.FRIDAY)).withDayOfMonth(THIRTEEN);
    }

}
