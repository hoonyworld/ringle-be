package org.ringle.apis.conversation.controller;

import org.ringle.apis.conversation.dto.response.ConversationTopicsResponse;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Conversation Topic", description = "대화 토픽 관련 API")
public interface ConversationTopicApi {

	@Operation(
		summary = "대화 토픽 목록 조회",
		description = "사용자가 선택할 수 있는 대화 토픽 리스트를 조회합니다.",
		responses = {
			@ApiResponse(responseCode = "200", description = "대화 토픽 리스트 조회 성공"),
			@ApiResponse(responseCode = "401", description = "인증 실패")
		}
	)
	ResponseEntity<ConversationTopicsResponse> getConversationTopics(
		@Parameter(hidden = true) Long userId
	);
}
