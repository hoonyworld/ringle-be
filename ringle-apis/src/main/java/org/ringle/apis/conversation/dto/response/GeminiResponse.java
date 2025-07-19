package org.ringle.apis.conversation.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeminiResponse(
	List<Candidate> candidates,
	PromptFeedback promptFeedback
) {

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Candidate(
		Content content,
		String finishReason,
		int index,
		List<SafetyRating> safetyRatings
	) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Content(
		List<Part> parts,
		String role
	) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record Part(
		String text
	) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record SafetyRating(
		String category,
		String probability
	) {}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public record PromptFeedback(
		List<SafetyRating> safetyRatings
	) {}
}
