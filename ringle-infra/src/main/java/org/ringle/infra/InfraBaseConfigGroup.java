package org.ringle.infra;

import org.ringle.infra.config.external.api.RestClientConfig;
import org.ringle.infra.config.external.redis.RedisConfig;
import org.ringle.infra.config.internal.async.AsyncConfig;
import org.ringle.infra.config.internal.jpa.JpaConfig;

public enum InfraBaseConfigGroup {
	ASYNC(AsyncConfig.class),
	JPA(JpaConfig.class),
	REDIS(RedisConfig.class),
	REST_CLIENT(RestClientConfig.class);

	private final Class<? extends InfraBaseConfig> configClass;

	InfraBaseConfigGroup(Class<? extends InfraBaseConfig> configClass) {
		this.configClass = configClass;
	}

	public Class<? extends InfraBaseConfig> getConfigClass() {
		return configClass;
	}
}
