package org.mmpp.example.rabbitmq.queue.hello;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ReceiveWorkerApplication {
    public static void main(String[] args) throws IOException, TimeoutException {
        ReceiveQueueWorker worker = new ReceiveQueueWorker();
        // サービスを実行
        worker.doReceive();
    }
}
