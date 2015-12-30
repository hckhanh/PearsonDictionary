package com.dictionary.hckhanh.pearsondictionary.fragment.word;

import com.dictionary.hckhanh.pearsondictionary.fragment.Pager;
import com.dictionary.hckhanh.pearsondictionary.fragment.PagerFragment;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;

import java.util.List;

public class WordPager extends Pager {

    List<Word> words;

    public WordPager(String title, List<Word> words, Class<? extends WordPagerFragment> fragmentClass) {
        super(title, fragmentClass);
        this.words = words;
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

    @Override
    public PagerFragment getPagerFragment() {
        WordPagerFragment wordPagerFragment = (WordPagerFragment) super.getPagerFragment();
        wordPagerFragment.setWords(words);

        return wordPagerFragment;
    }
}
