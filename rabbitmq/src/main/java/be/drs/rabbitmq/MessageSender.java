package be.drs.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class MessageSender implements CommandLineRunner {

    private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

    public MessageSender(RabbitTemplate rabbitTemplate,
                         Receiver receiver) {
        this.rabbitTemplate = rabbitTemplate;
        this.receiver = receiver;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.printf("Sending message...");
        rabbitTemplate.convertAndSend(RabbitmqApplication.topicExchangeName,
                "foo.bar.baz",
                "Hello message...");
        rabbitTemplate.convertAndSend("spring-boot",
                "Hello message via default exchange...");
        receiver.getLatch().await(1000, TimeUnit.MILLISECONDS);
    }
}
