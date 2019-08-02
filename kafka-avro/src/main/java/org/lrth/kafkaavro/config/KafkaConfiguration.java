package org.lrth.kafkaavro.config;

import org.lrth.kafkaavro.config.properties.MyConfigurationProperties;
import org.springframework.cloud.stream.schema.client.ConfluentSchemaRegistryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfiguration {
    @Bean
    public ConfluentSchemaRegistryClient confluentSchemaRegistryClient(MyConfigurationProperties properties)
    {
        ConfluentSchemaRegistryClient client = new ConfluentSchemaRegistryClient();
        client.setEndpoint(properties.getSchemaRegistry());
        return client;
    }
}
