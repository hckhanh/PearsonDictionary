package com.hckhanh.pearson.dict.word;

import com.hckhanh.pearson.dict.pager.Pager;
import com.hckhanh.pearson.dict.pager.PagerFragment;
import com.hckhanh.pearson.dict.pearson.data.Word;
import java.util.List;

public class WordPager extends Pager {
  public WordPager(String title, PagerFragment pagerFragment) {
    super(title, pagerFragment);
  }

  public void setWords(List<Word> words) {
    ((WordPagerFragment) getPagerFragment()).setWords(words);
  }
}
