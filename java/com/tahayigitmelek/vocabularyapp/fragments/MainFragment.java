package com.tahayigitmelek.vocabularyapp.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tahayigitmelek.vocabularyapp.R;
import com.tahayigitmelek.vocabularyapp.adapter.WordAdapter;
import com.tahayigitmelek.vocabularyapp.dao.Dao;
import com.tahayigitmelek.vocabularyapp.dao.DatabaseCopyHelper;
import com.tahayigitmelek.vocabularyapp.dao.DatabaseHelper;
import com.tahayigitmelek.vocabularyapp.objects.Words;
import java.io.IOException;
import java.util.ArrayList;

public class MainFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public Button buttonRefresh;
    public RecyclerView rvWords;
    private DatabaseHelper databaseHelper;

    public static MainFragment newInstance(String str, String str2) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_main, viewGroup, false);
        this.rvWords = inflate.findViewById(R.id.rvWords);
        this.buttonRefresh =  inflate.findViewById(R.id.buttonRefresh);
        ImageView imageViewWordInfo = inflate.findViewById(R.id.imageViewWordInfo);
        new DatabaseCopyHelper(getContext()).createDataBase();
        this.rvWords.setHasFixedSize(true);
        this.rvWords.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.card_anim_second));
        wordGetter();
        this.buttonRefresh.setOnClickListener(view -> {
            MainFragment.this.wordGetter();
            MainFragment.this.rvWords.startAnimation(AnimationUtils.loadAnimation(MainFragment.this.getContext(), R.anim.card_anim_first));
            MainFragment.this.buttonRefresh.startAnimation(AnimationUtils.loadAnimation(MainFragment.this.getContext(), R.anim.rotate_reflesh));
        });
        imageViewWordInfo.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainFragment.this.getActivity());
            builder.setTitle("İnformation");
            builder.setMessage("**INFO**");
            builder.setPositiveButton("OK", null).show();
        });
        return inflate;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.databaseHelper = new DatabaseHelper(context);
    }

    public void wordGetter() {
        this.rvWords.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
        ArrayList<Words> wordsArrayList = new Dao().allWords(this.databaseHelper);
        WordAdapter wordAdapter = new WordAdapter(getContext(), wordsArrayList);
        this.rvWords.setAdapter(wordAdapter);
    }
}
