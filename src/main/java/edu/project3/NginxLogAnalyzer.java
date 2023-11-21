package edu.project3;

/* Генерация отчётов происходит в папку 'LogsOut' директории проекта(.md и .adoc),
 * также в процессе реализации были добавлены 2 новые статистики - отчёт
 *  по дням(MONDAY...-...SUNDAY) и по методам запроса(GET, POST, HEAD,...)
 * */

// Examples:
// --path src/**/2023*.txt --from 2017-08-31 --format markdown   -> всё в папке src/test/resources/project3Test/logs
// --path https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs --from 2011-08-31 --format adoc

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings({"checkstyle:uncommentedMain", "checkstyle:magicnumber", "checkstyle:MultipleStringLiterals"})
public final class NginxLogAnalyzer {

    private NginxLogAnalyzer() {
    }

    private static final int LOG_LENGTH = 10;
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String MARKDOWN = "markdown";
    public static final Logger LOGGER = Logger.getLogger(NginxLogAnalyzer.class.getName());

    public static void main(String[] args) {
        parseArguments(args);
    }

    public static void parseArguments(String[] args) {
        if (args.length < 2) {
            LOGGER.severe(
                "Usage: java -jar nginx-log-stats.jar --path"
                    + " <log-path> [--from <from-date>] [--to <to-date>] [--format <output-format>]");
            System.exit(1);
        }

        String logPath = args[1];

        LocalDateTime fromDate = null;
        LocalDateTime toDate = null;
        String outputFormat = MARKDOWN;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);

        for (int i = 2; i < args.length; i += 2) {
            switch (args[i]) {
                case "--from":
                    fromDate = LocalDate.parse(args[i + 1], dtf).atStartOfDay();
                    break;
                case "--to":
                    toDate = LocalDate.parse(args[i + 1], dtf).atStartOfDay();
                    break;

                case "--format":
                    outputFormat = args[i + 1];
                    break;
                default:
                    LOGGER.severe("Unknown option: " + args[i]);
                    return;
            }
        }

