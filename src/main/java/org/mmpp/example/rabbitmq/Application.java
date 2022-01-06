package org.mmpp.example.rabbitmq;

import org.mmpp.example.rabbitmq.listener.ReceiveQueueListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
@EnableScheduling
public class Application {
    public static void main(String[] args) throws IOException, TimeoutException {
        // Spring boot Applicationの実行
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        // Componentの取り出し
        ReceiveQueueListener service = context.getBean(ReceiveQueueListener.class);
        // サービスを実行
        service.doReceive();
    }
}
