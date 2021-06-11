package com.tahayigitmelek.vocabularyapp.adapter;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tahayigitmelek.vocabularyapp.R;
import com.tahayigitmelek.vocabularyapp.dao.DatabaseHelper;
import com.tahayigitmelek.vocabularyapp.objects.Words;
import java.util.List;

public class KnownWordsAdapter extends RecyclerView.Adapter<KnownWordsAdapter.cardHolder> {

    ContentValues contentValues;
    public Context context;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    public List<Words> wordList;

    public KnownWordsAdapter(Context context2, List<Words> list) {
        this.context = context2;
        this.wordList = list;
        DatabaseHelper databaseHelper = new DatabaseHelper(context2);
        this.databaseHelper = databaseHelper;
        this.sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    @NonNull
    public cardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new cardHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_knownwords, viewGroup, false));
    }

    public void onBindViewHolder(final cardHolder cardholder2, final int i) {
        final Words kelimeler = this.wordList.get(i);
        cardholder2.textViewNative.setText(kelimeler.getNativeLanguage());
        cardholder2.textViewTarget.setText(kelimeler.getTargetLanguage());
        cardholder2.delete.setOnClickListener(view -> {
            KnownWordsAdapter.this.contentValues = new ContentValues();
            AlertDialog.Builder builder = new AlertDialog.Builder(KnownWordsAdapter.this.context);
            builder.setTitle("Deletion process!");
            builder.setMessage("Do you want to delete the word " + kelimeler.getTargetLanguage() + "?");
            builder.setNegativeButton("No", null);
            builder.setPositiveButton("Yes", (dialogInterface, i1) -> {
                KnownWordsAdapter.this.contentValues.put("stat", 0);
                KnownWordsAdapter.this.sqLiteDatabase.update("words", KnownWordsAdapter.this.contentValues, "id=?", new String[]{String.valueOf((KnownWordsAdapter.this.wordList.get(i1)).getId())});
                KnownWordsAdapter.this.wordList.get(i1).setStatus(0);
                KnownWordsAdapter.this.wordList.remove(i1);
                KnownWordsAdapter.this.notifyItemRemoved(i1);
                KnownWordsAdapter.this.notifyItemRangeChanged(i1, KnownWordsAdapter.this.wordList.size());
            }).show();
        });
        cardholder2.cardView.setOnClickListener(view -> cardholder2.delete.startAnimation(AnimationUtils.loadAnimation(KnownWordsAdapter.this.context, R.anim.shake)));
    }

    public int getItemCount() {
        return this.wordList.size();
    }

    public static class cardHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public Button delete;
        public TextView textViewTarget;
        public TextView textViewNative;

        public cardHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.cardViewKnown);
            this.textViewTarget = view.findViewById(R.id.textViewKnownTarget);
            this.textViewNative = view.findViewById(R.id.textViewKnownNative);
            this.delete = view.findViewById(R.id.buttonKnownDelete);
        }
    }
}
