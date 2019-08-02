package org.lrth.kafkaavro.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.dozer.CustomConverter;
import org.dozer.DozerBeanMapper;
import org.lrth.kafkaavro.config.properties.MyConfigurationProperties;
import org.lrth.kafkaavro.dozerconverters.JodaDateTimePreserver;
import org.lrth.kafkaavro.dozerconverters.TimestampStringConverter;
import org.lrth.kafkaavro.util.MyDateFormatter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DozerConfiguration {
    private MyConfigurationProperties configProperties;
    private MyDateFormatter dateFormatter;

    public DozerConfiguration(
        MyConfigurationProperties configProperties,
        MyDateFormatter dateFormatter
    ) {
        this.configProperties = configProperties;
        this.dateFormatter = dateFormatter;
    }

    @Bean
    public DozerBeanMapper beanMapper() {
        DozerBeanMapper mapper = new DozerBeanMapper();

        ArrayList<CustomConverter> customConverters = new ArrayList<>();
        Map<String, CustomConverter> customConvertersWithId = new HashMap<>();

        customConvertersWithId.put("timeStampConverter",
            new TimestampStringConverter(dateFormatter)
        );

        customConvertersWithId.put("jodaTimePreserver", new JodaDateTimePreserver());

        mapper.setMappingFiles(configProperties.getObjectMappingFiles());
        mapper.setCustomConverters(customConverters);
        mapper.setCustomConvertersWithId(customConvertersWithId);

        return mapper;
    }
}
