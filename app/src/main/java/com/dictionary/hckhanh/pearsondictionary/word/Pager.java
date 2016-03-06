package com.dictionary.hckhanh.pearsondictionary.word;

public class Pager {
  String tabTitle;

  PagerFragment pagerFragment;

  Class<? extends PagerFragment> fragmentClass;

  public Pager(String title, Class<? extends PagerFragment> fragmentClass) {
    this.tabTitle = title;
    this.fragmentClass = fragmentClass;
  }

  public String getTabTitle() {
    return tabTitle;
  }

  public PagerFragment getPagerFragment() {
    if (pagerFragment == null) {
      try {
        pagerFragment = fragmentClass.newInstance();
      } catch (InstantiationException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    return pagerFragment;
  }
}
