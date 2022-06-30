package be.drs.scs;

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
		return () -> "testString # sdf d# dd qds f EEE # eee ##### qsdf";
	}

	@Bean
	public Function<String, String> sanitize() {
		return value -> {
			System.out.println("Sanitizing: " + value);
			return value.replaceAll("#", "");
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
