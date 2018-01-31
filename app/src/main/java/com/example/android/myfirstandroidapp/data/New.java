package com.example.android.myfirstandroidapp.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Jennifer on 1/28/2018.
 */

public class New {

    public String title;
    public String author;
    public String description;
    public String url;
    public boolean isFav;

    public New(String title, String author, String description, String url) {
        this.title = title;
        this.author = author;
        this.description = description;
        this.url = url;
        this.isFav = false;
    }

    public New(){

    }

    public void setIsFav(SQLiteDatabase mDb) {
        Cursor mCursor = mDb.query(
                NewsContract.NewsEntry.TABLE_NAME,
                null,
                NewsContract.NewsEntry.COLUMN_URL + "=?",
                new String[] {this.url},
                null,
                null,
                null
        );

        this.isFav = (mCursor.getCount() != 0);
    }
}
