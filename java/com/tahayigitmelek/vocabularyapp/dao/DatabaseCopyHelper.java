package com.tahayigitmelek.vocabularyapp.dao;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class DatabaseCopyHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "**DATABASE_NAME**.db";
    String DB_PATH;
    private final Context myContext;
    private SQLiteDatabase myDataBase;

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    @SuppressLint("SdCardPath")
    public DatabaseCopyHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
    }

    public void createDataBase() {
        if (!checkDataBase()) {
            getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException unused) {
                throw new Error("Error copying database");
            }
        }
    }

    @SuppressLint("WrongConstant")
    private boolean checkDataBase() {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            sQLiteDatabase = SQLiteDatabase.openDatabase(this.DB_PATH + DB_NAME, null, 1);
        } catch (SQLiteException ignored) {
        }
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        return sQLiteDatabase != null;
    }

    private void copyDataBase() throws IOException {
        InputStream open = this.myContext.getAssets().open(DB_NAME);
        FileOutputStream fileOutputStream = new FileOutputStream(this.DB_PATH + DB_NAME);
        byte[] bArr = new byte[1024];
        while (true) {
            int read = open.read(bArr);
            if (read > 0) {
                fileOutputStream.write(bArr, 0, read);
            } else {
                fileOutputStream.flush();
                fileOutputStream.close();
                open.close();
                return;
            }
        }
    }

    @SuppressLint("WrongConstant")
    public void openDataBase() throws SQLException {
        this.myDataBase = SQLiteDatabase.openDatabase(this.DB_PATH + DB_NAME, null, 1);
    }

    public synchronized void close() {
        SQLiteDatabase sQLiteDatabase = this.myDataBase;
        if (sQLiteDatabase != null) {
            sQLiteDatabase.close();
        }
        super.close();
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
        sQLiteDatabase.disableWriteAheadLogging();
    }
}
