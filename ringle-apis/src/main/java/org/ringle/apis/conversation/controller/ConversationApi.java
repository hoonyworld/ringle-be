package org.ringle.apis.conversation.controller;

import org.ringle.apis.conversation.dto.request.StartConversationRequest;
import org.ringle.apis.conversation.dto.response.StartConversationResponse;
import org.springframework.http.ResponseEntity;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Conversation", description = "대화 세션 관련 API")
public interface ConversationApi {

	@Operation(
		summary = "대화 세션 시작",
		description = "사용자가 새로운 대화 세션을 시작합니다.",
		responses = {
			@ApiResponse(responseCode = "201", description = "대화 세션이 성공적으로 생성됨"),
			@ApiResponse(responseCode = "200", description = "이미 생성된 세션이 존재하여 기존 세션 반환"),
			@ApiResponse(responseCode = "400", description = "요청이 올바르지 않음"),
			@ApiResponse(responseCode = "403", description = "멤버십 권한 부족 등 금지됨")
		}
	)
	ResponseEntity<StartConversationResponse> startConversation(
		@Parameter(hidden = true) Long userId,
		@RequestBody(description = "대화 시작 요청") StartConversationRequest startConversationRequest
	);
}
