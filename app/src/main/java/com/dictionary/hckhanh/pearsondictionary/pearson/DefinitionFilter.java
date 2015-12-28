package com.dictionary.hckhanh.pearsondictionary.pearson;

import com.dictionary.hckhanh.pearsondictionary.pearson.data.Definition;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;

import java.util.ArrayList;
import java.util.List;

public class DefinitionFilter {

    List<Word> meanings;

    List<Word> synonyms;

    public DefinitionFilter(Definition definition, String sampleWord) {
        meanings = new ArrayList<>();
        synonyms = new ArrayList<>();

        Word[] words = definition.getResults();
        for (Word word : words) {
            if (sampleWord.equalsIgnoreCase(word.getHeadword())) {
                meanings.add(word);
            } else {
                synonyms.add(word);
            }
        }
    }

    public List<Word> getMeanings() {
        return meanings;
    }

    public List<Word> getSynonyms() {
        return synonyms;
    }

}
