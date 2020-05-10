package com.troweprice.service;

import com.troweprice.strategy.SearchWordStrategy;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class WordService {

    private static final String REGEX_WHITESPACE = "\\s";
    private static final String REGEX_ONE_OR_MORE_WHITESPACES = "\\s+";
    private static final String REGEX_NON_LETTERS = "[^\\w]|[\\d]";
    private static final String DELIMITER_BETWEEN_WORDS = " ";


    /*
    •	Write a method or function in the major programming language of your choice that returns the longest word in a sentence and its length. For example, “The cow jumped over the moon.” should return “jumped” and 6.
	•	Write unit tests, reworking code as needed
	•	Add a method that returns the shortest word and length with unit tests
	•	Create a README documenting any assumptions you made and including instructions on how to build and execute your tests.
	•	Share your code using GitHub or similar.

     */
    private SearchWordStrategy searchWordStrategy;

    public WordService(SearchWordStrategy searchWordStrategy){
        this.searchWordStrategy = searchWordStrategy;
    }

    public Optional<Pair<Integer, Set<String>>> findWord(String text){
        if(StringUtils.isBlank(text)){
            return Optional.empty();
        }
        String lowerCaseText = text.toLowerCase();
        String sanitisedText = sanitiseSpecialCharactersFromText(lowerCaseText);
        List<String> allWords = breakTextIntoWords(sanitisedText);

        return searchWordStrategy.findWord(allWords);
    }

    private static String sanitiseSpecialCharactersFromText(String text) {
        String sanitisedText = text.replaceAll(REGEX_NON_LETTERS, DELIMITER_BETWEEN_WORDS)
                                   .replaceAll(REGEX_ONE_OR_MORE_WHITESPACES, DELIMITER_BETWEEN_WORDS);
        return sanitisedText.strip();
    }

    private static List<String> breakTextIntoWords(String cleanedText) {
        String[] allWords = cleanedText.split(REGEX_WHITESPACE);
        return List.of(allWords);
    }
}
