package person.prashant.qpid.producer;

public class Runner {

    private static final String url = "amqp://localhost:5672?jms.username=guest&jms.password=guest&jms.clientID=clientid&amqp.vhost=default";
    private static final boolean isTopic = false;
    private static final String topicOrQueueName = "test";


    public static void main(String[] args) throws Exception {
        OurMessagePublisher messagePublisher = new ConnectionFactoryBasedMessagePublisher(url, isTopic, topicOrQueueName);
        new RandomStringMessageProducer(messagePublisher).publishFixedNumberOfMessages(1000);
    }
}
