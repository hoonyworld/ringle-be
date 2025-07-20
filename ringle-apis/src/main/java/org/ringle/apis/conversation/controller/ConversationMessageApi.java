package org.ringle.apis.conversation.controller;

import java.util.UUID;

import org.ringle.apis.conversation.dto.response.SttResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Conversation Message", description = "대화 메시지 관련 API")
public interface ConversationMessageApi {

	@Operation(
		summary = "SSE 구독",
		description = "실시간 메시지 수신을 위한 Server-Sent Events 구독을 설정합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "SSE 구독 성공",
			content = @Content(
				mediaType = "text/event-stream",
				schema = @Schema(type = "string", description = "SSE 스트림")
			)
		),
		@ApiResponse(responseCode = "400", description = "잘못된 토큰"),
		@ApiResponse(responseCode = "401", description = "인증 실패")
	})
	@GetMapping("/subscribe/{token}")
	SseEmitter subscribe(
		@Parameter(
			description = "JWT 인증 토큰",
			required = true,
			example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
		)
		@PathVariable("token") String token
	);

	@Operation(
		summary = "음성을 텍스트로 변환",
		description = "업로드된 음성 파일을 텍스트로 변환하고 AI 응답을 트리거합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "음성 변환 성공",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SttResponse.class)
			)
		),
		@ApiResponse(responseCode = "400", description = "잘못된 요청 (파일 형식 오류 등)"),
		@ApiResponse(responseCode = "401", description = "인증 실패"),
		@ApiResponse(responseCode = "500", description = "음성 처리 중 서버 오류")
	})
	@PostMapping(value = "/speech/stt", consumes = "multipart/form-data")
	ResponseEntity<SttResponse> processSpeech(
		@Parameter(hidden = true)
		@AuthenticationPrincipal Long userId,

		@Parameter(
			description = "대화 세션 ID",
			required = true,
			example = "550e8400-e29b-41d4-a716-446655440000"
		)
		@RequestParam("sessionId") UUID sessionId,

		@Parameter(
			description = "변환할 음성 파일 (지원 형식: mp3, wav, m4a 등)",
			required = true
		)
		@RequestParam("audio") MultipartFile audioFile
	);
}
