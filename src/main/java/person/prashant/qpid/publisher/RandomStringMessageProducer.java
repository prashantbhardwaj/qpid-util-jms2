package person.prashant.qpid.publisher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.util.UUID;

public class RandomStringMessageProducer implements OurMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomStringMessageProducer.class);
    private final OurMessagePublisher messagePublisher;

    public RandomStringMessageProducer(OurMessagePublisher messagePublisher) throws Exception {
        this.messagePublisher = messagePublisher;
    }

    public void publishFixedNumberOfMessages(int messageCount) throws JMSException {
        for(int i = 0; i < messageCount; i++){
            LOGGER.info("Publishing a message number - {}", i);
            this.messagePublisher.publish(UUID.randomUUID().toString());
        }
    }

    public void keepPublishingMessagesAfterProvidedMilliSeconds(int timeGapInMillis) {

    }
}
