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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.textfield.TextInputLayout;
import com.tahayigitmelek.vocabularyapp.R;
import com.tahayigitmelek.vocabularyapp.dao.Dao;
import com.tahayigitmelek.vocabularyapp.dao.DatabaseHelper;
import com.tahayigitmelek.vocabularyapp.objects.Sentence;
import com.tahayigitmelek.vocabularyapp.objects.Words;

import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.cardHolder> {

    ContentValues contentValues;
    public Context context;
    SQLiteDatabase sqLiteDatabase;
    DatabaseHelper databaseHelper;
    public Words word;
    public List<Words> wordList;

    public FavAdapter(Context context, List<Words> list) {
        this.context = context;
        this.wordList = list;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.databaseHelper = databaseHelper;
        this.sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    @NonNull
    public cardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new cardHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_favs, viewGroup, false));
    }

    public void onBindViewHolder(final cardHolder cardholder, final int i) {
        Words words = this.wordList.get(i);
        cardholder.textViewFav.setText(words.getTargetLanguage());
        cardholder.textViewFavNative.setText(words.getNativeLanguage());
        int id = words.getId();
        this.word = new Dao().getFav(this.databaseHelper, id);
        Sentence sentence = new Dao().getSentence(this.databaseHelper, id);
        cardholder.textInputLayoutFirst.getEditText().setText(sentence.getFirstSentence());
        cardholder.textInputLayoutSecond.getEditText().setText(sentence.getSecondSentence());
        cardholder.textInputLayoutThird.getEditText().setText(sentence.getThirdSentence());
        cardholder.buttonPen.setOnClickListener(view -> {
            new Dao().addSentence(FavAdapter.this.databaseHelper, FavAdapter.this.word.getId(), cardholder.textInputLayoutFirst.getEditText().getText().toString(), cardholder.textInputLayoutSecond.getEditText().getText().toString(), cardholder.textInputLayoutThird.getEditText().getText().toString());
            Toast.makeText(FavAdapter.this.context, "Registration Successful!", Toast.LENGTH_SHORT).show();
        });
        cardholder.buttonDeleteFav.setOnClickListener(view -> {
            FavAdapter.this.contentValues = new ContentValues();
            AlertDialog.Builder builder = new AlertDialog.Builder(FavAdapter.this.context);
            builder.setTitle("Attention!");
            builder.setMessage("If you remove the word from my favorites, the sentences you created will not be deleted, but it can be difficult to get the word back.");
            builder.setNegativeButton("No", null);
            builder.setPositiveButton("Yes", (dialogInterface, i1) -> {
                FavAdapter.this.contentValues.put("favs", 0);
                FavAdapter.this.sqLiteDatabase.update("words", FavAdapter.this.contentValues, "id=?", new String[]{String.valueOf((FavAdapter.this.wordList.get(i1)).getId())});
                FavAdapter.this.wordList.get(i1).setStatus(0);
                FavAdapter.this.wordList.remove(i1);
                FavAdapter.this.notifyItemRemoved(i1);
                FavAdapter.this.notifyItemRangeChanged(i1, FavAdapter.this.wordList.size());
            }).show();
        });
        cardholder.cardView.setOnClickListener(view -> {
            cardholder.buttonDeleteFav.startAnimation(AnimationUtils.loadAnimation(FavAdapter.this.context, R.anim.shake));
            cardholder.buttonPen.startAnimation(AnimationUtils.loadAnimation(FavAdapter.this.context, R.anim.shake));
        });
    }

    public int getItemCount() {
        return this.wordList.size();
    }

    public static class cardHolder extends RecyclerView.ViewHolder {

        public Button buttonDeleteFav;
        public Button buttonPen;
        public CardView cardView;
        public TextView textViewFav, textViewFavNative;
        public TextInputLayout textInputLayoutFirst, textInputLayoutSecond, textInputLayoutThird;

        public cardHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.cardViewFav);
            this.textViewFav = view.findViewById(R.id.textViewFav);
            this.textViewFavNative = view.findViewById(R.id.textViewFavNative);
            this.buttonPen = view.findViewById(R.id.buttonPen);
            this.buttonDeleteFav = view.findViewById(R.id.buttonDeleteFav);
            this.textInputLayoutFirst = view.findViewById(R.id.textInputLayoutFirst);
            this.textInputLayoutSecond = view.findViewById(R.id.textInputLayoutSecond);
            this.textInputLayoutThird = view.findViewById(R.id.textInputLayoutThird);
        }
    }

}
