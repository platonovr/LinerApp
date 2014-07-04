package com.example.linerapp.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.model.Row;

import java.util.ArrayList;

/**
 * Created by Stas on 04.07.2014.
 */
public class SqlCommand {
    private static SqlCommand sSqlCommand;
    private SqlDB db;
    private SQLiteDatabase sqlbase;

    public static SqlCommand get(Context c) {
        if (sSqlCommand == null) {
            sSqlCommand = new SqlCommand(c.getApplicationContext());
        }
        return sSqlCommand;
    }

    private SqlCommand (Context appContext) {
        db = new SqlDB(appContext);
        sqlbase = db.getWritableDatabase();
    }

    public void addRow (int id,String name){
        ContentValues cv = new ContentValues();
        cv.put(SqlDB.DB_FAVORITE_ID, id);
        cv.put(SqlDB.DB_FAVORITE_NAME, name);
        sqlbase.insert(SqlDB.DB_FAVORITE, null, cv);
    }

    public ArrayList<Row> getRows() {
        Cursor c = sqlbase.query(SqlDB.DB_FAVORITE, null, null, null, null, null, null);
        ArrayList<Row> rowArrayList = new ArrayList<>();
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex(SqlDB.DB_FAVORITE_ID);
            int nameColIndex = c.getColumnIndex(SqlDB.DB_FAVORITE_NAME);
            Row row ;
            do {
                row = new Row();
                row.setId(c.getInt(idColIndex)) ;
                row.setName(c.getString(nameColIndex));
                rowArrayList.add(row);
            } while (c.moveToNext());

        } else {
            Log.d("My", "0 rows");
        }
        c.close();
        return rowArrayList;
    }

    public boolean findRow (String name){
        String table = SqlDB.DB_FAVORITE;
        String selection =  SqlDB.DB_FAVORITE_NAME+"= ?" ;
        String[] selectionArgs = {name};
        Cursor c = sqlbase.query(table, null, selection,selectionArgs, null, null, null);

        return c.moveToFirst();
    }

    public boolean findRow (int id){
        String table = SqlDB.DB_FAVORITE;
        String selection =  SqlDB.DB_FAVORITE_ID+"= "+id ;

        Cursor c = sqlbase.query(table, null, selection,null, null, null, null);
        Log.d("My","a "+c.moveToFirst());
        return c.moveToFirst();
    }
}
