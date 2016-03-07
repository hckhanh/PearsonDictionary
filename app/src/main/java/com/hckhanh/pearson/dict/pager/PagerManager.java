package com.hckhanh.pearson.dict.pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.hckhanh.pearson.dict.history.HistoryPager;
import com.hckhanh.pearson.dict.history.dao.History;
import com.hckhanh.pearson.dict.pearson.DefinitionFilter;
import com.hckhanh.pearson.dict.word.WordPager;
import java.util.ArrayList;
import java.util.List;

public class PagerManager extends FragmentStatePagerAdapter {
  public List<Pager> pagers;

  public PagerManager(FragmentManager fm) {
    super(fm);
    pagers = new ArrayList<>();
  }

  @Override public Fragment getItem(int position) {
    return pagers.get(position).getPagerFragment();
  }

  @Override public int getCount() {
    return pagers.size();
  }

  @Override public CharSequence getPageTitle(int position) {
    return pagers.get(position).getTabTitle();
  }

  public void addPager(Pager pager) {
    pagers.add(pager);
  }

  public void setWords(DefinitionFilter filteredWords) {
    ((WordPager) pagers.get(0)).setWords(filteredWords.getMeanings());
    ((WordPager) pagers.get(1)).setWords(filteredWords.getSynonyms());
  }

  public void setHistories(List<History> histories) {
    ((HistoryPager) pagers.get(2)).setHistories(histories);
  }

  @Override public void notifyDataSetChanged() {
    super.notifyDataSetChanged();
    //pagers.get(0).getPagerFragment().notifyDataSetChanged();
    //pagers.get(1).getPagerFragment().notifyDataSetChanged();
    //pagers.get(2).getPagerFragment().notifyDataSetChanged();
  }

  public void showLoadingIndicator() {
    pagers.get(0).getPagerFragment().showLoadingIndicator();
    pagers.get(1).getPagerFragment().showLoadingIndicator();
  }

  public void hideLoadingIndicator() {
    pagers.get(0).getPagerFragment().hideLoadingIndicator();
    pagers.get(1).getPagerFragment().hideLoadingIndicator();
  }
}
