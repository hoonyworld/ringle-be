package org.ringle.apis.conversation.controller;

import java.util.UUID;

import org.ringle.apis.conversation.dto.response.SttResponse;
import org.ringle.apis.conversation.usecase.SseUseCase;
import org.ringle.apis.conversation.usecase.SttUseCase;
import org.ringle.gateway.jwt.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
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
public class ConversationMessageController {
	private final SseUseCase sseUseCase;
	private final SttUseCase sttUseCase;
	private final JwtTokenService jwtTokenService;

	@GetMapping("/subscribe")
	public SseEmitter subscribe(@RequestParam("token") String token) {
		Long userId = jwtTokenService.getUserIdFromToken(token);
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
