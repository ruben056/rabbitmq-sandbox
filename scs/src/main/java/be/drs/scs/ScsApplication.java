package be.drs.scs;

import com.github.javafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;


@SpringBootApplication
public class ScsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScsApplication.class, args);
	}

	@Bean
	public Supplier<String> stringSupplier(){
		return () -> Faker.instance().chuckNorris().fact();
	}

	@Bean
	public Function<String, String> transform() {
		return value -> {
			System.out.println("Transform: " + value);
			return value.replaceAll(" ", "_");
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
