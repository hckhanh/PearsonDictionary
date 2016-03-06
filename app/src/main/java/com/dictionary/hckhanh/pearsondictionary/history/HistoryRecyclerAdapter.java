package com.dictionary.hckhanh.pearsondictionary.history;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.dictionary.hckhanh.pearsondictionary.history.dao.History;
import java.util.List;

/**
 * The implementation of History tab base for {@link RecyclerView}
 */
public class HistoryRecyclerAdapter
    extends RecyclerView.Adapter<HistoryRecyclerAdapter.HistoryViewHolder> {
  private List<History> histories;

  /**
   * Initialize the object
   *
   * @param histories The {@link List} of {@link History}
   */
  public HistoryRecyclerAdapter(List<History> histories) {
    this.histories = histories;
  }

  @Override public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new HistoryViewHolder(LayoutInflater.from(parent.getContext())
        .inflate(android.R.layout.simple_list_item_1, parent, false));
  }

  @Override public void onBindViewHolder(HistoryViewHolder holder, int position) {
    History history = histories.get(position);

    holder.textView.setText(history.getWord());
  }

  @Override public int getItemCount() {
    return histories == null ? 0 : histories.size();
  }

  public void setHistories(List<History> histories) {
    this.histories = histories;
    notifyDataSetChanged();
  }

  public static class HistoryViewHolder extends RecyclerView.ViewHolder {
    @Bind(android.R.id.text1) TextView textView;

    public HistoryViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
