package it.uniroma3.Odiazon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class OdiazonApplication {

	public static void main(String[] args) {
		SpringApplication.run(OdiazonApplication.class, args);
	}

}
