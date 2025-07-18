package org.ringle.infra.config.internal.jpa;

import org.ringle.infra.InfraBaseConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EntityScan(basePackages = "org.ringle.domain")
@EnableJpaRepositories(basePackages = "org.ringle")
public class JpaConfig implements InfraBaseConfig {
}

