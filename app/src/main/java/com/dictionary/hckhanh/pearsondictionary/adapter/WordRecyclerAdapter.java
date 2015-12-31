package com.dictionary.hckhanh.pearsondictionary.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dictionary.hckhanh.pearsondictionary.R;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Example;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Pronunciation;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Sense;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WordRecyclerAdapter extends RecyclerView.Adapter<WordRecyclerAdapter.WordViewHolder> {

    List<Word> words;

    public WordRecyclerAdapter(List<Word> words) {
        this.words = words;
    }

    @Override
    public WordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new WordViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.layout_word, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(WordViewHolder holder, int position) {
        Word word = words.get(position);

        holder.wordText.setText(word.getHeadword());

        StringBuilder pronunTextBuilder = new StringBuilder();
        Pronunciation[] pronunciations = word.getPronunciations();
        if (pronunciations != null) {
            pronunTextBuilder.append(pronunciations[0].getIpa());
        }
        pronunTextBuilder.append(" (").append(word.getPartOfSpeech()).append(')');
        holder.pronunciationText.setText(pronunTextBuilder.toString());

        StringBuilder contentTextBuilder = new StringBuilder();
        Sense[] senses = word.getSenses();
        if (senses != null) {
            for (Sense sense : senses) {

                String[] defs = sense.getDefinition();
                if (defs != null) {
                    for (String def : defs) {
                        contentTextBuilder.append("<h3>").append(def).append("</h3>");
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

    @Override
    public int getItemCount() {
        return words.size();
    }

    public static class WordViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.word_text)
        TextView wordText;

        @Bind(R.id.pronunciation_text)
        TextView pronunciationText;

        @Bind(R.id.content_text)
        TextView contentText;

        public WordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
