package org.ringle.apis.config;

import org.ringle.infra.EnableInfraBaseConfig;
import org.ringle.infra.InfraBaseConfigGroup;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@EnableInfraBaseConfig({
	InfraBaseConfigGroup.JPA,
	InfraBaseConfigGroup.ASYNC,
	InfraBaseConfigGroup.REDIS,
	InfraBaseConfigGroup.REST_CLIENT
})
public class InfraConfig {
}
