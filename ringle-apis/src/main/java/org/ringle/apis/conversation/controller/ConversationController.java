package org.ringle.apis.conversation.controller;

import org.ringle.apis.conversation.dto.request.StartConversationRequest;
import org.ringle.apis.conversation.dto.response.StartConversationResponse;
import org.ringle.apis.conversation.usecase.ConversationUseCase;
import org.ringle.globalutils.constants.SessionStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversation")
public class ConversationController {

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
}
