package org.lrth.kafkaavro.dozerconverters;

import org.dozer.DozerConverter;
import org.joda.time.DateTime;

/**
 * Rather than a converter, this is a preserver since
 * dozer's default converter is just creating new DateTimes
 * and dismissing source values.
 */
public class JodaDateTimePreserver
        extends DozerConverter<DateTime,DateTime>
{
    public JodaDateTimePreserver() {
        super(DateTime.class, DateTime.class);
    }

    @Override
    public DateTime convertTo(DateTime source, DateTime destination) {
        return destination != null ? new DateTime(destination) : null;
    }

    @Override
    public DateTime convertFrom(DateTime source, DateTime destination) {
        return source != null ? new DateTime(source) : null;
    }
}