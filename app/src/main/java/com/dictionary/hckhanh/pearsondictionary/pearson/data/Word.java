package com.dictionary.hckhanh.pearsondictionary.pearson.data;

public class Word {

    public String getId() {
        return id;
    }

    public String getHeadword() {
        return headword;
    }

    public String getPartOfSpeech() {
        return part_of_speech;
    }

    public Pronunciation[] getPronunciations() {
        return pronunciations;
    }

    public Sense[] getSenses() {
        return senses;
    }

    String id;

    String headword;

    String part_of_speech;

    Pronunciation[] pronunciations;

    Sense[] senses;

}
