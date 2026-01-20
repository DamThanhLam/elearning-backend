package com.elearning.elearning_sdk.util;

import org.springframework.stereotype.Component;

import java.time.*;

@Component
public class ClockProxy {

    private final Clock clock = Clock.systemDefaultZone();
    private final ZoneId zoneId = ZoneId.systemDefault();

    public LocalDate nowDate() {
        return this.nowDateTime().toLocalDate();
    }

    public LocalDateTime nowDateTime() {
        return LocalDateTime.now(clock).atZone(zoneId).toLocalDateTime();
    }

    public long toTimestamp(LocalDateTime dateTime) {
        return dateTime.atZone(zoneId).toInstant().toEpochMilli();
    }

    public LocalDate toLocalDate(long millis) {
        return this.toLocalDateTime(millis).toLocalDate();
    }

    public LocalDateTime toLocalDateTime(long millis) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), zoneId);
    }

    public LocalDateTime nowDateTimeIfNull(LocalDateTime value) {
        return value != null ? value : this.nowDateTime();
    }
}
