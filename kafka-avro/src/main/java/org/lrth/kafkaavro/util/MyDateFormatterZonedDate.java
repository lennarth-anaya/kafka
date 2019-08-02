package org.lrth.kafkaavro.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.lrth.kafkaavro.config.properties.MyConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class MyDateFormatterZonedDate implements MyDateFormatter {
    private DateTimeFormatter formatter;

    public MyDateFormatterZonedDate(MyConfigurationProperties config) {
        String stringDateFormat = config.getDates().getStringFormat();
        formatter = DateTimeFormatter.ofPattern(stringDateFormat);
    }

    @Override
    public String parseDate(Long dateTimeMillis) {
        Instant instant = Instant.ofEpochMilli(dateTimeMillis);
        ZonedDateTime ldt = ZonedDateTime.of(
                LocalDateTime.ofInstant(instant, ZoneId.systemDefault()),
                ZoneId.systemDefault());
        return ldt.format(formatter);
    }
}
