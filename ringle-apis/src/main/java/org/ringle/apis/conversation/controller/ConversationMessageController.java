package org.ringle.apis.conversation.controller;

import java.util.UUID;

import org.ringle.apis.conversation.dto.response.SttResponse;
import org.ringle.apis.conversation.usecase.SseUseCase;
import org.ringle.apis.conversation.usecase.SttUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ConversationMessageController implements ConversationMessageApi {
	private final SseUseCase sseUseCase;
	private final SttUseCase sttUseCase;
	private final JwtDecoder jwtDecoder;

	@GetMapping("/subscribe/{token}")
	public SseEmitter subscribe(@PathVariable String token) {
		Jwt jwt = jwtDecoder.decode(token);
		Long userId = Long.valueOf(jwt.getSubject());
		return sseUseCase.subscribe(userId);
	}

	@PostMapping("/speech/stt")
	public ResponseEntity<SttResponse> processSpeech(
		@AuthenticationPrincipal Long userId,
		@RequestParam("sessionId") UUID sessionId,
		@RequestParam("audio") MultipartFile audioFile
	) {
		SttResponse response = sttUseCase.processSttAndTriggerAi(userId, sessionId, audioFile);
		return ResponseEntity.ok(response);
	}
}