        try { // try to choose URI or file(s)
            if (logPath.startsWith("http")) {
                List<LogRecord> logRecords = readLogsFromUrl(new URI(logPath));
                logRecords = filterLogs(logRecords, fromDate, toDate);
                LogReport logReport = new LogReport(logRecords, fromDate, toDate, new URI(logPath), null);
                generateReport(logReport, outputFormat);
            } else {
                List<Path> files = findAndReadMatchingFiles(logPath);
                List<LogRecord> logRecords = new ArrayList<>();

                for (Path file : files) {
                    logRecords.addAll(readLogsFromFile(file));
                }
                logRecords = filterLogs(logRecords, fromDate, toDate);
                LogReport logReport = new LogReport(logRecords, fromDate, toDate, null, files);
                generateReport(logReport, outputFormat);
            }

        } catch (IOException | URISyntaxException e) {
//            e.printStackTrace();
        }
    }

    /**
     * Метод проходит по файловому дереву начиная с директории проекта и ищет
     * файлы, соответствующие шаблону logPathPattern.
     *
     * @param logPathPattern glob pattern
     * @return Список подходящий логов
     * @throws IOException В случае ошибки считывания перебора файловой системы
     */
    static List<Path> findAndReadMatchingFiles(String logPathPattern) throws IOException {
        Path dir = Paths.get(System.getProperty("user.dir"));  // Относительный путь - текущий проект
        // текущая директория с проектом (можно изменить на родителей)
        String linuxDir = dir.toString().replace(File.separator, "/");
        // glob работает с '/'
        List<Path> files = new ArrayList<>();
        PathMatcher pathMatcher = FileSystems.getDefault().getPathMatcher("glob:" + linuxDir + "/" + logPathPattern);
        Files.walkFileTree(dir, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                if (pathMatcher.matches(file)) {
                    files.add(file);
//                    System.out.println(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return files;
    }

    private static List<LogRecord> readLogsFromFile(Path filePath) throws IOException {

        try (Stream<String> lines = Files.lines(filePath, Charset.defaultCharset())) {
            return lines.map(NginxLogAnalyzer::parseLogRecord)
                .collect(Collectors.toList());
        }

    }

    private static List<LogRecord> readLogsFromUrl(URI url) throws IOException {
        try (InputStream inputStream = url.toURL().openStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

            return bufferedReader.lines()
                .map(NginxLogAnalyzer::parseLogRecord)
                .collect(Collectors.toList());
        }
    }

    static LogRecord parseLogRecord(String logLine) {
        String[] parts = logLine.split("\\s+(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
//        Arrays.stream(parts).forEach(System.out::println);
        if (parts.length < LOG_LENGTH) {
            LOGGER.severe("Не удалось корректно разбить строку на части");
            return null;
        }

        String ipAddress = parts[0];
        String remoteUser = parts[2];
        String timestampStr =
            parts[3].substring(1) + " " + parts[4].substring(0, parts[4].length() - 1);
        OffsetDateTime timestamp =
            OffsetDateTime.parse(timestampStr, DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH));

        String request = parts[5].substring(1, parts[5].length() - 1);
        String[] parseRequest = request.split(" ");
        String methodRequest = parseRequest[0];
        String uriRequest = parseRequest[1];
        String protocolRequest = parseRequest[2];

        int statusCode = Integer.parseInt(parts[6]);
        long bytesSent = Long.parseLong(parts[7]);
        String referer = parts[8];
        String userAgent = parts[9];

        return new LogRecord(
            ipAddress,
            remoteUser,
            timestamp,
            request,
            methodRequest,
            uriRequest,
            protocolRequest,
            statusCode,
            bytesSent,
            referer,
            userAgent
        );
    }

    static List<LogRecord> filterLogs(
        List<LogRecord> logRecords,
        LocalDateTime fromDate,
        LocalDateTime toDate
    ) {

        List<LogRecord> filteredLogs = new ArrayList<>();
        ZoneOffset zoneOffset = ZoneOffset.UTC;
        if (fromDate != null) {
            filteredLogs = logRecords.stream()
                .filter(log -> log.timestamp()
                    .isAfter(OffsetDateTime.ofInstant(fromDate.toInstant(zoneOffset), zoneOffset)))
                .collect(Collectors.toList());
        }
        if (toDate != null) {
            filteredLogs = filteredLogs.stream()
                .filter(log ->
                    log.timestamp()
                        .isBefore(OffsetDateTime.ofInstant(toDate.toInstant(zoneOffset), zoneOffset)))
                .collect(Collectors.toList());
        }

        return filteredLogs;
    }

    private static void generateReport(LogReport logReport, String outputFormat) {
        switch (outputFormat.toLowerCase()) {
            case MARKDOWN -> generateMarkdownReport(logReport);
            case "adoc" -> generateAdocReport(logReport);
            default -> LOGGER.severe("Unsupported output format: " + outputFormat);
        }
    }

    private static void generateMarkdownReport(LogReport logReport) {
        String outputPath = "LogsOut/outputStatistics.md";
        StringBuilder markdownReport = new StringBuilder();
        LOGGER.info("Generating... ");
        markdownReport.append("#### Общая информация\n");
        markdownReport.append("| Метрика | Значение |\n");
        markdownReport.append("|---------|----------|\n");
        if (logReport.getFiles() == null) {
            markdownReport.append("| URI | ").append(logReport.getUri().toString()).append(" |\n");
        } else {
            markdownReport.append("| Файл(-ы) | ");
            for (int i = 0; i < logReport.getFiles().size(); i++) {
                String fileString = logReport.getFiles().get(i).toString();
                fileString = fileString.substring(1 + fileString.lastIndexOf("\\"));
                markdownReport.append("'").append(fileString).append("'");
                if (i != logReport.getFiles().size() - 1) {
                    markdownReport.append(", ");
                }
            }
            markdownReport.append(" |\n");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);
        markdownReport.append("| Начальная дата | ")
            .append(logReport.getFromDate() == null ? " --- " : dtf.format(logReport.getFromDate())).append(" |\n");
        markdownReport.append("| Конечная дата | ")
            .append(logReport.getToDate() == null ? " --- " : dtf.format(logReport.getToDate())).append(" |\n");
        markdownReport.append("| Количество запросов | ").append(logReport.getTotalRequests()).append(" |\n");
        markdownReport.append("| Средний размер ответа | ").append(logReport.getAverageResponseSize()).append(" |\n");

        markdownReport.append("\n#### Запрашиваемые ресурсы\n");
        markdownReport.append("| Ресурс | Количество |\n");
        markdownReport.append("|--------|------------|\n");
        Map<String, Long> mostRequestedResources = logReport.getMostRequestedResources();
        mostRequestedResources.forEach((resource, count) ->
            markdownReport.append("| ").append(resource).append(" | ").append(count).append(" |\n"));

        markdownReport.append("\n#### Коды ответа\n");
        markdownReport.append("| Код | Имя | Количество |\n");
        markdownReport.append("|-----|-----|------------|\n");
        Map<Integer, Long> mostFrequentStatusCodes = logReport.getMostFrequentStatusCodes();
        mostFrequentStatusCodes.forEach((code, count) ->
            markdownReport.append("| ").append(code).append(" | ").append(getStatusName(code)).append(" | ")
                .append(count).append(" |\n"));

        markdownReport.append("\n#### Методы запроса\n");
        markdownReport.append("| Метод | Количество |\n");
        markdownReport.append("|-----|------------|\n");
        Map<String, Long> mostFrequentMethodRequests = logReport.getMostFrequentMethodRequest();
        mostFrequentMethodRequests.forEach((method, count) ->
            markdownReport.append("| ").append(method).append(" | ").append(count).append(" |\n"));

        Map<DayOfWeek, Long> statisticsByDayOfWeek = logReport.getStatisticsByDayOfWeek();
        markdownReport.append("\n#### Статистика по дням недели\n");
        markdownReport.append("| День недели | Количество |\n");
        markdownReport.append("|-------------|------------|\n");
        statisticsByDayOfWeek.forEach((dayOfWeek, count) ->
            markdownReport.append("| ").append(dayOfWeek.toString()).append(" | ").append(count).append(" |\n")
        );

        try {
            Files.write(Paths.get(outputPath), markdownReport.toString().getBytes());
            LOGGER.info("Markdown report saved to: " + outputPath);
        } catch (IOException e) {
//            e.printStackTrace();
        }
    }

    private static void generateAdocReport(LogReport logReport) {
        String outputPath = "LogsOut/outputStatistics.adoc";
        StringBuilder asciiDocReport = new StringBuilder();
        LOGGER.info("Generating...");

        asciiDocReport.append("== Общая информация\n");
        asciiDocReport.append("|===\n");
        asciiDocReport.append("| Метрика | Значение\n");

        if (logReport.getFiles() == null) {
            asciiDocReport.append("| URI | `").append(logReport.getUri().toString()).append("`\n");
        } else {
            asciiDocReport.append("| Файл(-ы) | ");
            for (int i = 0; i < logReport.getFiles().size(); i++) {
                String fileString = logReport.getFiles().get(i).toString();
                fileString = fileString.substring(1 + fileString.lastIndexOf("\\"));
                asciiDocReport.append("'").append(fileString).append("'");
                if (i != logReport.getFiles().size() - 1) {
                    asciiDocReport.append(", ");
                }
            }
            asciiDocReport.append("\n");
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_PATTERN, Locale.ENGLISH);

        asciiDocReport.append("| Начальная дата | ")
            .append(logReport.getFromDate() == null ? " --- " : dtf.format(logReport.getFromDate())).append(" |\n");
        asciiDocReport.append("Конечная дата | ")
            .append(logReport.getToDate() == null ? " --- " : dtf.format(logReport.getToDate())).append(" |\n");
        asciiDocReport.append("Количество запросов | ").append(logReport.getTotalRequests()).append(" |\n");
        asciiDocReport.append("Средний размер ответа | ").append(logReport.getAverageResponseSize()).append(" |\n");
        asciiDocReport.append("|===\n");

        asciiDocReport.append("\n== Запрашиваемые ресурсы\n");
        asciiDocReport.append("|===\n");
        asciiDocReport.append("| Ресурс | Количество\n");
        Map<String, Long> mostRequestedResources = logReport.getMostRequestedResources();
        mostRequestedResources.forEach((resource, count) ->
            asciiDocReport.append("\n| ").append(resource).append("\n | ").append(count));
        asciiDocReport.append("\n|===\n");

        asciiDocReport.append("\n== Коды ответа\n");
        asciiDocReport.append("|===\n");
        asciiDocReport.append("| Код | Имя | Количество\n");
        Map<Integer, Long> mostFrequentStatusCodes = logReport.getMostFrequentStatusCodes();
        mostFrequentStatusCodes.forEach((code, count) ->
            asciiDocReport.append("\n| ").append(code).append("\n | ").append(getStatusName(code)).append("\n | ")
                .append(count));
        asciiDocReport.append("\n|===\n");

        asciiDocReport.append("\n== Методы запроса\n");
        asciiDocReport.append("|===\n");
        asciiDocReport.append("| Метод | Количество\n");
        Map<String, Long> mostFrequentMethodRequests = logReport.getMostFrequentMethodRequest();
        mostFrequentMethodRequests.forEach((method, count) ->
            asciiDocReport.append("\n| ").append(method).append("\n | ").append(count));
        asciiDocReport.append("\n|===\n");

        Map<DayOfWeek, Long> statisticsByDayOfWeek = logReport.getStatisticsByDayOfWeek();

        asciiDocReport.append("\n== Статистика по дням недели\n");
        asciiDocReport.append("|===\n");
        asciiDocReport.append("| День недели | Количество \n");
        statisticsByDayOfWeek.forEach((dayOfWeek, count) ->
            asciiDocReport.append("\n| ").append(dayOfWeek.toString()).append("\n | ").append(count)
        );
        asciiDocReport.append("\n|===\n");

        try {
            Files.write(Paths.get(outputPath), asciiDocReport.toString().getBytes());
            LOGGER.info("AsciiDoc report saved to: " + outputPath);
        } catch (IOException e) {
            // Handle the exception as needed
        }
    }

    private static String getStatusName(int statusCode) {
        return switch (statusCode) {
            case 200 -> "OK";
            case 206 -> "Partial Content";
            case 302 -> "Found(Redirection)";
            case 304 -> "Not Modified";
            case 403 -> "Forbidden";
            case 404 -> "Not Found";
            case 416 -> "Requested Range Not Satisfiable";
            case 500 -> "Internal Server Error";
            default -> "Unknown";
        };
    }
}
