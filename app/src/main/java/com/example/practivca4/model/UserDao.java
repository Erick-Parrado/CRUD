package com.example.practivca4.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.hardware.usb.UsbRequest;
import android.util.Log;
import android.view.View;

import com.example.practivca4.entities.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class UserDao {
    private ManagerDB managerDB;
    Context context;
    View view;
    User user;

    public UserDao(Context context, View view) {
        this.context = context;
        this.view = view;
        this.managerDB = new ManagerDB(context);
    }

    public void insertUser(User user){
        try{
            SQLiteDatabase db = managerDB.getWritableDatabase();
            if(db != null) {
                ContentValues values = new ContentValues();
                values.put("user_document",user.getDocument());
                values.put("user_userName",user.getUserName());
                values.put("user_name",user.getName());
                values.put("user_lastName",user.getLastName());
                values.put("user_password",user.getPassword());
                values.put("user_status","1");
                long cod = db.insert("users",null,values);
                Snackbar.make(this.view,"The user has registered "+cod,Snackbar.LENGTH_LONG).show();
            }
            else {
                Snackbar.make(this.view,"The user couldn't registered",Snackbar.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            Log.i("Data base error",""+e);
        }
    }

    public void updateUser(User user){
        try{
            SQLiteDatabase db = managerDB.getWritableDatabase();
            if(db != null) {
                String selection = "user_document LIKE = ?";
                String[] selectionArgs ={user.getDocument().toString()};
                ContentValues values = new ContentValues();
                HashMap<String,String> fields = user.toArray();
                Iterator iterator = fields.entrySet().iterator();
                while (iterator.hasNext()){
                    String key = iterator.next().toString();
                    values.put(key,fields.get(key));
                }
                values.put("user_status","1");
                long cod = db.update("users",values,selection,selectionArgs);
                Snackbar.make(this.view,"The user " +user.getDocument()+ " has update "+cod,Snackbar.LENGTH_LONG).show();
            }
            else {
                Snackbar.make(this.view,"The user couldn't update",Snackbar.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            Log.i("Data base error",""+e);
        }
    }

    public void deleteUser(Integer id){
        try{
            SQLiteDatabase db = managerDB.getWritableDatabase();
            if(db != null) {
                String selection = "user_document LIKE = ?";
                String[] selectionArgs ={id.toString()};
                ContentValues values = new ContentValues();
                values.put("user_status","0");
                long cod = db.update("users",values,selection,selectionArgs);
                Snackbar.make(this.view,"The user " +user.getDocument()+ " has deleted "+cod,Snackbar.LENGTH_LONG).show();
            }
            else {
                Snackbar.make(this.view,"The user couldn't delete",Snackbar.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            Log.i("Data base error",""+e);
        }
    }

    public ArrayList<User> getUsers(){
        String query = "SELECT * FROM users WHERE user_status = 1";
        return getUsersExecuter(query);
    }

    public ArrayList<User> getUsers(User user){
        HashMap<String,String> conditions = user.toArray();
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT * FROM users WHERE user_status = 1");
        Iterator iterator = conditions.entrySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next().toString();
            queryBuilder.append(" AND ");
            queryBuilder.append(key);
            queryBuilder.append(" = ");
            queryBuilder.append(conditions.get(key));
        }
        String query = queryBuilder.toString();
        return getUsersExecuter(query);
    }
    public ArrayList<User> getUsersExecuter(String query){
        SQLiteDatabase db = managerDB.getReadableDatabase();
        ArrayList<User> resultList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                User userTemp = new User();
                userTemp.setDocument(cursor.getInt(0));
                userTemp.setUserName(cursor.getString(1));
                userTemp.setName(cursor.getString(2));
                userTemp.setLastName(cursor.getString(3));
                userTemp.setPassword(cursor.getString(4));
                resultList.add(userTemp);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return resultList;
    }
}
