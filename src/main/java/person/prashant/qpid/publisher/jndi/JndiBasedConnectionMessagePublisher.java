package person.prashant.qpid.publisher.jndi;
import person.prashant.qpid.publisher.OurMessagePublisher;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.util.Arrays;
import java.util.List;

public class JndiBasedConnectionMessagePublisher implements OurMessagePublisher {
    private static final int DELIVERY_MODE = DeliveryMode.NON_PERSISTENT;
    private final MessageProducer messageProducer;
    private final Session session;
    private final Connection connection;

    public JndiBasedConnectionMessagePublisher() throws Exception{
        Context context = new InitialContext();

        ConnectionFactory factory = (ConnectionFactory) context.lookup("myFactoryLookup");
        Destination queue = (Destination) context.lookup("myQueueLookup");

        this.connection = factory.createConnection();
        connection.setExceptionListener(new MyExceptionListener());
        connection.start();

        this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        this.messageProducer = this.session.createProducer(queue);
    }


    @Override
    public void publish(List<String> messages) throws JMSException {
        messages.forEach(message -> {
            TextMessage textMessage = null;
            try {
                textMessage = session.createTextMessage(message);
                this.messageProducer.send(textMessage, DELIVERY_MODE, Message.DEFAULT_PRIORITY, Message.DEFAULT_TIME_TO_LIVE);
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
