package person.prashant.qpid.publisher.manual;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import person.prashant.qpid.publisher.OurMessagePublisher;

import javax.jms.*;
import java.util.List;

public class ManualConnectionFactoryToMessagePublisher implements OurMessagePublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(ManualConnectionFactoryToMessagePublisher.class);
    private static final int DELIVERY_MODE = DeliveryMode.NON_PERSISTENT;
    private final MessageProducer messageProducer;
    private final Session session;
    private final Connection connection;
    private final ConnectionFactory connectionFactory;

    public ManualConnectionFactoryToMessagePublisher(String url, boolean isTopic, final String destinationName) throws Exception{
        this.connectionFactory = new JmsConnectionFactory(url);
        this.connection = this.connectionFactory.createConnection();
        connection.setExceptionListener(new MyExceptionListener());
        connection.start();

        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Destination destination = null;
        if(isTopic){
            destination = (Destination) session.createTopic(destinationName);
        } else {
            destination = (Destination) session.createQueue(destinationName);
        }

        this.messageProducer = this.session.createProducer(destination);
    }


    @Override
    public void publish(List<String> messages) throws JMSException {
        messages.forEach(message -> {
            TextMessage textMessage = null;
            try {
                textMessage = session.createTextMessage(message);
                this.messageProducer.send(textMessage, DELIVERY_MODE, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);
                LOGGER.info("Message sent - {}", message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void close() throws JMSException {
        this.connection.close();
    }

    private static class MyExceptionListener implements ExceptionListener {
        @Override
        public void onException(JMSException exception) {
            System.out.println("Connection ExceptionListener fired, exiting.");
            exception.printStackTrace(System.out);
            System.exit(1);
        }
    }
}
