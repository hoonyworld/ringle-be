package org.ringle.infra.config.internal.async;

import static org.springframework.context.support.AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME;

import java.util.concurrent.Executor;

import org.ringle.infra.InfraBaseConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements InfraBaseConfig, AsyncConfigurer {

	/**
	 * @Async 어노테이션 비동기 처리 설정
	 * AsyncConfigurer 구현으로 Spring의 기본 비동기 처리기를 대체함!
	 */
	@Override
	public Executor getAsyncExecutor() {
		return new DelegatingSecurityContextExecutor(taskExecutor());
	}

	/**
	 * 이벤트 처리는 반드시 비동기로만 처리되도록 설정함
	 * Spring 설정에 따라 Bean의 name을 삭제하면 안됨!
	 */
	@Bean(name = APPLICATION_EVENT_MULTICASTER_BEAN_NAME)
	public ApplicationEventMulticaster applicationEventMulticaster() {
		SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
		eventMulticaster.setTaskExecutor(taskExecutor());
		return eventMulticaster;
	}

	/**
	 * 공통 비동기 작업 처리용 스레드풀
	 * @Async 메서드와 이벤트 처리에서 공통으로 사용
	 * 추후 AOP 로깅 도입 시, DelegatingSecurityContextExecutor를 이용하여
	 * 비동기 스레드로 SecurityContext를 전달 가능하도록 변경해야 함
	 */
	@Bean(name = "taskExecutor")
	public TaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(3);
		executor.setThreadNamePrefix("async-");
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.setAwaitTerminationSeconds(30);
		executor.initialize();
		return executor;
	}
}
