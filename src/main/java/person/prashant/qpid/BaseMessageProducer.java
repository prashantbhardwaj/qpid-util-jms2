package person.prashant.qpid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.prashant.qpid.publisher.OurMessageProducer;
import person.prashant.qpid.publisher.OurMessagePublisher;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public abstract class BaseMessageProducer implements OurMessageProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseMessageProducer.class);
    private final OurMessagePublisher messagePublisher;

    protected BaseMessageProducer(OurMessagePublisher messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    public void publishFixedNumberOfMessages(long messageCount) throws JMSException {
        LOGGER.info("Creating {} messages", messageCount);
        List<String> messages = new ArrayList<>();
        for(int i = 0; i < messageCount; i++){
            messages.add(this.getMessage());
        }
        this.messagePublisher.publish(messages);
    }

    public void publishFixedNumberOfMessagesButTakeABreaksInBetween(long messageCount, int breakAfter) throws JMSException {
        LOGGER.info("Creating {} messages", messageCount);
        List<String> messages = new ArrayList<>();
        for(int i = 0; i < messageCount; i++){
            messages.add(this.getMessage());
        }
        this.messagePublisher.publish();
    }

    public void keepPublishingMessagesAfterProvidedMilliSeconds(int timeGapInMillis) throws JMSException {
        while(this != null){
            this.messagePublisher.publish(this.getMessage());
            try {
                TimeUnit.MILLISECONDS.sleep(timeGapInMillis);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract String getMessage();
}
