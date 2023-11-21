package edu.project3;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class NginxLogAnalyzerTest {

    @ParameterizedTest
    @CsvSource({
        "93.180.71.3, -, [17/May/2015:08:05:23 +0000], GET /downloads/product_1 HTTP/1.1, 304, 0, -, Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)",
        "80.91.33.133, -, [17/May/2015:08:05:23 +0000], GET /downloads/product_2 HTTP/1.1, 200, 0, -, Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)"
    })
    void parseLogRecord_ValidInput_ParsesCorrectly(
        String ipAddress, String remoteUser, String timestampStr, String request,
        int statusCode, long bytesSent, String referer, String userAgent
    ) {

        String logLine = ipAddress + " - " + remoteUser + " " + timestampStr + " " +
            "\"" + request + "\" " + statusCode + " " + bytesSent + " " +
            "\"" + referer + "\" \"" + userAgent + "\"";

        LogRecord logRecord = NginxLogAnalyzer.parseLogRecord(logLine);

        assertNotNull(logRecord);
        assertEquals(ipAddress, logRecord.ipAddress());
        assertEquals(remoteUser, logRecord.remoteUser());
        assertEquals("GET", logRecord.methodRequest());
        assertEquals("HTTP/1.1", logRecord.protocolRequest());
        assertEquals(OffsetDateTime.parse(
            "17/May/2015:08:05:23 +0000",
            DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH)
        ), logRecord.timestamp());
        assertEquals(statusCode, logRecord.statusCode());
        assertEquals(bytesSent, logRecord.bytesSent());

    }

    @Test
    void parseLogRecord_InvalidInput_ReturnsNull() {
        String invalidLogLine = "Invalid log line";
        LogRecord logRecord = NginxLogAnalyzer.parseLogRecord(invalidLogLine);
        assertNull(logRecord);
    }

//    @Test
//    void testFindAndReadMatchingFiles_ValidInput_ParsesCorrectly() throws IOException {
//        String logPathPattern = "src/**/2023*.txt";
//
//        List<Path> files = NginxLogAnalyzer.findAndReadMatchingFiles(logPathPattern);
//
//        assertThat(files).isNotNull();
//        assertThat(files).hasSize(2);
//
//        Path expectedPath1 = Paths.get(
//            System.getProperty("user.dir"),
//            "src\\test\\resources\\project3Test\\logs\\2\\",
//            "2023-11-17.txt"
//        );
//        Path expectedPath2 = Paths.get(
//            System.getProperty("user.dir"),
//            "src\\test\\resources\\project3Test\\logs\\2\\",
//            "2023-others.txt"
//        );
//        System.out.println(expectedPath2);
//        assertThat(files).containsExactlyInAnyOrder(expectedPath1, expectedPath2);
//    }

    @Test
    void testFindAndReadMatchingFiles_InvalidInput_ReturnsEmptyList() throws IOException {
        String logPathPattern = "src/**/2077*.txt";

        List<Path> files = NginxLogAnalyzer.findAndReadMatchingFiles(logPathPattern);

        assertThat(files).isEmpty();
    }

    @Test
    void testFilterLogs() {
        List<LogRecord> logRecords = List.of(
            new LogRecord(
                "95.226.236.238",
                "-",
                OffsetDateTime.parse("2022-11-17T20:48:53Z"),
                "POST /Synergized_Graphic%20Interface-leading%20edge.css HTTP/1.1",
                "GET",
                "/Synergized_Graphic%20Interface-leading%20edge.css",
                "HTTP/1.1",
                200,
                3005,
                "-",
                "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_8_10 rv:4.0; en-US) AppleWebKit/533.30.7 (KHTML, like Gecko) Version/4.2 Safari/533.30.7"
            ),
            new LogRecord(
                "120.133.248.154",
                "-",
                OffsetDateTime.parse("2021-11-10T20:08:53Z"),
                "GET /archive/Open-source/stable.php HTTP/1.1",
                "POST",
                "/archive/Open-source/stable.php",
                "HTTP/1.1",
                200,
                2556,
                "-",
                "Mozilla/5.0 (Windows; U; Windows NT 6.0) AppleWebKit/535.38.2 (KHTML, like Gecko) Version/5.1 Safari/535.38.2"
            ),
            new LogRecord(
                "120.133.248.154",
                "-",
                OffsetDateTime.parse("2021-11-17T20:08:53Z"),
                "GET /archive/Open-source/stable2.php HTTP/1.1",
                "POST",
                "/archive/Open-source/stable2.php",
                "HTTP/1.1",
                404,
                2556,
                "-",
                "Mozilla/5.0 (Windows; U; Windows NT 6.0) AppleWebKit/535.38.2 (KHTML, like Gecko) Version/5.1 Safari/535.38.2"
            ),
            new LogRecord(
                "120.133.248.154",
                "-",
                OffsetDateTime.parse("2023-11-17T20:08:54Z"),
                "GET /archive/Open-source/stable.php HTTP/1.1",
                "GET",
                "/archive/Open-source/stable.php",
                "HTTP/1.1",
                302,
                2556,
                "-",
                "Mozilla/5.0 (Windows; U; Windows NT 6.0) AppleWebKit/535.38.2 (KHTML, like Gecko) Version/5.1 Safari/535.38.2"
            )
        );

        LocalDateTime fromDate = LocalDateTime.parse("2021-11-20T00:00:00");
        LocalDateTime toDate = LocalDateTime.parse("2023-12-10T23:59:59");

        List<LogRecord> filteredLogs = NginxLogAnalyzer.filterLogs(logRecords, fromDate, toDate);

        assertThat(filteredLogs).isNotNull();
        assertThat(filteredLogs).hasSize(2);
        assertThat(filteredLogs).contains(logRecords.get(0)).contains(logRecords.get(3));

    }

}
