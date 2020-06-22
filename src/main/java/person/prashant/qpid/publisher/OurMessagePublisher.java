package person.prashant.qpid.publisher;

import javax.jms.JMSException;
import java.util.Arrays;
import java.util.List;

public interface OurMessagePublisher {
   default void publish(String... messages) throws JMSException{
      this.publish(Arrays.asList(messages));
   }
   void publish(List<String> messages) throws JMSException;
   void close() throws JMSException;
}
