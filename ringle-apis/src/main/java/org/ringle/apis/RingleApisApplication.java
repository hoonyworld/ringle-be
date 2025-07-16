package org.ringle.apis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@SpringBootApplication(
	scanBasePackages = {
		"org.ringle.apis",
		"org.ringle.infra",
		"org.ringle.domain",
		"org.ringle.gateway",
		"org.ringle.globalutils"
	},
	exclude = {JpaRepositoriesAutoConfiguration.class}
)
public class RingleApisApplication {

	public static void main(String[] args) {
		SpringApplication.run(RingleApisApplication.class, args);
	}

}
