package com.dictionary.hckhanh.pearsondictionary.history;

import com.dictionary.hckhanh.pearsondictionary.history.dao.History;
import com.dictionary.hckhanh.pearsondictionary.pager.Pager;
import com.dictionary.hckhanh.pearsondictionary.pager.PagerFragment;
import java.util.List;

/**
 * The implementation of {@link Pager} for History screen
 */
public class HistoryPager extends Pager {
  public HistoryPager(String title, PagerFragment pagerFragment) {
    super(title, pagerFragment);
  }

  public void setHistories(List<History> histories) {
    ((HistoryPagerFragment) getPagerFragment()).setHistories(histories);
  }
}
