package com.example.learn_sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Random;

public class BiodataTable extends SQLiteOpenHelper {

    Context context;
    Cursor cursor;
    SQLiteDatabase database;

    public static String database_name = "data";
    public static String table_name = "biodata";

    public BiodataTable(@Nullable Context context) {
        super(context, database_name, null, 3);
        this.context = context;
        database = getReadableDatabase();
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + table_name + " (id varchar(50), name varchar(50), address varchar(50))";
        db.execSQL(query);
    }

    String random()
    {
        int rand = new Random().nextInt(888+1)+100;
        return String.valueOf(rand);
    }

    void save_data(String name, String address)
    {
        database.execSQL(
                "INSERT INTO " + table_name + " VALUES" +
                        "('"+ random() +"', '"+ name +"', '"+ address +"')"
        );
        Toast.makeText(context, "Data saved", Toast.LENGTH_SHORT).show();
    }

    void update_data(String id, String name, String address)
    {
        database.execSQL(
                "UPDATE " + table_name + " SET name = '"+name+"', address = '"+address+"'" +
                        " WHERE id = '"+id+"'"
        );
        Toast.makeText(context, "Data Updated!", Toast.LENGTH_SHORT).show();
    }

    Cursor show_data()
    {
        Cursor cursor = database.rawQuery("SELECT * FROM " + table_name, null);
        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
