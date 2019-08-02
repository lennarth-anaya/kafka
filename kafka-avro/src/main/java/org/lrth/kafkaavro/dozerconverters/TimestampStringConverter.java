package org.lrth.kafkaavro.dozerconverters;

import org.dozer.DozerConverter;
import org.lrth.kafkaavro.util.MyDateFormatter;

public class TimestampStringConverter
        extends DozerConverter<Long, String>
{
    private MyDateFormatter dateFormatter;

    public TimestampStringConverter(MyDateFormatter dateFormatter) {
        super(Long.class, String.class);
        this.dateFormatter = dateFormatter;
    }

    @Override
    public String convertTo(Long sourceValue, String destinationCurrentValue) {
        String timezonedDate = dateFormatter.parseDate(sourceValue);
        return timezonedDate;
    }

    @Override
    public Long convertFrom(String source, Long destination) {
        throw new UnsupportedOperationException("This converter is [so far] intended to" +
                " be used in a one-way conversion mapping due to input-output pipeline nature");
    }
}
