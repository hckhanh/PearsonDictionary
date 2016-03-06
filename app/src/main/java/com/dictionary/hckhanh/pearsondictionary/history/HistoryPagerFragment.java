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
import com.dictionary.hckhanh.pearsondictionary.history.dao.History;
import com.dictionary.hckhanh.pearsondictionary.pager.PagerFragment;
import com.dictionary.hckhanh.pearsondictionary.rx.RxEventBus;
import java.util.List;

/**
 * The fragment of History screen
 */
public class HistoryPagerFragment extends PagerFragment {
  @Bind(R.id.history_list) RecyclerView historyList;

  @Bind(R.id.progress_bar) ProgressBar progressBar;

  HistoryRecyclerAdapter historyRecyclerAdapter;

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View page = inflater.inflate(R.layout.fragment_history, container, false);
    ButterKnife.bind(this, page);

    historyList.setHasFixedSize(true);
    historyList.setLayoutManager(new LinearLayoutManager(getContext()));

    historyRecyclerAdapter = new HistoryRecyclerAdapter(null);
    historyList.setAdapter(historyRecyclerAdapter);

    return page;
  }

  @Override public void onStart() {
    super.onStart();
    RxEventBus.getEventBus().send(new OnHistoryStartEvent());
  }

  /**
   * Release data
   */
  @Override public void onDestroyView() {
    super.onDestroyView();
    ButterKnife.unbind(this);
  }

  @Override public void notifyDataSetChanged() {
    historyRecyclerAdapter.notifyDataSetChanged();
  }

  @Override public void showLoadingIndicator() {
    if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
  }

  @Override public void hideLoadingIndicator() {
    if (progressBar != null) progressBar.setVisibility(View.GONE);
  }

  public void setHistories(List<History> histories) {
    historyRecyclerAdapter.setHistories(histories);
  }

  public static final class OnHistoryStartEvent {
  }
}
