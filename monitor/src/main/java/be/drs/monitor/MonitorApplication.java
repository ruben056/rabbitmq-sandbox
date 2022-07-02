package be.drs.monitor;

import com.github.javafaker.Faker;
import org.reactivestreams.Publisher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootApplication
public class MonitorApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonitorApplication.class, args);
	}

}

@RestController
class SSERestController{

	//todo luisteren naar topics en pushen naar geinteresseerde FE ...
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value="/sse/{n}")
	public Publisher<String> connectieMaken(@PathVariable String n){
		return Flux.interval(Duration.ofSeconds(2)).map(aLong -> Faker.instance().backToTheFuture().quote());
	}
}
