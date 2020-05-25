package person.prashant.qpid.publisher;


import javax.jms.JMSException;

public interface OurMessageProducer {
    void publishFixedNumberOfMessages(int messageCount) throws JMSException;
    void keepPublishingMessagesAfterProvidedMilliSeconds(int timeGapInMillis) throws JMSException;
}
