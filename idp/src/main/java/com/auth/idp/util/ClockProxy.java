package com.auth.idp.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class ClockProxy {

    public static LocalDateTime toLocalDateTime(Date date) {
        return date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime();
    }
}
