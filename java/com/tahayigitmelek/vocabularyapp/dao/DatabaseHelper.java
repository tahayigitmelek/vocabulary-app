package com.tahayigitmelek.vocabularyapp.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "**DATABASE_NAME**.db", null, 1);
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS \"words\" " +
                "(\n\t\"id\"\tINTEGER PRIMARY KEY AUTOINCREMENT," +
                "\n\t\"target_language\"\tTEXT," +
                "\n\t\"native_language\"\tTEXT," +
                "\n\t\"stat\"\tINTEGER DEFAULT 0," +
                "\n\t\"favs\"\tINTEGER DEFAULT 0," +
                "\n\t\"sen1\"\tTEXT," +
                "\n\t\"sen2\"\tTEXT," +
                "\n\t\"sen3\"\tTEXT\n);");
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS words", null);
        onCreate(sQLiteDatabase);
    }
}
