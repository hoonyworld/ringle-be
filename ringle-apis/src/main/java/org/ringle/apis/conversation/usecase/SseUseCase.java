package org.ringle.apis.conversation.usecase;

import org.ringle.apis.conversation.service.SseService;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SseUseCase {
	private final SseService sseService;

	public SseEmitter subscribe(Long userId) {
		return sseService.subscribe(userId);
	}
}
