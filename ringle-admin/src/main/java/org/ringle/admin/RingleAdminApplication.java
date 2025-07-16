package org.ringle.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;

@SpringBootApplication(
	scanBasePackages = {
		"org.ringle.admin",
		"org.ringle.infra",
		"org.ringle.domain",
		"org.ringle.gateway",
		"org.ringle.globalutils"
	},
	exclude = {JpaRepositoriesAutoConfiguration.class}
)
public class RingleAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(RingleAdminApplication.class, args);
	}

}
