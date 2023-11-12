package com.example.practivca4.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.SQLException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.practivca4.entities.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


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

    public UserDao(Context context, View view,User user) {
        this.context = context;
        this.view = view;
        this.managerDB = new ManagerDB(context);
        this.user = user;
    }

    //Validations
    private void validate(String patternStr,String field,String message) throws Exception{
        Pattern pattern = Pattern.compile(patternStr);
        Matcher validator = pattern.matcher(field.toString());
        if(!validator.find()){
            throw new Exception(message);
        }
    }
    private void validateDocument() throws Exception {
        String patternStr ="[0-9]{8,10}";
        validate(patternStr,this.user.getDocument(),"Document hasn't match");
    }
    private void validateUserName() throws Exception {
        String patternStr = "([a-zA-Z0-9_.]{8})@(gmail.com|uniempresiar.edu.co|hotmail.com|yahoo.es)";
        validate(patternStr, this.user.getUserName(), "Email isn't correct");
    }

    private void validatePassword() throws Exception{
        String patternStr = "^(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[0-9]+)(?=.*[!@#$%^&*(){}\\[\\]]+)[a-zA-Z0-9!@#$%^&*(){}\\[\\]]{8,}$";
        validate(patternStr,this.user.getPassword(),"Password missed any require");
    }

    private ContentValues putsOfUser(){
        ContentValues values = new ContentValues();
        HashMap<String,String> fields = this.user.toArray();
        Iterator iterator = fields.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next().toString();
            values.put(key,fields.get(key));
        }
        return values;
    }
    public void insertUser(){
        try{
            SQLiteDatabase db = managerDB.getWritableDatabase();
            if(db != null) {
                ContentValues values = putsOfUser();
                values.put("user_status","1");
                Snackbar.make(this.view,values.toString(),Snackbar.LENGTH_LONG).show();
                long cod = db.insert("users",null,values);
            }
            else {
                Snackbar.make(this.view,"The user couldn't registered",Snackbar.LENGTH_LONG).show();
            }
        }catch (SQLException e){
            Log.i("Data base error",""+e);
        }
        catch (Exception e){
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

    public void updateUser(){
        try{
            SQLiteDatabase db = managerDB.getWritableDatabase();
            if(db != null) {
                String selection = "user_document LIKE = ?";
                String[] selectionArgs ={this.user.getDocument().toString()};
                ContentValues values = putsOfUser();
                values.put("user_status","1");
                long cod = db.update("users",values,selection,selectionArgs);
                Snackbar.make(this.view,"The user " +this.user.getDocument()+ " has update "+cod,Snackbar.LENGTH_LONG).show();
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
        Iterator iterator = conditions.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next().toString();
            queryBuilder.append(" AND ");
            queryBuilder.append(key);
            queryBuilder.append(" = \'");
            queryBuilder.append(conditions.get(key));
            queryBuilder.append("\'");
        }
        String query = queryBuilder.toString();
        Snackbar.make(this.view,query,Snackbar.LENGTH_LONG).show();
        return getUsersExecuter(query);
    }
    public ArrayList<User> getUsersExecuter(String query){
        SQLiteDatabase db = managerDB.getReadableDatabase();
        ArrayList<User> resultList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.moveToFirst()){
            do{
                User userTemp = new User();
                userTemp.setDocument(cursor.getString(0));
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
