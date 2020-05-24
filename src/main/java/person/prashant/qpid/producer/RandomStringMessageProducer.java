package person.prashant.qpid.producer;


import javax.jms.JMSException;
import java.util.UUID;

public class RandomStringMessageProducer implements OurMessageProducer {

    private final OurMessagePublisher messagePublisher;

    public RandomStringMessageProducer(OurMessagePublisher messagePublisher) throws Exception {
        this.messagePublisher = messagePublisher;
    }

    public void publishFixedNumberOfMessages(int messageCount) throws JMSException {
        for(int i = 0; i < messageCount; i++){
            this.messagePublisher.publish(UUID.randomUUID().toString());
        }
    }

    public void keepPublishingMessagesAfterProvidedMilliSeconds(int timeGapInMillis) {

    }
}
