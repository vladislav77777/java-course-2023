package edu.project3;

import java.time.OffsetDateTime;

public record LogRecord(
    String ipAddress,
    String remoteUser,
    OffsetDateTime timestamp,
    String request,
    String methodRequest,
    String uriRequest,
    String protocolRequest,
    int statusCode,
    long bytesSent,
    String referer,
    String userAgent
) {
}
