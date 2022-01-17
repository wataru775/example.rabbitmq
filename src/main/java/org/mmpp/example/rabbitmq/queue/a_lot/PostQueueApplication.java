package org.mmpp.example.rabbitmq.queue.a_lot;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class PostQueueApplication {

    private final static String QUEUE_NAME = "a_lot";

    private final static String MQ_HOST = "192.168.1.203";
    private final static String MQ_USER = "rabbit";
    private final static String MQ_PASSWORD = "rabbit";

    private final static Logger logger = LoggerFactory.getLogger(PostQueueApplication.class);

    public static void main(String[] args ) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MQ_HOST);
        factory.setUsername(MQ_USER);
        factory.setPassword(MQ_PASSWORD);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for(int i = 1 ; i <= 10 ; i ++) {
            String message = "ハローワールド ! No." + i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            logger.info(" Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }
}
