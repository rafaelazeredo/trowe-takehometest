package com.troweprice.strategy;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class SearchShortestWordStrategyTest {

    @InjectMocks
    SearchShortestWordStrategy searchShortestWordStrategy;

    @Test
    void findWord_whenListIsEmptyReturnsOptionalEmpty() {
        //When
        Optional<Pair<Integer, Set<String>>> longestWords = searchShortestWordStrategy.findWord(List.of());

        //Then
       assertThat(longestWords.isEmpty()).isTrue();
    }

    @Test
    void findWord_whenListIsNullReturnsOptionalEmpty() {
        //When
        Optional<Pair<Integer, Set<String>>> longestWords = searchShortestWordStrategy.findWord(null);

        //Then
       assertThat(longestWords.isEmpty()).isTrue();
    }

    @Test
    void findWord_whenOnlyWordHasTheShortestLength() {
        //Given
        List<String> givenWords = List.of("a", "cow", "jumped", "over", "the", "moon", "times");

        String expectedLongestWord = "a";
        int expectedLongestWordLength = 1;

        //When
        Optional<Pair<Integer, Set<String>>> longestWordsOp = searchShortestWordStrategy.findWord(givenWords);

        //Then
       assertThat(longestWordsOp.isPresent()).isTrue();
        Pair<Integer, Set<String>> longestWords = longestWordsOp.get();
       assertThat(longestWords).extracting(Pair::getLeft).contains(expectedLongestWordLength);
       assertThat(longestWords).extracting(Pair::getRight).containsExactlyInAnyOrder(Set.of(expectedLongestWord));
    }

    @Test
    void findWord_whenMultipleWordsHaveTheLongestLength() {
        //Given
        List<String> givenWords = List.of("the", "cow", "jumped", "over", "the", "moon", "and", "walked", "back", "home");

        Set<String> expectedLongestWords = Set.of("the","cow","and");
        int expectedLongestWordLength = 3;

        //When
        Optional<Pair<Integer, Set<String>>> longestWordsOp = searchShortestWordStrategy.findWord(givenWords);

        //Then
       assertThat(longestWordsOp.isPresent()).isTrue();
        Pair<Integer, Set<String>> longestWords = longestWordsOp.get();
       assertThat(longestWords).extracting(Pair::getLeft).contains(expectedLongestWordLength);
       assertThat(longestWords).extracting(Pair::getRight).containsExactlyInAnyOrder(expectedLongestWords);
    }



}