package com.dictionary.hckhanh.pearsondictionary.word;

import com.dictionary.hckhanh.pearsondictionary.pager.Pager;
import com.dictionary.hckhanh.pearsondictionary.pager.PagerFragment;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;
import java.util.List;

public class WordPager extends Pager {
  public WordPager(String title, PagerFragment pagerFragment) {
    super(title, pagerFragment);
  }

  public void setWords(List<Word> words) {
    ((WordPagerFragment) getPagerFragment()).setWords(words);
  }
}
