package com.hckhanh.pearson.dict.word;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.hckhanh.pearson.dict.R;
import com.hckhanh.pearson.dict.pearson.data.Example;
import com.hckhanh.pearson.dict.pearson.data.Pronunciation;
import com.hckhanh.pearson.dict.pearson.data.Sense;
import com.hckhanh.pearson.dict.pearson.data.Word;
import java.util.List;

/**
 * The implementation of Word and More tab base for {@link RecyclerView}
 */
public class WordRecyclerAdapter extends RecyclerView.Adapter<WordRecyclerAdapter.WordViewHolder> {

  private List<Word> words;

  /**
   * Initialize the object
   *
   * @param words The {@link List} of {@link Word}
   */
  public WordRecyclerAdapter(List<Word> words) {
    this.words = words;
  }

  @Override public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return new WordViewHolder(
        LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_word, parent, false));
  }

  @Override public void onBindViewHolder(WordViewHolder holder, int position) {
    Word word = words.get(position);

    holder.wordText.setText(word.getHeadword());

    StringBuilder pronounTextBuilder = new StringBuilder();
    Pronunciation[] pronunciations = word.getPronunciations();
    if (pronunciations != null) {
      pronounTextBuilder.append(pronunciations[0].getIpa());
    }

    String partOfSpeech = word.getPartOfSpeech();
    if (partOfSpeech != null) pronounTextBuilder.append(" (").append(partOfSpeech).append(')');

    holder.pronunciationText.setText(pronounTextBuilder.toString());

    StringBuilder contentTextBuilder = new StringBuilder();
    Sense[] senses = word.getSenses();
    if (senses != null) {
      for (Sense sense : senses) {

        String[] definitions = sense.getDefinition();
        if (definitions != null) {
          for (String definition : definitions) {
            contentTextBuilder.append("<h3>").append(definition).append("</h3>");
          }
        }

        Example[] examples = sense.getExamples();
        if (examples != null) {
          for (Example example : examples) {
            contentTextBuilder.append("<p>").append(example.getText()).append("</p><br />");
          }
        }
      }
    }

    holder.contentText.setText(Html.fromHtml(contentTextBuilder.toString()));
  }

  @Override public int getItemCount() {
    return words == null ? 0 : words.size();
  }

  public void setWords(List<Word> words) {
    this.words = words;
    notifyDataSetChanged();
  }

  public static class WordViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.word_text) TextView wordText;

    @Bind(R.id.pronunciation_text) TextView pronunciationText;

    @Bind(R.id.content_text) TextView contentText;

    public WordViewHolder(View itemView) {
      super(itemView);
      ButterKnife.bind(this, itemView);
    }
  }
}
