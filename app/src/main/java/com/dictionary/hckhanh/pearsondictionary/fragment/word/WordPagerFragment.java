package com.dictionary.hckhanh.pearsondictionary.fragment.word;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dictionary.hckhanh.pearsondictionary.R;
import com.dictionary.hckhanh.pearsondictionary.adapter.WordRecyclerAdapter;
import com.dictionary.hckhanh.pearsondictionary.fragment.PagerFragment;
import com.dictionary.hckhanh.pearsondictionary.pearson.data.Word;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WordPagerFragment extends PagerFragment {

    @Bind(R.id.word_list)
    RecyclerView wordList;

    WordRecyclerAdapter wordRecyclerAdapter;

    List<Word> words;

    public void setWords(List<Word> words) {
        this.words = words;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View page = inflater.inflate(R.layout.fragment_meaning, container, false);
        ButterKnife.bind(this, page);

        wordList.setHasFixedSize(true);
        wordList.setLayoutManager(new LinearLayoutManager(getContext()));

        wordRecyclerAdapter = new WordRecyclerAdapter(words);

        wordList.setAdapter(wordRecyclerAdapter);

        return page;
    }

    @Override
    public void notifyDataSetChanged() {
        wordRecyclerAdapter.notifyDataSetChanged();
    }

}
