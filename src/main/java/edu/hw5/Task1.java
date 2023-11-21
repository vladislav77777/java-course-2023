package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public final class Task1 {

    private Task1() {
    }

    public static String calculateAverageTime(List<String> timeIntervals) {
        long totalSeconds = 0;
        if (timeIntervals.isEmpty()) {
            return "0ч 0м";
        }
        for (String interval : timeIntervals) {
            String[] parts = interval.split(" - ");
            String pattern = "yyyy-MM-dd, HH:mm";
            LocalDateTime start = LocalDateTime.parse(parts[0], DateTimeFormatter.ofPattern(pattern));
            LocalDateTime end = LocalDateTime.parse(parts[1], DateTimeFormatter.ofPattern(pattern));
            Duration duration = Duration.between(start, end);
            totalSeconds += duration.getSeconds();
        }
        long averageSeconds = totalSeconds / timeIntervals.size();
        Duration averageDuration = Duration.ofSeconds(averageSeconds);
        long hours = averageDuration.toHours();
        long minutes = averageDuration.minusHours(hours).toMinutes();

        return hours + "ч " + minutes + "м";

    }
}
