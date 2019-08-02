package org.lrth.kafkaavro.config.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "my-configugration")
public class MyConfigurationProperties {
    private String schemaRegistry;
    private List<String> objectMappingFiles;
    private DatesConfigProperties dates;
    
    public DatesConfigProperties getDates() {
        return dates;
    }

    public void setDates(DatesConfigProperties dates) {
        this.dates = dates;
    }

    public List<String> getObjectMappingFiles() {
        return objectMappingFiles;
    }

    public void setObjectMappingFiles(List<String> objectMappingFiles) {
        this.objectMappingFiles = objectMappingFiles;
    }

    public String getSchemaRegistry() {
        return schemaRegistry;
    }

    public void setSchemaRegistry(String schemaRegistry) {
        this.schemaRegistry = schemaRegistry;
    }



    public static class DatesConfigProperties {
        private String stringFormat;

        public String getStringFormat() {
            return stringFormat;
        }

        public void setStringFormat(String stringFormat) {
            this.stringFormat = stringFormat;
        }
    }
}
