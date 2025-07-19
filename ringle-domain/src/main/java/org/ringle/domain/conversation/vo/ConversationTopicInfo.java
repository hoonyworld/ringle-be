package org.ringle.domain.conversation.vo;

import org.ringle.domain.conversation.ConversationTopic;

public record ConversationTopicInfo(
	Long id,
	String title,
	String initialPrompt

) {
	public static ConversationTopicInfo newInstance(ConversationTopic entity) {
		return new ConversationTopicInfo(
			entity.getId(),
			entity.getTitle(),
			entity.getInitialPrompt()
		);
	}
}
