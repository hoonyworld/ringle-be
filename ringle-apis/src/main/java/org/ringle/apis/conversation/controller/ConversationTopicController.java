package org.ringle.apis.conversation.controller;

import org.ringle.apis.conversation.dto.response.ConversationTopicsResponse;
import org.ringle.apis.conversation.usecase.ConversationUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/conversation-topics")
public class ConversationTopicController implements ConversationTopicApi {
	private final ConversationUseCase conversationUseCase;

	@GetMapping
	public ResponseEntity<ConversationTopicsResponse> getConversationTopics(
		@AuthenticationPrincipal Long userId
	) {
		ConversationTopicsResponse response = conversationUseCase.getConversationTopics();
		return ResponseEntity.ok(response);
	}
}
