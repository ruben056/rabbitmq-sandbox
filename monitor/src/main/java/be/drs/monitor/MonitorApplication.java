package be.drs.monitor;

import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.function.Consumer;

@SpringBootApplication
public class MonitorApplication {

	@Autowired
	private MessageDispatcherService messageDispatcherService;

	public static void main(String[] args) {
		SpringApplication.run(MonitorApplication.class, args);
	}

	@Bean
	public Consumer<String> consume(){
		return messageDispatcherService::dispatch;
	}

}

@RestController
class SSERestController{

	private final MessageDispatcherService messageDispatcherService;

	public SSERestController(MessageDispatcherService messageDispatcherService) {
		this.messageDispatcherService = messageDispatcherService;
	}


	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE, value="/sse/{n}")
	public Publisher<String> connectieMaken(@PathVariable String n){
		return messageDispatcherService.asFlux();
	}
}

@Service
class MessageDispatcherService {

	private final Sinks.Many<String> emitter = Sinks.many().multicast().onBackpressureBuffer();

	public void dispatch(String msg) {
		emitter.tryEmitNext(msg);
	}

	public Flux<String> asFlux() {
		return emitter.asFlux().share();
	}
}
