package com.example.practivca4.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class ManagerDB extends SQLiteOpenHelper {
    private static final String DB_NAME="dbUsers";
    private static final Integer VERSION = 1;
    private static final String TABLE_USER="users";

    private static final String  CREATE_TABLE_QUERY = "CREATE TABLE "+TABLE_USER+" (" +
            "user_document VARCHAR(20) PRIMARY KEY NOT NULL," +
            "user_userName VARCHAR(125) NOT NULL," +
            "user_name VARCHAR(125) NOT NULL," +
            "user_lastName VARCHAR(125) NOT NULL," +
            "user_password VARCHAR(125) NOT NULL," +
            "user_status VARCHAR(1));";

    private static final String DELETE_TABLE_QUERY= "DROP TABLE IF EXISTS "+TABLE_USER;
    public ManagerDB(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETE_TABLE_QUERY);
        onCreate(db);
    }
}
