package org.lrth.kafkaavro.integration;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface FurtherIntegrationChannels {
    String ALTERNATIVE_INPUT = "another-input";
    
    String WITHOUT_FRUITS_OUTPUT = "without-fruits-output";
    String NULL_DATE_OUTPUT = "null-date-output";

    @Input(ALTERNATIVE_INPUT)
    SubscribableChannel alternativeInput();
    
    @Output(WITHOUT_FRUITS_OUTPUT)
    MessageChannel withoutFruitsOutput();
    
    @Output(NULL_DATE_OUTPUT)
    MessageChannel nullDateOutput();
}
