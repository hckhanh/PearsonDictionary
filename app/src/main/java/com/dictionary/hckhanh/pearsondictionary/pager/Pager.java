package com.dictionary.hckhanh.pearsondictionary.pager;

public class Pager {
  String tabTitle;

  PagerFragment pagerFragment;

  public Pager(String title, PagerFragment pagerFragment) {
    this.tabTitle = title;
    this.pagerFragment = pagerFragment;
  }

  public String getTabTitle() {
    return tabTitle;
  }

  public PagerFragment getPagerFragment() {
    return pagerFragment;
  }
}
