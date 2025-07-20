package org.ringle.apis.conversation.controller;

import java.util.UUID;

import org.ringle.apis.conversation.dto.request.StartConversationRequest;
import org.ringle.apis.conversation.dto.response.ConversationHistoryResponse;
import org.ringle.apis.conversation.dto.response.StartConversationResponse;
import org.ringle.apis.conversation.usecase.ConversationUseCase;
import org.ringle.globalutils.constants.SessionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversation")
public class ConversationController implements ConversationApi {

	private final ConversationUseCase conversationUseCase;

	@PostMapping("/start")
	public ResponseEntity<StartConversationResponse> startConversation(
		@AuthenticationPrincipal Long userId,
		@RequestBody StartConversationRequest startConversationRequest
	) {
		StartConversationResponse response = conversationUseCase.startConversation(userId, startConversationRequest);
		HttpStatus httpStatus = response.status() == SessionStatus.ACTIVE ? HttpStatus.CREATED : HttpStatus.OK;
		return ResponseEntity.status(httpStatus).body(response);
	}

	@GetMapping("/{sessionId}")
	public ResponseEntity<ConversationHistoryResponse> getConversationHistory(
		@AuthenticationPrincipal Long userId,
		@PathVariable UUID sessionId
	) {
		ConversationHistoryResponse response = conversationUseCase.getConversationHistory(userId, sessionId);
		return ResponseEntity.ok(response);
	}
}
