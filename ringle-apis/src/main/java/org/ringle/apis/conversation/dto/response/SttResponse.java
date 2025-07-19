package org.ringle.apis.conversation.dto.response;

public record SttResponse(
	String transcript
) {
	public static SttResponse from(String transcript) {
		return new SttResponse(transcript);
	}
}
