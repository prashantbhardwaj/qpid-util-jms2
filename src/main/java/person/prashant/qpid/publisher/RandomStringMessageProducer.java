package person.prashant.qpid.publisher;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.prashant.qpid.BaseMessageProducer;

import javax.jms.JMSException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RandomStringMessageProducer extends BaseMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RandomStringMessageProducer.class);

    public RandomStringMessageProducer(OurMessagePublisher messagePublisher) throws Exception {
        super(messagePublisher);
    }

    @Override
    protected String getMessage() {
        return UUID.randomUUID().toString();
    }
}
