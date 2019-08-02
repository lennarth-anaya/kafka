package org.lrth.kafkaavro.rest;

import org.lrth.kafkaavro.integration.FurtherIntegrationChannels;
import org.lrth.kafkaavro.model.avro.SomeInputRecord;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleRestController {
    private FurtherIntegrationChannels channels;
    
    public SampleRestController(FurtherIntegrationChannels channels) {
        this.channels = channels;
    }

    @PostMapping(path = "/kafka-web-topic", consumes = "application/json")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void kafkaWebTopic(@RequestBody SomeInputRecord webInput) {
        channels.alternativeInput().send(
            new GenericMessage<>(webInput));
    }
}
