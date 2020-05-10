package com.troweprice.util;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

class WordUtilTest {

    @Test
    void createOrderListOfWords_whenListIsNullThrowsException() {
        //When
        assertThatThrownBy(() -> WordUtil.createOrderListOfWords(null)).isInstanceOf(IllegalArgumentException.class)
                                                                       .hasMessage("List can't be null.");
    }

    @Test
    void createOrderListOfWords_removeDuplicatedElements() {
        //Given
        List<String> listWithDuplicates = List.of("abc", "abc", "bcd");

        //When
        LinkedList<String> orderedListWithoutDuplicates = WordUtil.createOrderListOfWords(listWithDuplicates);

        //Then
        assertThat(orderedListWithoutDuplicates).hasSize(2);
        assertThat(orderedListWithoutDuplicates).containsExactlyInAnyOrder("abc", "bcd");
    }

    @Test
    void createOrderListOfWords_removeEmptyElements() {
        //Given
        List<String> listWithEmptyAndBlankStrings = List.of(" ", "", "   ");

        //When
        LinkedList<String> orderedListWithoutDuplicates = WordUtil.createOrderListOfWords(listWithEmptyAndBlankStrings);

        //Then
        assertThat(orderedListWithoutDuplicates).isEmpty();
    }

    @Test
    void createOrderListOfWords_removeNullElements() {
        //Given
        List<String> listWithNullValues = new ArrayList<>();
        listWithNullValues.add(null);
        listWithNullValues.add(null);
        listWithNullValues.add(null);

        //When
        LinkedList<String> orderedListWithoutDuplicates = WordUtil.createOrderListOfWords(listWithNullValues);

        //Then
        assertThat(orderedListWithoutDuplicates).isEmpty();
    }

    @Test
    void createOrderListOfWords_listIsSuccessfullyOrdered() {
        //Given
        List<String> unorderedList = List.of("abcd", "abc", "ab", "a");

        //When
        LinkedList<String> orderedList = WordUtil.createOrderListOfWords(unorderedList);

        //Then
        assertThat(orderedList).hasSize(4);
        assertThat(orderedList).containsExactlyElementsOf(List.of("a", "ab", "abc", "abcd"));
    }
}