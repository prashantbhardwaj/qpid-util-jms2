package person.prashant.qpid.producer;

import javafx.util.Pair;

import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.TextMessage;

public interface DestinationResolver {

    Pair<Destination, String> resolve(JMSContext jmsContext, TextMessage message);
}
