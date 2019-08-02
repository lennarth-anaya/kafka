package org.lrth.kafkaavro.integration;

import org.dozer.DozerBeanMapper;
import org.lrth.kafkaavro.model.avro.SomeInputRecord;
import org.lrth.kafkaavro.model.avro.SomeOutputRecord;
import org.lrth.kafkaavro.model.avro.SomeOutputWOFruitsRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;

@Component
@EnableBinding({Processor.class, FurtherIntegrationChannels.class})
public class SampleTransformer {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
    private DozerBeanMapper beanMapper;
    private FurtherIntegrationChannels furtherChannels;
    
    @Autowired
    public SampleTransformer(
        DozerBeanMapper beanMapper,
        FurtherIntegrationChannels furtherIntegrationChannels
    ) {
        this.beanMapper = beanMapper;
        this.furtherChannels = furtherIntegrationChannels;
    }
    
    @ServiceActivator(
        inputChannel = Processor.INPUT,
        outputChannel = Processor.OUTPUT,
        // allows to return null to skip Processor.OUTPUT:
        requiresReply = "false"
    )
    public SomeOutputRecord transform(SomeInputRecord input) {
        SomeOutputRecord output = beanMapper.map(
            input, SomeOutputRecord.class, "inputOutput");

        SomeOutputWOFruitsRecord outputWithoutFruits = beanMapper.map(
            output, SomeOutputWOFruitsRecord.class, "outputToOutputPreservingDateTime");
       
        // send messages to a certain output different than final output
        furtherChannels.withoutFruitsOutput().send(new GenericMessage<>(outputWithoutFruits));
       
        if (output.getNULLABLESTRDATE() == null) {
            // use alternative final output rather than Processor.OUTPUT
            // to handle null date
            furtherChannels.nullDateOutput().send(new GenericMessage<>(output));
            
            // prevent Processor.OUTPUT
            return null;
        }

        // send response to Processor.OUTPUT
        return output;
    }
    
    @ServiceActivator(
        inputChannel = FurtherIntegrationChannels.ALTERNATIVE_INPUT,
        outputChannel = Processor.OUTPUT,
        // allows to return null to skip Processor.OUTPUT:
        requiresReply = "false"
    )
    public SomeOutputRecord transformFromAlternative(SomeInputRecord input) {
        logger.debug("Received message from WEB topic: {}", input);
        return transform(input);
    }
}
