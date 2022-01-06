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

    private static Channel channel;

    public static void main(String[] args ) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(MQ_HOST);
        factory.setUsername(MQ_USER);
        factory.setPassword(MQ_PASSWORD);

        Connection connection = factory.newConnection();
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 疎通確認
        sendMessage("ハローワールド !");

        // 複数パケットの動作確認様
        sendMessage("1st message.");
        sendMessage("2nd message.....");
        sendMessage("3rd message.");
        sendMessage("4th message...");
        sendMessage("5th message..");
        sendMessage("6th message...");
        sendMessage("7th message.");
        sendMessage("8th message.");
        sendMessage("9th message.");
        sendMessage("10th message.");

        channel.close();
        connection.close();
    }

    public static void sendMessage(String message) throws IOException {
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        logger.info(" Sent '" + message + "'");
    }
}
