package com.dictionary.hckhanh.pearsondictionary.word;

import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;
import java.util.List;

public class WordPager extends Pager {
  List<Word> words;

  public WordPager(String title, List<Word> words,
      PagerFragment pagerFragment) {
    super(title, pagerFragment);
    this.words = words;
  }

  public void setWords(List<Word> words) {
    this.words = words;
    ((WordPagerFragment) getPagerFragment()).setWords(words);
  }
}
