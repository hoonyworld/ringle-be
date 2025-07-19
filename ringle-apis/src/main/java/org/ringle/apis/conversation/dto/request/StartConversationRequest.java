package org.ringle.apis.conversation.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "대화 시작 요청")
public record StartConversationRequest(
	@Schema(description = "대화 주제 ID", example = "1")
	Long topicId,

	@Schema(description = "기존 세션 확인을 건너뛰고 강제로 새 세션을 생성할지 여부", defaultValue = "false")
	boolean forceCreate
) {
	public StartConversationRequest(Long topicId) {
		this(topicId, false);
	}
}
