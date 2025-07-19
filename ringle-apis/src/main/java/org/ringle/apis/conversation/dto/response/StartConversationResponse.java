package org.ringle.apis.conversation.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

import org.ringle.globalutils.constants.SessionStatus;

@Schema(description = "대화 시작 응답")
public record StartConversationResponse(
	@Schema(description = "대화 세션 ID")
	UUID sessionId,

	@Schema(description = "세션 상태 (NEW: 새로 생성됨, EXISTING: 기존 세션 존재)")
	SessionStatus status
) {
	public static StartConversationResponse of(UUID sessionId, SessionStatus status) {
		return new StartConversationResponse(sessionId, status);
	}
}
