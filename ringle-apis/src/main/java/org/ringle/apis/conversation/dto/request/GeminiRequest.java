package org.ringle.apis.conversation.dto.request;

import java.util.List;

import org.ringle.apis.conversation.dto.request.GeminiRequest.Content.Part;

public record GeminiRequest(List<Content> contents) {

	public static GeminiRequest createGemini(String text) {
		Part part = new Part(text);
		Content content = new Content(List.of(part));
		return new GeminiRequest(List.of(content));
	}

	public record Content(List<Part> parts) {

		public Content(String text) {
			this(List.of(new Part(text)));
		}

		public record Part(String text) {
		}
	}
}
