package org.ringle.apis.conversation.dto.response;

import java.util.List;

public record GeminiResponse(List<Candidate> candidates) {
	public record Candidate(Content content, String finishReason) {
	}

	public record Content(List<Part> parts, String role) {
	}

	public record Part(String text) {
	}
}
