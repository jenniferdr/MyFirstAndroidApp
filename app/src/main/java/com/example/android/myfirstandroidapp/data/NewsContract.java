package com.example.android.myfirstandroidapp.data;

import android.provider.BaseColumns;

/**
 * Created by Jennifer on 1/30/2018.
 */

public class NewsContract {

    public static final class NewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "NewItem";
        public static final String COLUMN_TITLE = "newsTitle";
        public static final String COLUMN_AUTHOR = "newsAuthor";
        public static final String COLUMN_URL = "newsURL";
    }
}
