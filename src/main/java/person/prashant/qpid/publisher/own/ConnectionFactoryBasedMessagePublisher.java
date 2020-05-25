package person.prashant.qpid.publisher.own;

import javafx.util.Pair;
import org.apache.qpid.jms.JmsConnectionFactory;
import person.prashant.qpid.publisher.DestinationResolver;
import person.prashant.qpid.publisher.OurMessagePublisher;

import javax.jms.*;

public class ConnectionFactoryBasedMessagePublisher implements OurMessagePublisher {

    private final ConnectionFactory connectionFactory;
    private DestinationResolver destinationResolver;

    public ConnectionFactoryBasedMessagePublisher(String url, boolean isTopic, final String destinationName) throws Exception {
        this.connectionFactory = new JmsConnectionFactory(url);
        this.destinationResolver = (jmsContext, msg) -> {
            if(isTopic) {
                return new Pair<Destination, String>(jmsContext.createTopic(destinationName), destinationName);
            } else {
                return new Pair<Destination, String>(jmsContext.createQueue(destinationName), destinationName);
            }
        };
    }

    public void publish(String message) throws JMSException {
        try(JMSContext jmsContext = this.connectionFactory.createContext()) {
            JMSProducer jmsProducer = jmsContext.createProducer();
            TextMessage jmsMessage = jmsContext.createTextMessage(message);
            Destination destination = this.destinationResolver.resolve(jmsContext, jmsMessage).getKey();
            jmsProducer.send(destination, jmsMessage);
            if (jmsContext.getTransacted()) {
                jmsContext.commit();
            }
        } catch (Exception exp){
            throw exp;
        }
    }

    @Override
    public void close() throws JMSException {
        // no need because no open connection
    }
}
