package com.hckhanh.pearson.dict.history;

import com.hckhanh.pearson.dict.history.dao.History;
import com.hckhanh.pearson.dict.pager.Pager;
import com.hckhanh.pearson.dict.pager.PagerFragment;
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
