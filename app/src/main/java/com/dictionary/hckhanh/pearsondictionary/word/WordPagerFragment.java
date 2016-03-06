package com.dictionary.hckhanh.pearsondictionary.word;

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
import com.dictionary.hckhanh.pearsondictionary.pager.PagerFragment;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;
import com.dictionary.hckhanh.pearsondictionary.rx.RxEventBus;
import java.util.List;

public class WordPagerFragment extends PagerFragment {
  @Bind(R.id.word_list) RecyclerView wordList;

  @Bind(R.id.progress_bar) ProgressBar progressBar;

  WordRecyclerAdapter wordRecyclerAdapter;

  public void setWords(List<Word> words) {
    wordRecyclerAdapter.setWords(words);
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View page = inflater.inflate(R.layout.fragment_meaning, container, false);
    ButterKnife.bind(this, page);

    wordList.setHasFixedSize(true);
    wordList.setLayoutManager(new LinearLayoutManager(getContext()));

    wordRecyclerAdapter = new WordRecyclerAdapter(null);
    wordList.setAdapter(wordRecyclerAdapter);

    return page;
  }

  @Override public void onStart() {
    super.onStart();
    RxEventBus.getEventBus().send(new OnWordStartEvent());
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

  public static final class OnWordStartEvent {
  }
}
