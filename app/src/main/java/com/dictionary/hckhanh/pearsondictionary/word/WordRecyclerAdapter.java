package com.dictionary.hckhanh.pearsondictionary.word;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.dictionary.hckhanh.pearsondictionary.R;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Example;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Pronunciation;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Sense;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;
import java.util.List;

public class WordRecyclerAdapter extends RecyclerView.Adapter<WordRecyclerAdapter.WordViewHolder> {

  private List<Word> words;

  public WordRecyclerAdapter(List<Word> words) {
    this.words = words;
  }

  /**
   * Create the {@link WordViewHolder} of {@link WordRecyclerAdapter}
   *
   * @param parent The ViewGroup into which the new View will be added after it is bound to
   * an adapter position.
   * @param viewType The view type of the new View.
   * @return A new ViewHolder that holds a View of the given view type.
   * @see RecyclerView.Adapter#getItemViewType(int)
   * @see RecyclerView.Adapter#onBindViewHolder(RecyclerView.ViewHolder, int)
   */
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