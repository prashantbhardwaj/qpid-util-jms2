package person.prashant.qpid.publisher.manual;

import person.prashant.qpid.publisher.NumberMessageProducer;
import person.prashant.qpid.publisher.OurMessagePublisher;
import person.prashant.qpid.publisher.RandomStringMessageProducer;

public class Runner {

    private static final String url = "amqp://localhost:5672?jms.username=guest&jms.password=guest&jms.clientID=clientid&amqp.vhost=default";
    private static final boolean isTopic = false;
    private static final String topicOrQueueName = "test";


    public static void main(String[] args) throws Exception {
        OurMessagePublisher messagePublisher = new ManualConnectionFactoryToMessagePublisher(url, isTopic, topicOrQueueName);
        //new RandomStringMessageProducer(messagePublisher).keepPublishingMessagesAfterProvidedMilliSeconds(1);
        new NumberMessageProducer(messagePublisher).publishFixedNumberOfMessages(30000);
    }
}
