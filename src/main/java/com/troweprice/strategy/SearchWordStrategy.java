package com.troweprice.strategy;

import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SearchWordStrategy {

   Optional<Pair<Integer,Set<String>>> findWord(List<String> words);
}
