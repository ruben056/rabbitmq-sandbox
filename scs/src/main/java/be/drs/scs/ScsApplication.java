package be.drs.scs;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@SpringBootApplication
public class ScsApplication {

	private static final Faker FAKER = Faker.instance();

	public static void main(String[] args) {
		SpringApplication.run(ScsApplication.class, args);
	}

	@Bean
	public Supplier<Flux<String>> stringSupplier(){
		return () -> Flux.interval(Duration.ofSeconds(2)).map(aLong -> FAKER.chuckNorris().fact());
	}

	/*@Bean
	public Supplier<String> stringSupplier(){
		return () -> FAKER.chuckNorris().fact();
	}*/

	@Bean
	public Function<String, String> transform() {
		return value -> {
			String toReplace = "Chuck Norris";
			String replacement = "The Governator";
			String output = Pattern.compile(toReplace,
							Pattern.LITERAL | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE)
					.matcher(value)
					.replaceAll(Matcher.quoteReplacement(replacement));
			System.out.println("Transform: " + output);
			return output;
		};
	}

	@Bean
	public Function<String, String> uppercase() {
		return value -> {
			System.out.println("Uppercasing: " + value);
			return value.toUpperCase();
		};
	}

	@Bean
	public Consumer<String> printResult() {
		return s -> System.out.println("result: " + s);
	}

}
