package person.prashant.qpid.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.prashant.qpid.BaseMessageProducer;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class NumberMessageProducer extends BaseMessageProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(NumberMessageProducer.class);
    private AtomicLong currentMessage = new AtomicLong(0);

    public NumberMessageProducer(OurMessagePublisher messagePublisher) throws Exception {
        super(messagePublisher);
    }

    @Override
    protected String getMessage() {
        return String.valueOf(currentMessage.addAndGet(1));
    }
}
