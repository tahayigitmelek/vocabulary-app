package com.tahayigitmelek.vocabularyapp.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.tahayigitmelek.vocabularyapp.R;
import com.tahayigitmelek.vocabularyapp.adapter.FavAdapter;
import com.tahayigitmelek.vocabularyapp.dao.Dao;
import com.tahayigitmelek.vocabularyapp.dao.DatabaseCopyHelper;
import com.tahayigitmelek.vocabularyapp.dao.DatabaseHelper;
import com.tahayigitmelek.vocabularyapp.objects.Words;
import java.io.IOException;
import java.util.ArrayList;

public class FavFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private DatabaseHelper databaseHelper;

    public static FavFragment newInstance(String str, String str2) {
        FavFragment favFragment = new FavFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, str);
        bundle.putString(ARG_PARAM2, str2);
        favFragment.setArguments(bundle);
        return favFragment;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_fav, viewGroup, false);
        RecyclerView rvFavs = (RecyclerView) inflate.findViewById(R.id.rvFavs);
        DatabaseCopyHelper databaseCopyHelper = new DatabaseCopyHelper(getContext());
        databaseCopyHelper.createDataBase();
        databaseCopyHelper.openDataBase();
        rvFavs.setHasFixedSize(true);
        rvFavs.setLayoutManager(new StaggeredGridLayoutManager(1, 1));
        ArrayList<Words> wordsArrayList = new Dao().favWords(this.databaseHelper);
        FavAdapter favorilerAdapter = new FavAdapter(getContext(), wordsArrayList);
        rvFavs.setAdapter(favorilerAdapter);
        return inflate;
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.databaseHelper = new DatabaseHelper(context);
    }
}
