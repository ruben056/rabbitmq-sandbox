package be.drs.scs;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Supplier;


@SpringBootApplication
public class ScsDlxApplication {

	private static final Faker FAKER = Faker.instance();

	public static void main(String[] args) {
		SpringApplication.run(ScsDlxApplication.class, args);
	}

	@Bean
	public Supplier<Flux<String>> dlxStringSupplier(){
		// one msg for testing every 10 seconds the rest via http ... manuel testing
		return () -> Flux.interval(Duration.ofSeconds(10)).map(aLong -> FAKER.chuckNorris().fact());
	}

	@Bean
	public Consumer<String> printDlxResult() {
		return s -> {
			if(s.contains("error")){
				throw new IllegalArgumentException(s + " should not contain error");
			}
			System.out.println("result: " + s);
		};
	}

}


@RestController
@RequestMapping("/api/v1")
class SCSIRestCtrl{

	private final StreamBridge streamBridge;

	public SCSIRestCtrl(StreamBridge streamBridge) {
		this.streamBridge = streamBridge;
	}

	@GetMapping(value="/msg/{msg}")
	public void connectieMaken(@PathVariable String msg){
		streamBridge.send("dlxStringSupplier-out-0", msg);
	}
}
