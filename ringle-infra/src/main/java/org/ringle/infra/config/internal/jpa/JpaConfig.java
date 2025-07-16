package org.ringle.infra.config.internal.jpa;

import org.ringle.infra.InfraBaseConfig;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EntityScan(basePackages = "org.ringle.infra")
@EnableJpaRepositories(basePackages = "org.ringle.infra")
public class JpaConfig implements InfraBaseConfig {
}

