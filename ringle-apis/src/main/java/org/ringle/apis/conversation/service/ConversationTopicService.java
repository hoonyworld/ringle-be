package org.ringle.apis.conversation.service;

import java.util.List;

import org.ringle.domain.conversation.ConversationTopicRepository;
import org.ringle.domain.conversation.vo.ConversationTopicInfo;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConversationTopicService {

	private final ConversationTopicRepository conversationTopicRepository;

	public List<ConversationTopicInfo> findAllTopics() {
		return conversationTopicRepository.findAll().stream()
			.map(ConversationTopicInfo::newInstance)
			.toList();
	}
}
