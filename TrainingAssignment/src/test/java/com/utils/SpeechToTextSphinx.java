package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;

public class SpeechToTextSphinx {

	public static void main(String[] args) throws Exception {

		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/Dictionary/keywordTestDict.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		
		//For file to transcript
//		StreamSpeechRecognizer recognizer = new StreamSpeechRecognizer(configuration);
//		InputStream stream = new FileInputStream(new File("C:\\Users\\barkha.sinha\\Downloads\\Recording.wav"));
//		String sentence = "";
//		recognizer.startRecognition(stream);
//		SpeechResult result;
//		while ((result = recognizer.getResult()) != null) {
//			sentence += result.getHypothesis() + " ";
//		}
//		recognizer.stopRecognition();
//		System.out.println("Spoken sentence :\n" + sentence);
		
		//For live to transcript
		LiveSpeechRecognizer recognizer = new LiveSpeechRecognizer(configuration);
		System.out.println("Recognization started");
		// Start recognition process pruning previously cached data.
		recognizer.startRecognition(true);
		SpeechResult result = recognizer.getResult();
		// Pause recognition process. It can be resumed then with startRecognition(false).
		recognizer.stopRecognition();
		System.out.println("Spoken sentence :\n" + result.getHypothesis());
	}
}
