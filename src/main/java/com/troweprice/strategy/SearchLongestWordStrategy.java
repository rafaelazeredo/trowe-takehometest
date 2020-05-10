package com.troweprice.strategy;

import com.troweprice.util.WordUtil;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class SearchLongestWordStrategy implements SearchWordStrategy {

    @Override
    public Optional<Pair<Integer, Set<String>>> findWord(final List<String> words) {
        if (Objects.isNull(words) || words.isEmpty()) {
            return Optional.empty();
        }

        LinkedList<String> uniqueOrderedWords = WordUtil.createOrderListOfWords(words);

        Pair<Integer, Set<String>> longestWordsAndLength = findLongestWords(uniqueOrderedWords);

        return Optional.of(longestWordsAndLength);
    }

    private Pair<Integer, Set<String>> findLongestWords(LinkedList<String> uniqueOrderedWords) {
        int longestWordLength = 0;
        Set<String> longestWords = new HashSet<>();
        while (!uniqueOrderedWords.isEmpty()) {
            String word = uniqueOrderedWords.pollLast();
            if (word.length() < longestWordLength) {
                break;
            }
            longestWordLength = word.length();
            longestWords.add(word);
        }
        return Pair.of(longestWordLength, longestWords);
    }
}
