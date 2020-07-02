package person.prashant.qpid.publisher.manual;

import javafx.util.Pair;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.prashant.qpid.publisher.DestinationResolver;
import person.prashant.qpid.publisher.OurMessagePublisher;

import javax.jms.*;
import java.util.List;

public class JmsContextFromConnectionFactoryBasedMessagePublisher implements OurMessagePublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(JmsContextFromConnectionFactoryBasedMessagePublisher.class);
    private final ConnectionFactory connectionFactory;
    private DestinationResolver destinationResolver;

    public JmsContextFromConnectionFactoryBasedMessagePublisher(String url, boolean isTopic, final String destinationName) throws Exception {
        this.connectionFactory = new JmsConnectionFactory(url);
        this.destinationResolver = (jmsContext, msg) -> {
            if(isTopic) {
                return new Pair<Destination, String>(jmsContext.createTopic(destinationName), destinationName);
            } else {
                return new Pair<Destination, String>(jmsContext.createQueue(destinationName), destinationName);
            }
        };
    }

    public void publish(List<String> messages) throws JMSException {
        try(JMSContext jmsContext = this.connectionFactory.createContext()) {
            JMSProducer jmsProducer = jmsContext.createProducer();

            messages.forEach(message -> {
                TextMessage jmsMessage = jmsContext.createTextMessage(message);
                Destination destination = this.destinationResolver.resolve(jmsContext, jmsMessage).getKey();
                jmsProducer.send(destination, jmsMessage);
                LOGGER.info("Message sent - {}", message);
            });

            if (jmsContext.getTransacted()) {
                LOGGER.info("Committing the message");
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
