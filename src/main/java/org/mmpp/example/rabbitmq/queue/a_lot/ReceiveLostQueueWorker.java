package org.mmpp.example.rabbitmq.queue.a_lot;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveLostQueueWorker {

    private final static String QUEUE_NAME = "a_lot";

    private final static String MQ_HOST = "192.168.1.203";
    private final static String MQ_USER = "rabbit";
    private final static String MQ_PASSWORD = "rabbit";

    private final static Logger logger = LoggerFactory.getLogger(ReceiveLostQueueWorker.class);

    private int pass_packet = 0;

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
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(pass_packet++ > 0) {
                logger.info(" Received [Lost]");
                System.exit(0);
            }
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });

//        channel.close();
//        connection.close();
    }
    public static void main(String[] args) throws IOException, TimeoutException {
        ReceiveLostQueueWorker worker = new ReceiveLostQueueWorker();
        // サービスを実行
        worker.doReceive();
    }
}
