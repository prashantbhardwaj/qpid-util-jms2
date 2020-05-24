package person.prashant.qpid.producer;

import javax.jms.JMSException;

public interface OurMessagePublisher {
   public void publish(String message) throws JMSException;
}
