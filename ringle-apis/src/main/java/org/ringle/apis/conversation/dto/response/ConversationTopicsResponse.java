package org.ringle.apis.conversation.dto.response;

import java.util.List;

import org.ringle.domain.conversation.vo.ConversationTopicInfo;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "대화 토픽 목록 응답")
public record ConversationTopicsResponse(

	@Schema(description = "대화 토픽 목록")
	List<ConversationTopic> topics

) {
	public static ConversationTopicsResponse from(List<ConversationTopicInfo> infos) {
		List<ConversationTopic> topics = infos.stream()
			.map(info -> new ConversationTopic(info.id(), info.title(), info.initialPrompt()))
			.toList();

		return new ConversationTopicsResponse(topics);
	}

	@Schema(description = "대화 토픽 정보")
	public record ConversationTopic(

		@Schema(description = "토픽 ID", example = "1")
		Long id,

		@Schema(description = "토픽 제목", example = "Travel")
		String title,

		@Schema(description = "토픽 설명", example = "Let's talk about your travel experiences.")
		String description

	) {
	}
}
