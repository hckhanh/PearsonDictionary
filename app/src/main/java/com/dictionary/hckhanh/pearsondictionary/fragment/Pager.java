package com.dictionary.hckhanh.pearsondictionary.fragment;

import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;

import java.util.List;

public class Pager {

    String tabTitle;

    List<Word> words;

    public Pager(String title, List<Word> words) {
        this.tabTitle = title;
        this.words = words;
    }

    public String getTabTitle() {
        return tabTitle;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public void addWords(List<Word> words) {
        this.words.addAll(words);
    }
}
