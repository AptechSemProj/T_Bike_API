package se.pj.tbike;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan({ "org.photoservice.www", "se.pj.tbike" })
@SpringBootApplication
public class TBikeApiApplication {

	public static void main(String[] args) {
		SpringApplication.run( TBikeApiApplication.class, args );
	}

}
