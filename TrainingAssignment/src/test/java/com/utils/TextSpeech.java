package com.utils;

import java.io.FileReader;
import java.util.Locale;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import javax.speech.Central;
import javax.speech.synthesis.Synthesizer;
import javax.speech.synthesis.SynthesizerModeDesc;

public class TextSpeech {

	private static final String CONVERSATION_JSON_PATH = "src\\test\\resources\\ProductConfigs\\TovoCallingConversations.json";

	/**
	 * This method is used to get the all the convos data from the Json file based
	 * on the sentenceName passed in the method.
	 * 
	 * @param sentenceName
	 * @return
	 */
	private static LinkedTreeMap<String, String> getConversationFromJSON(String conversationName) throws Throwable {
		Gson gson = new Gson();
		LinkedTreeMap<String, String> conversations = new LinkedTreeMap<String, String>();
		try {
			LinkedTreeMap<String, Object> jsonData = gson.fromJson(new FileReader(CONVERSATION_JSON_PATH),
					conversations.getClass());
			if (jsonData.containsKey(conversationName)) {
				conversations = (LinkedTreeMap<String, String>) jsonData.get(conversationName);
			}
		} catch (Exception e) {
			throw new RuntimeException("Exception =" + e);
		}
		return conversations;
	}

	public static void main(String[] args) throws Throwable {
		LinkedTreeMap<String, String> sentencesToSpeak = getConversationFromJSON("OutgoingCallConvo1");
		System.setProperty("FreeTTSSynthEngineCentral", "com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		Central.registerEngineCentral("com.sun.speech.freetts.jsapi.FreeTTSEngineCentral");
		Synthesizer synthesizer = Central.createSynthesizer(new SynthesizerModeDesc(Locale.ENGLISH));
		for (String sentences : sentencesToSpeak.keySet()) {
			Thread.sleep(2000);
			if (sentences.toLowerCase().contains("caller")) {
				System.out.println("Speaker is caller, unmuting caller and muting receiver");
			} else if (sentences.toLowerCase().contains("receiver")) {
				System.out.println("Speaker is receiver, unmuting receiver and muting caller");
			}
			synthesizer.allocate();
			synthesizer.getSynthesizerProperties().setSpeakingRate(120.0f);
			synthesizer.resume();
			System.out.println("Sentence spoken: " + sentencesToSpeak.get(sentences));
			synthesizer.speakPlainText(sentencesToSpeak.get(sentences).toString(), null);
			synthesizer.waitEngineState(Synthesizer.QUEUE_EMPTY);
		}
		synthesizer.deallocate();
	}
}