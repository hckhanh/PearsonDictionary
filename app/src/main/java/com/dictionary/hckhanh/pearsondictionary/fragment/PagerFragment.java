package com.dictionary.hckhanh.pearsondictionary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dictionary.hckhanh.pearsondictionary.R;
import com.dictionary.hckhanh.pearsondictionary.adapter.WordRecyclerAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PagerFragment extends Fragment {

    @Bind(R.id.word_list)
    RecyclerView wordList;

    WordRecyclerAdapter wordRecyclerAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View page = inflater.inflate(R.layout.fragment_meaning, container, false);
        ButterKnife.bind(this, page);

        wordList.setHasFixedSize(true);
        wordList.setLayoutManager(new LinearLayoutManager(getContext()));

        wordRecyclerAdapter = new WordRecyclerAdapter(PagerManager.pagers.get(0).getWords());

        wordList.setAdapter(wordRecyclerAdapter);

        return page;
    }

}
