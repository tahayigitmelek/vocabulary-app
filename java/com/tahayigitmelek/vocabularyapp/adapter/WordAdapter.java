package com.tahayigitmelek.vocabularyapp.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.tahayigitmelek.vocabularyapp.R;
import com.tahayigitmelek.vocabularyapp.dao.Dao;
import com.tahayigitmelek.vocabularyapp.dao.DatabaseHelper;
import com.tahayigitmelek.vocabularyapp.objects.Words;

import java.util.List;
import java.util.Locale;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.cardHolder> {
    ContentValues contentValues;
    public Context context;
    SQLiteDatabase sqLiteDatabase;
    int status;
    DatabaseHelper helper;
    public List<Words> wordList;
    public TextToSpeech textToSpeech;

    public WordAdapter(Context context, List<Words> list) {
        this.context = context;
        this.wordList = list;
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        this.helper = databaseHelper;
        this.sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    @NonNull
    public cardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new cardHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_words, viewGroup, false));
    }

    public void onBindViewHolder(final cardHolder cardholder, final int i) {
        final Words words = this.wordList.get(i);
        cardholder.textViewNative.setText(words.getNativeLanguage());
        cardholder.textViewTarget.setText(words.getTargetLanguage());
        int fav_stat = new Dao().favStatWithId(this.helper, words.getId());
        this.status = fav_stat;
        if (fav_stat == 1) {
            cardholder.checkBoxFav.setVisibility(View.VISIBLE);
        }
        cardholder.cardView.setOnClickListener(view -> {
            cardholder.textViewNative.startAnimation(AnimationUtils.loadAnimation(WordAdapter.this.context, R.anim.card_open));
            cardholder.checkBoxControl.startAnimation(AnimationUtils.loadAnimation(WordAdapter.this.context, R.anim.card_open));
            cardholder.sag_sol.startAnimation(AnimationUtils.loadAnimation(WordAdapter.this.context, R.anim.right_left));
        });
        cardholder.checkBoxControl.setOnCheckedChangeListener((compoundButton, z) -> {
            WordAdapter.this.contentValues = new ContentValues();
            if (z) {
                WordAdapter.this.contentValues.put("stat", 1);
                WordAdapter.this.sqLiteDatabase.update("words", WordAdapter.this.contentValues, "id=?", new String[]{String.valueOf((WordAdapter.this.wordList.get(i)).getId())});
                (WordAdapter.this.wordList.get(i)).setStatus(1);
                Toast.makeText(WordAdapter.this.context, "The word " + words.getTargetLanguage() + " has been added to known words.", Toast.LENGTH_SHORT).show();
                return;
            }
            WordAdapter.this.contentValues.put("stat", 0);
            WordAdapter.this.sqLiteDatabase.update("words", WordAdapter.this.contentValues, "id=?", new String[]{String.valueOf(( WordAdapter.this.wordList.get(i)).getId())});
            (WordAdapter.this.wordList.get(i)).setStatus(0);
            Toast.makeText(WordAdapter.this.context, "The word " + words.getTargetLanguage() + " has been added to unknown words.", Toast.LENGTH_SHORT).show();
        });
        cardholder.checkBoxFav.setOnCheckedChangeListener((compoundButton, z) -> {
            WordAdapter.this.contentValues = new ContentValues();
            if (z) {
                WordAdapter.this.contentValues.put("favs", 1);
                WordAdapter.this.sqLiteDatabase.update("words", WordAdapter.this.contentValues, "id=?", new String[]{String.valueOf((WordAdapter.this.wordList.get(i)).getId())});
                (WordAdapter.this.wordList.get(i)).setStatus(1);
                Toast.makeText(WordAdapter.this.context, "'" + words.getTargetLanguage() + "' word added to favorite words.", Toast.LENGTH_SHORT).show();
                return;
            }
            WordAdapter.this.contentValues.put("favs", 0);
            WordAdapter.this.sqLiteDatabase.update("words", WordAdapter.this.contentValues, "id=?", new String[]{String.valueOf((WordAdapter.this.wordList.get(i)).getId())});
            (WordAdapter.this.wordList.get(i)).setStatus(0);
            Toast.makeText(WordAdapter.this.context, "The word '" + words.getTargetLanguage() + "' has been removed from favorite words.", Toast.LENGTH_SHORT).show();
        });
        this.textToSpeech = new TextToSpeech(this.context, i1 -> {
            if (i1 != -1) {
                WordAdapter.this.textToSpeech.setLanguage(Locale.FRENCH);
            }
        });
        cardholder.imageViewSound.setOnClickListener(view -> WordAdapter.this.textToSpeech.speak(cardholder.textViewTarget.getText().toString(), 0, null));
    }

    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.textToSpeech.stop();
        this.textToSpeech.shutdown();
    }

    public int getItemCount() {
        return this.wordList.size();
    }

    public static class cardHolder extends RecyclerView.ViewHolder {

        public CardView cardView;
        public ImageView imageViewSound;
        public CheckBox checkBoxFav, checkBoxControl;
        public TextView sag_sol, textViewTarget, textViewNative;

        public cardHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.cardViewWords);
            this.textViewTarget = view.findViewById(R.id.textViewTarget);
            this.textViewNative = view.findViewById(R.id.textViewNative);
            this.checkBoxControl = view.findViewById(R.id.checkBoxControl);
            this.checkBoxFav = view.findViewById(R.id.checkBoxFav);
            this.sag_sol = view.findViewById(R.id.leftRight);
            this.imageViewSound = view.findViewById(R.id.imageViewSound);
        }
    }
}
