package org.ringle.apis.conversation.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import org.ringle.domain.conversation.ConversationMessage;
import org.ringle.domain.conversation.SenderType;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "대화 내역 전체 응답")
public record ConversationHistoryResponse(
	@Schema(description = "대화 메시지 목록")
	List<ConversationHistoryMessageResponse> messages
) {
	@Schema(description = "개별 대화 메시지 정보")
	public record ConversationHistoryMessageResponse(
		@Schema(description = "발신자 (USER 또는 AI)")
		SenderType sender,

		@Schema(description = "메시지 내용")
		String message,

		@Schema(description = "생성 일시")
		LocalDateTime createdAt
	) {
		public static ConversationHistoryMessageResponse from(ConversationMessage message) {
			return new ConversationHistoryMessageResponse(
				message.getSender(),
				message.getMessage(),
				message.getCreatedAt()
			);
		}
	}

	public static ConversationHistoryResponse from(List<ConversationHistoryMessageResponse> messages) {
		return new ConversationHistoryResponse(messages);
	}
}
