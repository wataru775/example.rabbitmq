package org.mmpp.example.rabbitmq;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PostQueueApplication {

    private final static String QUEUE_NAME = "hello";

    private final static String MQ_HOST = "192.168.1.203";
    private final static String MQ_USER = "rabbit";
    private final static String MQ_PASSWORD = "rabbit";

    private final static Logger logger = LoggerFactory.getLogger(PostQueueApplication.class);

    private static Connection connection;
    private static Channel channel;

    static {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost(MQ_HOST);
            factory.setUsername(MQ_USER);
            factory.setPassword(MQ_PASSWORD);

            connection = factory.newConnection();
            channel = connection.createChannel();
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args ) throws IOException, TimeoutException {
        for (int i = 1; i <= 10; i++) {
            String message = "ハローワールド ! " + i;

            sendMessage(message);
        }
        sendMessage("1st message");
        sendMessage("2nd message....");
        sendMessage("3rd message");
        sendMessage("4th message..");
        sendMessage("5th message..");
        sendMessage("6th message..");

        channel.close();
        connection.close();
    }

    public static void sendMessage(String message) throws IOException {
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        logger.info(" Sent '" + message + "'");
    }
}
