package person.prashant.qpid.producer;


import javax.jms.JMSException;

public interface OurMessageProducer {
    void publishFixedNumberOfMessages(int messageCount) throws JMSException;
    void keepPublishingMessagesAfterProvidedMilliSeconds(int timeGapInMillis);
}
