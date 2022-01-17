package org.mmpp.example.rabbitmq.queue.hello;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveQueueWorker {

    private final static String QUEUE_NAME = "hello";

    private final static String MQ_HOST = "192.168.1.203";
    private final static String MQ_USER = "rabbit";
    private final static String MQ_PASSWORD = "rabbit";

    private final static Logger logger = LoggerFactory.getLogger(ReceiveQueueWorker.class);

    public void doReceive() throws IOException, TimeoutException {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MQ_HOST);
        factory.setUsername(MQ_USER);
        factory.setPassword(MQ_PASSWORD);

        final Connection connection = factory.newConnection();
        final Channel channel = connection.createChannel();

        DeliverCallback deliverCallback = (consumerTag,delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            logger.info( " Received '" + message + "'");
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

//        channel.close();
//        connection.close();
    }
}
