package com.abhi.sqlitehelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DB_Helper extends SQLiteOpenHelper {

    static final String DATABASE = "Userdata";
    static final String TABLE = "User";

    public DB_Helper(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table if not exists " + TABLE + "(Name varchar(30),Phone varchar(12));";
        db.execSQL(query, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    void addUser(CustomUserData userData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("Name", userData.getName());
        values.put("Phone", userData.getPhone());

        db.insert(TABLE, null, values);
    }

    List<CustomUserData> getUsersData() {
        List<CustomUserData> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select *from " + TABLE, null);

        if (cursor.moveToFirst()) {
            do {
                CustomUserData userData = new CustomUserData(cursor.getString(0), cursor.getString(1));
                list.add(userData);
            } while (cursor.moveToNext());
        }

        return list;
    }

    void deleteUser(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE + " where Name='" + name + "';");
    }

    void updateUser(String name, CustomUserData userData) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query="update "+TABLE+" set Name='"+userData.getName()+"', Phone='"+userData.getPhone()+"' where Name='"+name+"';";

        db.execSQL(query);
    }
}


















