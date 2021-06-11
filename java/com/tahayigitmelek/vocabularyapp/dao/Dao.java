package com.tahayigitmelek.vocabularyapp.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.tahayigitmelek.vocabularyapp.objects.Sentence;
import com.tahayigitmelek.vocabularyapp.objects.Words;
import java.util.ArrayList;

public class Dao {

    public Sentence getSentence(DatabaseHelper databaseHelper, int i) {
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT * FROM words WHERE id =" + i, (String[]) null);
        rawQuery.moveToNext();
        return new Sentence(rawQuery.getInt(rawQuery.getColumnIndex("id")), rawQuery.getString(rawQuery.getColumnIndex("sen1")), rawQuery.getString(rawQuery.getColumnIndex("sen2")), rawQuery.getString(rawQuery.getColumnIndex("sen3")));
    }

    public void addSentence(DatabaseHelper databaseHelper, int id, String str, String str2, String str3) {
        SQLiteDatabase writableDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("sen1", str);
        contentValues.put("sen2", str2);
        contentValues.put("sen3", str3);
        writableDatabase.update("words", contentValues, "id = ?", new String[]{String.valueOf(id)});
    }

    public Words getFav(DatabaseHelper databaseHelper, int i) {
        new Words();
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT * FROM words WHERE id=" + i, (String[]) null);
        rawQuery.moveToNext();
        return new Words(rawQuery.getInt(rawQuery.getColumnIndex("id")), rawQuery.getString(rawQuery.getColumnIndex("target_language")), rawQuery.getString(rawQuery.getColumnIndex("native_language")), rawQuery.getInt(rawQuery.getColumnIndex("stat")));
    }

    public ArrayList<Words> allWords(DatabaseHelper databaseHelper) {
        ArrayList<Words> arrayList = new ArrayList<>();
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT * FROM words WHERE stat=0 ORDER BY RANDOM() LIMIT 1", (String[]) null);
        while (rawQuery.moveToNext()) {
            arrayList.add(new Words(rawQuery.getInt(rawQuery.getColumnIndex("id")), rawQuery.getString(rawQuery.getColumnIndex("target_language")), rawQuery.getString(rawQuery.getColumnIndex("native_language")), rawQuery.getInt(rawQuery.getColumnIndex("stat"))));
        }
        return arrayList;
    }

    public ArrayList<Words> knownWords(DatabaseHelper databaseHelper) {
        ArrayList<Words> arrayList = new ArrayList<>();
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT * FROM words WHERE stat = 1", (String[]) null);
        while (rawQuery.moveToNext()) {
            arrayList.add(new Words(rawQuery.getInt(rawQuery.getColumnIndex("id")), rawQuery.getString(rawQuery.getColumnIndex("target_language")), rawQuery.getString(rawQuery.getColumnIndex("native_language")), rawQuery.getInt(rawQuery.getColumnIndex("stat"))));
        }
        return arrayList;
    }

    public ArrayList<Words> favWords(DatabaseHelper databaseHelper) {
        ArrayList<Words> arrayList = new ArrayList<>();
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT * FROM words WHERE favs = 1", (String[]) null);
        while (rawQuery.moveToNext()) {
            arrayList.add(new Words(rawQuery.getInt(rawQuery.getColumnIndex("id")), rawQuery.getString(rawQuery.getColumnIndex("target_language")), rawQuery.getString(rawQuery.getColumnIndex("native_language")), rawQuery.getInt(rawQuery.getColumnIndex("stat"))));
        }
        return arrayList;
    }

    public ArrayList<Words> queryWords(DatabaseHelper databaseHelper, String str) {
        ArrayList<Words> arrayList = new ArrayList<>();
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT * FROM words WHERE favs=1 AND target_language like '" + str + "%';", (String[]) null);
        while (rawQuery.moveToNext()) {
            arrayList.add(new Words(rawQuery.getInt(rawQuery.getColumnIndex("id")), rawQuery.getString(rawQuery.getColumnIndex("target_language")), rawQuery.getString(rawQuery.getColumnIndex("native_language")), rawQuery.getInt(rawQuery.getColumnIndex("stat"))));
        }
        return arrayList;
    }

    public ArrayList<Words> queryKnown(DatabaseHelper databaseHelper, String str) {
        ArrayList<Words> arrayList = new ArrayList<>();
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT * FROM words WHERE stat=1 AND target_language like '" + str + "%';", (String[]) null);
        while (rawQuery.moveToNext()) {
            arrayList.add(new Words(rawQuery.getInt(rawQuery.getColumnIndex("id")), rawQuery.getString(rawQuery.getColumnIndex("target_language")), rawQuery.getString(rawQuery.getColumnIndex("native_language")), rawQuery.getInt(rawQuery.getColumnIndex("stat"))));
        }
        return arrayList;
    }

    public int unknownStat(DatabaseHelper databaseHelper) {
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT COUNT(*) as count FROM words WHERE stat=0", (String[]) null);
        rawQuery.moveToNext();
        return rawQuery.getInt(rawQuery.getColumnIndex("deneme"));
    }

    public int favStatWithId(DatabaseHelper databaseHelper, int id) {
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT * FROM words where id=" + id, (String[]) null);
        rawQuery.moveToNext();
        return rawQuery.getInt(rawQuery.getColumnIndex("favs"));
    }

    public int favStat(DatabaseHelper databaseHelper) {
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT COUNT(*) as count FROM words WHERE favs=1;", (String[]) null);
        rawQuery.moveToNext();
        return rawQuery.getInt(rawQuery.getColumnIndex("count"));
    }

    public int knownStat(DatabaseHelper databaseHelper) {
        Cursor rawQuery = databaseHelper.getWritableDatabase().rawQuery("SELECT COUNT(*) as count FROM words WHERE stat=1;", (String[]) null);
        rawQuery.moveToNext();
        return rawQuery.getInt(rawQuery.getColumnIndex("count"));
    }
}
