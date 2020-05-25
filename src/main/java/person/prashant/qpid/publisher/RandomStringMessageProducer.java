package person.prashant.qpid.publisher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RandomStringMessageProducer implements OurMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomStringMessageProducer.class);
    private final OurMessagePublisher messagePublisher;

    public RandomStringMessageProducer(OurMessagePublisher messagePublisher) throws Exception {
        this.messagePublisher = messagePublisher;
    }

    public void publishFixedNumberOfMessages(int messageCount) throws JMSException {
        LOGGER.info("Creating {} messages", messageCount);
        List<String> messages = new ArrayList<>();
        for(int i = 0; i < messageCount; i++){
            messages.add(UUID.randomUUID().toString());
        }
        this.messagePublisher.publish(messages.toArray(new String [messageCount]));
    }

    public void keepPublishingMessagesAfterProvidedMilliSeconds(int timeGapInMillis) throws JMSException {
        while(this != null){
            this.messagePublisher.publish(UUID.randomUUID().toString());
            try {
                TimeUnit.MILLISECONDS.sleep(timeGapInMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
