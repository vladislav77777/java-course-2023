package edu.project3;

import java.net.URI;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class LogReportTest {
    @Test
    void getTotalRequests_ShouldReturnCorrectCount() {
        List<LogRecord> logRecords = List.of(
            new LogRecord("ip1", "user1", null, "request1", "GET", "/resource1", "HTTP/1.1", 200, 100, null, null),
            new LogRecord("ip2", "user2", null, "request2", "POST", "/resource2", "HTTP/1.1", 404, 150, null, null)
        );
        LogReport logReport =
            new LogReport(logRecords, null, null, URI.create("http://example.com"), List.of(Path.of("file1")));

        long totalRequests = logReport.getTotalRequests();

        assertThat(totalRequests).isEqualTo(logRecords.size());
    }

    @Test
    void getMostRequestedResources_ShouldReturnCorrectResourceCount() {
        List<LogRecord> logRecords = List.of(
            new LogRecord("ip1", "user1", null, "request1", "GET", "/resource1", "HTTP/1.1", 200, 100, null, null),
            new LogRecord("ip2", "user2", null, "request2", "POST", "/resource2", "HTTP/1.1", 404, 150, null, null),
            new LogRecord("ip3", "user3", null, "request3", "GET", "/resource1", "HTTP/1.1", 200, 120, null, null),
            new LogRecord("ip4", "user4", null, "request4", "GET", "/resource3", "HTTP/1.1", 200, 130, null, null)
        );
        LogReport logReport =
            new LogReport(logRecords, null, null, URI.create("http://example.com"), List.of(Path.of("file1")));

        Map<String, Long> mostRequestedResources = logReport.getMostRequestedResources();

        var linkedMap = new LinkedHashMap<String, Long>();
        linkedMap.put("/resource1", 2L);
        linkedMap.put("/resource2", 1L);
        linkedMap.put("/resource3", 1L);
        assertThat(mostRequestedResources).containsExactlyEntriesOf(linkedMap);
    }


    @Test
    void testGetMostFrequentStatusCodes() {
        LogRecord record1 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 200, 0, null, null);
        LogRecord record2 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 404, 0, null, null);
        LogRecord record3 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 200, 0, null, null);
        LogRecord record4 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 404, 0, null, null);
        LogRecord record5 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 500, 0, null, null);

        List<LogRecord> logRecords = Arrays.asList(record1, record2, record3, record4, record5);

        LogReport logReport = new LogReport(logRecords, null, null, null, null);

        Map<Integer, Long> mostFrequentStatusCodes = logReport.getMostFrequentStatusCodes();
        var linkedMap = new LinkedHashMap<Integer, Long>();
        linkedMap.put(200, 2L);
        linkedMap.put(404, 2L);
        linkedMap.put(500, 1L);
        assertThat(mostFrequentStatusCodes).containsExactlyEntriesOf(linkedMap);
    }

    @Test
    void testGetMostFrequentMethodRequest() {
        LogRecord record1 = new LogRecord(null, null, OffsetDateTime.now(), null, "GET", null, null, 200, 0, null, null);
        LogRecord record2 = new LogRecord(null, null, OffsetDateTime.now(), null, "POST", null, null, 404, 0, null, null);
        LogRecord record3 = new LogRecord(null, null, OffsetDateTime.now(), null, "GET", null, null, 200, 0, null, null);
        LogRecord record4 = new LogRecord(null, null, OffsetDateTime.now(), null, "POST", null, null, 404, 0, null, null);
        LogRecord record5 = new LogRecord(null, null, OffsetDateTime.now(), null, "PUT", null, null, 500, 0, null, null);

        List<LogRecord> logRecords = Arrays.asList(record1, record2, record3, record4, record5);

        LogReport logReport = new LogReport(logRecords, null, null, null, null);

        Map<String, Long> mostFrequentMethodRequest = logReport.getMostFrequentMethodRequest();
        var linkedMap = new LinkedHashMap<String, Long>();
        linkedMap.put("GET", 2L);
        linkedMap.put("POST", 2L);
        linkedMap.put("PUT", 1L);
        assertThat(mostFrequentMethodRequest).containsExactlyEntriesOf(linkedMap);
    }


    @Test
    void testGetAverageResponseSize() {
        LogRecord record1 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 200, 100, null, null);
        LogRecord record2 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 404, 200, null, null);
        LogRecord record3 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 200, 50, null, null);
        LogRecord record4 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 404, 150, null, null);
        LogRecord record5 = new LogRecord(null, null, OffsetDateTime.now(), null, null, null, null, 500, 120, null, null);

        List<LogRecord> logRecords = Arrays.asList(record1, record2, record3, record4, record5);

        LogReport logReport = new LogReport(logRecords, null, null, null, null);

        double averageResponseSize = logReport.getAverageResponseSize();
        assertThat(averageResponseSize).isEqualTo((100 + 200 + 50 + 150 + 120) / 5.0);
    }

}
