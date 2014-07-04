package com.example.linerapp.app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Stas on 04.07.2014.
 */
public class SqlDB extends SQLiteOpenHelper {

    public static final String DB_NAME = "LinerDB1";
    public static final String DB_FAVORITE ="favorite";
    public static final String DB_FAVORITE_ID ="id";
    public static final String DB_FAVORITE_NAME ="comp_name";

    public SqlDB(Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+DB_FAVORITE+" ("
                +DB_FAVORITE_ID+" integer primary key," +DB_FAVORITE_NAME +" text" + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {

    }
}
