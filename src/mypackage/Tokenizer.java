package mypackage;

import java.util.ArrayList;
import java.util.List;

import mypackage.util.Constants;
import mypackage.util.Stemmer;

/**
 * Tokenizes the input string. Uses stemmer to stem the words, removes stop words and words size less than 3.
 * 
 * @author Manjusha
 *
 */
public class Tokenizer {
	private static Stemmer stemmer = new Stemmer();

	public static List<String> tokenize(final String inputString) {
		final String trimmedString = inputString.replaceAll("[`,?\\\\\\*\"\';:!$\\^()&#/|+=%<>{}\\[\\]]", " ");

		final String[] wordArray = trimmedString.split("[\\s]+");
		List<String> response = new ArrayList<String>();
		for (String word : wordArray) {
			if (word.length() < 2) {
				continue;
			}
			if (isStopWord(word)) {
				continue;
			}
			final String stemmedWord = stemmer.stem(word);
			if (!response.contains(stemmedWord)) {
				response.add(stemmedWord);
			}
		}
		return response;
	}

	private static boolean isStopWord(final String word) {
		return Constants.STOP_WORDS.indexOf(" " + word + " ") >= 0;
	}

}
