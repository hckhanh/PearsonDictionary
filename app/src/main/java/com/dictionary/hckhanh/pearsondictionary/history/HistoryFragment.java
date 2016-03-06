package com.dictionary.hckhanh.pearsondictionary.history;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.dictionary.hckhanh.pearsondictionary.R;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;
import com.dictionary.hckhanh.pearsondictionary.word.PagerFragment;
import com.dictionary.hckhanh.pearsondictionary.word.WordRecyclerAdapter;
import java.util.List;

/**
 * The fragment of History screen
 */
public class HistoryFragment extends PagerFragment {
  @Bind(R.id.history_list) RecyclerView historyList;

  @Bind(R.id.progress_bar) ProgressBar progressBar;

  WordRecyclerAdapter wordRecyclerAdapter;

  public void setWords(List<Word> words) {
    wordRecyclerAdapter.setWords(words);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View page = inflater.inflate(R.layout.fragment_meaning, container, false);
    ButterKnife.bind(this, page);

    historyList.setHasFixedSize(true);
    historyList.setLayoutManager(new LinearLayoutManager(getContext()));

    wordRecyclerAdapter = new WordRecyclerAdapter(null);

    historyList.setAdapter(wordRecyclerAdapter);

    return page;
  }

  /**
   * Release data
   */
  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void notifyDataSetChanged() {
    wordRecyclerAdapter.notifyDataSetChanged();
  }

  @Override public void showLoadingIndicator() {
    if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoadingIndicator() {
    if (progressBar != null) progressBar.setVisibility(View.GONE);
  }
}
