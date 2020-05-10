package com.troweprice.component;

import static org.assertj.core.api.Java6Assertions.assertThat;

import com.troweprice.service.WordService;
import com.troweprice.strategy.SearchLongestWordStrategy;
import com.troweprice.strategy.SearchShortestWordStrategy;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Set;

public class WordSearch {

    @Test
    public void findLongestWordInAText(){
        Optional<Pair<Integer, Set<String>>> longestWordsOp =
                        new WordService(new SearchLongestWordStrategy()).findWord("The cow jumped over the moon and walked back home.");
        assertThat(longestWordsOp.isPresent()).isTrue();
        Pair<Integer, Set<String>> longestWords = longestWordsOp.get();
        assertThat(longestWords).extracting(Pair::getLeft).contains(6);
        assertThat(longestWords).extracting(Pair::getRight).containsExactly(Set.of("jumped", "walked"));
    }

    @Test
    public void findShortestWordInAText(){
        Optional<Pair<Integer, Set<String>>> shortestWordsOp =
                        new WordService(new SearchShortestWordStrategy()).findWord("The cow jumped over the moon and walked back home.");
        assertThat(shortestWordsOp.isPresent()).isTrue();
        Pair<Integer, Set<String>> shortestWords = shortestWordsOp.get();
        assertThat(shortestWords).extracting(Pair::getLeft).contains(3);
        assertThat(shortestWords).extracting(Pair::getRight).containsExactly(Set.of("the", "cow", "and"));
    }
}
