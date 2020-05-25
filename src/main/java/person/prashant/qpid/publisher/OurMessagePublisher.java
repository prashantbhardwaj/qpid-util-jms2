package person.prashant.qpid.publisher;

import javax.jms.JMSException;

public interface OurMessagePublisher {
   public void publish(String... messages) throws JMSException;
   public void close() throws JMSException;
}
