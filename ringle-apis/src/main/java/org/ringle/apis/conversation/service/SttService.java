package org.ringle.apis.conversation.service;

import com.google.cloud.speech.v1.RecognitionAudio;
import com.google.cloud.speech.v1.RecognitionConfig;
import com.google.cloud.speech.v1.RecognizeResponse;
import com.google.cloud.speech.v1.SpeechClient;
import com.google.cloud.speech.v1.SpeechRecognitionAlternative;
import com.google.cloud.speech.v1.SpeechRecognitionResult;
import com.google.protobuf.ByteString;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class SttService {

	private final SpeechClient speechClient;

	public String convertSpeechToText(MultipartFile audioFile) {
		log.info("Requesting Google Speech-to-Text API for file: {}", audioFile.getOriginalFilename());

		try {
			ByteString audioBytes = ByteString.copyFrom(audioFile.getBytes());

			RecognitionConfig config = RecognitionConfig.newBuilder()
				.setEncoding(RecognitionConfig.AudioEncoding.WEBM_OPUS)
				.setSampleRateHertz(48000)
				.setLanguageCode("en-US")
				.build();

			RecognitionAudio recognitionAudio = RecognitionAudio.newBuilder()
				.setContent(audioBytes)
				.build();

			RecognizeResponse response = speechClient.recognize(config, recognitionAudio);

			if (response.getResultsCount() == 0) {
				log.warn("No transcript found for the audio file.");
				return "";
			}

			SpeechRecognitionResult result = response.getResultsList().getFirst();
			SpeechRecognitionAlternative alternative = result.getAlternativesList().getFirst();

			String transcript = alternative.getTranscript();
			log.info("Transcript received: {}", transcript);

			return transcript;

		} catch (IOException e) {
			log.error("Failed to process audio file.", e);
			throw new RuntimeException("Failed to read audio file.", e);
		} catch (Exception e) {
			log.error("Google Speech-to-Text API error.", e);
			throw new RuntimeException("Error during speech-to-text conversion.", e);
		}
	}
}
