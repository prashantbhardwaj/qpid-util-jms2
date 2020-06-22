package person.prashant.qpid.publisher.jndi;

import person.prashant.qpid.publisher.OurMessagePublisher;
import person.prashant.qpid.publisher.RandomStringMessageProducer;
import person.prashant.qpid.publisher.own.ConnectionFactoryBasedMessagePublisher;

public class Runner {

    public static void main(String[] args) throws Exception {
        OurMessagePublisher messagePublisher = new JndiBasedConnectionMessagePublisher();
        new RandomStringMessageProducer(messagePublisher).keepPublishingMessagesAfterProvidedMilliSeconds(1);
    }
}
