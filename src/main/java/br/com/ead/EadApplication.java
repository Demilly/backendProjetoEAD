package br.com.ead;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.ead")
public class EadApplication {

	public static void main(String[] args) {
		SpringApplication.run(EadApplication.class, args);
	}

}
