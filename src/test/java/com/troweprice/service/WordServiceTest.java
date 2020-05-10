package com.troweprice.service;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.troweprice.strategy.SearchWordStrategy;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class WordServiceTest {

    @Mock
    SearchWordStrategy searchWordStrategy;

    @InjectMocks
    WordService wordService;

    @Test
    void findWord_returnsEmptyOptionalWhenTextIsNull() {
        //When
        Optional<Pair<Integer, Set<String>>> word = wordService.findWord(null);

        //Then
        assertThat(word.isEmpty()).isTrue();
        verifyNoInteractions(searchWordStrategy);
    }

    @Test
    void findWord_returnsEmptyOptionalWhenTextIsEmpty() {
        //When
        Optional<Pair<Integer, Set<String>>> word = wordService.findWord("");

        //Then
        assertThat(word.isEmpty()).isTrue();
        verifyNoInteractions(searchWordStrategy);
    }

    @Test
    void findWord_returnsEmptyOptionalWhenTextOnlyHasWhitespaces() {
        //When
        Optional<Pair<Integer, Set<String>>> word = wordService.findWord("          ");

        //Then
        assertThat(word.isEmpty()).isTrue();
        verifyNoInteractions(searchWordStrategy);
    }

    @Test
    void findWord_successfully() {
        //Given
        String givenText = "The cow jumped over the moon.";
        when(searchWordStrategy.findWord(any())).thenReturn(Optional.of(Pair.of(6, Set.of("jumped"))));

        Set<String> expectedWords = Set.of("jumped");
        int expectedLength = 6;
        List<String> expectedWordsSentToStrategyFinder = List.of("the", "cow", "jumped", "over", "the", "moon");

        //When
        Optional<Pair<Integer, Set<String>>> returnedWords = wordService.findWord(givenText);

        //Then
        assertThat(returnedWords.isPresent()).isTrue();
        Pair<Integer, Set<String>> words = returnedWords.get();
        assertThat(words).extracting(Pair::getLeft).contains(expectedLength);
        assertThat(words).extracting(Pair::getRight).containsExactly(expectedWords);

        ArgumentCaptor<List<String>> wordSearchStrategyCaptor = ArgumentCaptor.forClass(List.class);
        verify(searchWordStrategy).findWord(wordSearchStrategyCaptor.capture());
        List<String> wordSearchStrategyCaptorValue = wordSearchStrategyCaptor.getValue();
        assertThat(wordSearchStrategyCaptorValue).containsOnlyElementsOf(expectedWordsSentToStrategyFinder);
    }


    @Test
    void findWord_findsMultipleWordsOfSameSize() {
        //Given
        String givenText = "The cow jumped over the moon and walked back home.";
        when(searchWordStrategy.findWord(any())).thenReturn(Optional.of(Pair.of(6, Set.of("jumped", "walked"))));

        Set<String> expectedWords = Set.of("jumped", "walked");
        int expectedLength = 6;

        List<String> expectedWordsSentToStrategyFinder = List.of("the", "cow", "jumped", "over", "the", "moon", "and", "walked", "back", "home");

        //When
        Optional<Pair<Integer, Set<String>>> returnedWords = wordService.findWord(givenText);

        //Then
        assertThat(returnedWords.isPresent()).isTrue();
        Pair<Integer, Set<String>> words = returnedWords.get();
        assertThat(words).extracting(Pair::getLeft).contains(expectedLength);
        assertThat(words).extracting(Pair::getRight).containsExactly(expectedWords);

        ArgumentCaptor<List<String>> wordSearchStrategyCaptor = ArgumentCaptor.forClass(List.class);
        verify(searchWordStrategy).findWord(wordSearchStrategyCaptor.capture());
        List<String> wordSearchStrategyCaptorValue = wordSearchStrategyCaptor.getValue();
        assertThat(wordSearchStrategyCaptorValue).containsOnlyElementsOf(expectedWordsSentToStrategyFinder);
    }

    @Test
    void findWord_whenTextHasSpecialCharactersTheyShouldBeIgnored() {
        //Given
        String givenText = "The! . ;\" cow     [jumped]{}      @ £  ,over )( the % $moon.";
        when(searchWordStrategy.findWord(any())).thenReturn(Optional.of(Pair.of(6, Set.of("jumped"))));

        Set<String> expectedWords = Set.of("jumped");
        int expectedLength = 6;
        List<String> expectedWordsSentToStrategyFinder = List.of("the", "cow", "jumped", "over", "the", "moon");

        //When
        Optional<Pair<Integer, Set<String>>> returnedWords = wordService.findWord(givenText);

        //Then
        assertThat(returnedWords.isPresent()).isTrue();
        Pair<Integer, Set<String>> words = returnedWords.get();
        assertThat(words).extracting(Pair::getLeft).contains(expectedLength);
        assertThat(words).extracting(Pair::getRight).containsExactly(expectedWords);

        ArgumentCaptor<List<String>> wordSearchStrategyCaptor = ArgumentCaptor.forClass(List.class);
        verify(searchWordStrategy).findWord(wordSearchStrategyCaptor.capture());
        List<String> wordSearchStrategyCaptorValue = wordSearchStrategyCaptor.getValue();
        assertThat(wordSearchStrategyCaptorValue).containsOnlyElementsOf(expectedWordsSentToStrategyFinder);
    }

    @Test
    void findWord_whenTextHasNumbersTheyShouldBeIgnored() {
        //Given
        String givenText = "The cow jumped over the moon 100000 times.";
        when(searchWordStrategy.findWord(any())).thenReturn(Optional.of(Pair.of(6, Set.of("jumped"))));

        Set<String> expectedWords = Set.of("jumped");
        int expectedLength = 6;
        List<String> expectedWordsSentToStrategyFinder = List.of("the", "cow", "jumped", "over", "the", "moon", "times");

        //When
        Optional<Pair<Integer, Set<String>>> returnedWords = wordService.findWord(givenText);

        //Then
        assertThat(returnedWords.isPresent()).isTrue();
        Pair<Integer, Set<String>> words = returnedWords.get();
        assertThat(words).extracting(Pair::getLeft).contains(expectedLength);
        assertThat(words).extracting(Pair::getRight).containsExactlyInAnyOrder(expectedWords);

        ArgumentCaptor<List<String>> wordSearchStrategyCaptor = ArgumentCaptor.forClass(List.class);
        verify(searchWordStrategy).findWord(wordSearchStrategyCaptor.capture());
        List<String> wordSearchStrategyCaptorValue = wordSearchStrategyCaptor.getValue();
        assertThat(wordSearchStrategyCaptorValue).containsOnlyElementsOf(expectedWordsSentToStrategyFinder);
    }

}