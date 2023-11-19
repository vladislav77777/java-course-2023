package edu.project3;

import java.net.URI;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LogReport {

    private final List<LogRecord> logRecords;
    private final LocalDateTime fromDate;
    private final LocalDateTime toDate;
    private final URI uri;
    private final List<Path> files;

    public LogReport(
        List<LogRecord> logRecords,
        LocalDateTime fromDate,
        LocalDateTime toDate,
        URI uri,
        List<Path> files
    ) {
        this.logRecords = logRecords;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.uri = uri;
        this.files = files;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public URI getUri() {
        return uri;
    }

    public List<Path> getFiles() {
        return files;
    }

    // Метод для подсчета общего числа запросов
    public long getTotalRequests() {
        return logRecords.size();
    }

    // Метод для определения наиболее часто запрашиваемых ресурсов
    public Map<String, Long> getMostRequestedResources() {
        Map<String, Long> resourceCount = new LinkedHashMap<>();

        for (LogRecord logRecord : logRecords) {
            String resource = logRecord.uriRequest();
            resourceCount.put(resource, resourceCount.getOrDefault(resource, 0L) + 1);
        }

        return resourceCount.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    // Метод для определения наиболее частых кодов ответа
    public Map<Integer, Long> getMostFrequentStatusCodes() {
        Map<Integer, Long> statusCodeCount = new LinkedHashMap<>();

        for (LogRecord logRecord : logRecords) {
            int statusCode = logRecord.statusCode();
            statusCodeCount.put(statusCode, statusCodeCount.getOrDefault(statusCode, 0L) + 1);
        }

        return statusCodeCount.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    // Метод для определения наиболее популярных методов запроса[NEW]
    public Map<String, Long> getMostFrequentMethodRequest() {
        Map<String, Long> methodRequestCount = new LinkedHashMap<>();

        for (LogRecord logRecord : logRecords) {
            String methodRequest = logRecord.methodRequest();
            methodRequestCount.put(methodRequest, methodRequestCount.getOrDefault(methodRequest, 0L) + 1);
        }

        return methodRequestCount.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }

    // Метод для расчета среднего размера ответа сервера
    public double getAverageResponseSize() {
        long totalSize = 0;

        for (LogRecord logRecord : logRecords) {
            totalSize += logRecord.bytesSent();
        }

        return logRecords.isEmpty() ? 0 : (double) totalSize / logRecords.size();
    }

    // Метод для расчета статистики по дням[NEW]
    public Map<DayOfWeek, Long> getStatisticsByDayOfWeek() {
        return logRecords.stream()
            .collect(Collectors.groupingBy(
                logRecord -> logRecord.timestamp().getDayOfWeek(),
                Collectors.counting()
            )).entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                Map.Entry::getValue,
                (e1, e2) -> e1,
                LinkedHashMap::new
            ));
    }
}

