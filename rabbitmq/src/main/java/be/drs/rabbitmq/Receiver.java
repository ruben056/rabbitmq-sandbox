package be.drs.rabbitmq;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Receiver {

    private CountDownLatch latch = new CountDownLatch(2);

    public void receiveMessage(byte[] message) {
        String value = new String(message);
        System.out.println("Received <" + value.toString() + ">");
        latch.countDown();
    }

    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}
