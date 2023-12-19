package ar.edu.ubp.das.mss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MssApplication {

	public static void main(String[] args) {
		SpringApplication.run(MssApplication.class, args);
	}

}
