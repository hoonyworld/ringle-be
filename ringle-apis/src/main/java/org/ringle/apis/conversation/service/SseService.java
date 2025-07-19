package org.ringle.apis.conversation.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SseService {

	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();

	public SseEmitter subscribe(Long userId) {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		emitters.put(userId, emitter);

		emitter.onCompletion(() -> emitters.remove(userId));
		emitter.onTimeout(() -> emitters.remove(userId));
		emitter.onError(e -> emitters.remove(userId));

		sendEvent(userId, "connected", "SSE connection established for user " + userId);
		return emitter;
	}

	public void sendEvent(Long userId, String eventName, Object data) {
		SseEmitter emitter = emitters.get(userId);
		if (emitter != null) {
			try {
				emitter.send(SseEmitter.event().name(eventName).data(data));
			} catch (IOException e) {
				log.error("Failed to send SSE event to user: {}", userId, e);
				emitters.remove(userId);
			}
		}
	}
}
