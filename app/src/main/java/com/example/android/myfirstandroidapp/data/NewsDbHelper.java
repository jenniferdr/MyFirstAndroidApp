package com.example.android.myfirstandroidapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Jennifer on 1/30/2018.
 */

public class NewsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "newsdb.db";
    private static final int DATABASE_VERSION = 1;

    public NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + NewsContract.NewsEntry.TABLE_NAME + " (" +
                NewsContract.NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NewsContract.NewsEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                NewsContract.NewsEntry.COLUMN_AUTHOR + " TEXT , " +
                NewsContract.NewsEntry.COLUMN_URL + " TEXT NOT NULL" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // TODO : Alter table instead of dropping it
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NewsContract.NewsEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
