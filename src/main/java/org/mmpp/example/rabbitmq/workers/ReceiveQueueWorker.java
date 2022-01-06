package org.mmpp.example.rabbitmq.workers;

import com.rabbitmq.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeoutException;

@Component
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

        DeliverCallback deliverCallback = this::work;
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

//        channel.close();
//        connection.close();
    }

    public void work(String consumerTag, Delivery delivery) throws UnsupportedEncodingException {
        String message = new String(delivery.getBody(), "UTF-8");
        logger.info( " Received '" + message + "'");
        for (char ch: message.toCharArray()) {
            if (ch == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
