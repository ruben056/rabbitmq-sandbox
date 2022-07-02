package be.drs.scsconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;

@SpringBootApplication
public class ScsConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScsConsumerApplication.class, args);
    }


    @Bean
    public Consumer<String> sout(){
        return incomingMsg -> System.out.println("scsConsumer : " + incomingMsg);
     }
}
