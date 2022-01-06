package org.mmpp.example.rabbitmq;

import org.mmpp.example.rabbitmq.workers.ReceiveQueueWorker;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveQueueApplication {
    public static void main(String[] args) throws IOException, TimeoutException {
        ReceiveQueueWorker worker = new ReceiveQueueWorker();
        // サービスを実行
        worker.doReceive();
    }
}
