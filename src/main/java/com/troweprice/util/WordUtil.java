package com.troweprice.util;// Copyright (c) 2020 Travelex Ltd

import org.apache.commons.lang3.StringUtils;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class WordUtil {

    public static LinkedList<String> createOrderListOfWords(List<String> words) {
        if (Objects.isNull(words)) {
            throw new IllegalArgumentException("List can't be null.");
        }

        LinkedList<String> uniqueOrderedWords = words.stream()
                                                     .distinct()
                                                     .filter(s -> StringUtils.isNotBlank(s))
                                                     .sorted(Comparator.comparing(String::length))
                                                     .collect(Collectors.toCollection(LinkedList::new));
        return uniqueOrderedWords;
    }
}
