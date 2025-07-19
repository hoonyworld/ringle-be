package org.ringle.apis.conversation.dto.response;

public record AiCompleteEvent(String text) {
	public static AiCompleteEvent of(String text) {
		return new AiCompleteEvent(text);
	}
}
