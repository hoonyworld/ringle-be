package org.ringle.apis.conversation.service;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SseService {

	private final Map<Long, SseEmitter> emitters = new ConcurrentHashMap<>();
	private final ObjectMapper objectMapper;

	public SseEmitter subscribe(Long userId) {
		SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
		emitters.put(userId, emitter);

		emitter.onCompletion(() -> emitters.remove(userId));
		emitter.onTimeout(() -> emitters.remove(userId));
		emitter.onError(e -> emitters.remove(userId));

		try {
			// 1. 일반 텍스트 대신 Map 또는 DTO 객체 생성
			Map<String, String> connectionData = Map.of("message", "SSE connection established for user " + userId);
			// 2. ObjectMapper를 사용하여 JSON 문자열로 변환
			String jsonData = objectMapper.writeValueAsString(connectionData);
			// 3. "connected" 이벤트로 JSON 데이터 전송
			emitter.send(SseEmitter.event().name("connected").data(jsonData));
		} catch (IOException e) {
			log.warn("Failed to send initial connection event to user {}", userId, e);
			// 에러 발생 시 맵에서 제거
			emitters.remove(userId);
		}

		return emitter;
	}

	public void sendEvent(Long userId, String eventName, Object data) {
		SseEmitter emitter = emitters.get(userId);
		if (emitter != null) {
			try {
				// 이 메서드는 이미 객체를 받아 JSON으로 보내고 있으므로 수정할 필요가 없습니다.
				emitter.send(SseEmitter.event().name(eventName).data(data));
			} catch (IOException e) {
				log.error("Failed to send SSE event to user: {}", userId, e);
				emitters.remove(userId);
			}
		}
	}

	public void completeEmitter(Long userId) {
		SseEmitter emitter = emitters.get(userId);
		if (emitter != null) {
			emitter.complete();
			log.info("SSE emitter completed for user: {}", userId);
		}
	}
}
