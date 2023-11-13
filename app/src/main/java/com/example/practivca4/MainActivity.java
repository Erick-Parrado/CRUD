package com.example.practivca4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.graphics.PathEffect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.practivca4.entities.User;
import com.example.practivca4.model.ManagerDB;
import com.example.practivca4.model.UserDao;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private Context context;
    private EditText etDocument;
    private EditText etUserName;
    private EditText etName;
    private EditText etLastName;
    private EditText etPassword;
    private ListView lvList;

    private Button btRegister;
    private Button btSearch;
    private Button btList;
    private Button btClean;
    private Button btUpdate;
    private Button btDelete;

    private User currentUser;

    SQLiteDatabase SQLGestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        beginning();
        btRegister.setOnClickListener(this::createUser);
        btList.setOnClickListener(this::listUsers);
        btClean.setOnClickListener(this::cleanFields);
        btSearch.setOnClickListener(this::searchUser);
        btUpdate.setOnClickListener(this::updateUser);
        btDelete.setOnClickListener(this::deleteUser);
    }

    private void createUser(View view) {
        catchUser();
        UserDao userDao = new UserDao(this.context,view,this.currentUser);
        userDao.insertUser();
    }

    private void beginning(){
        etDocument = findViewById(R.id.etDocument);
        etUserName = findViewById(R.id.etUserName);
        etName = findViewById(R.id.etName);
        etLastName = findViewById(R.id.etLastName);
        etPassword = findViewById(R.id.etPassword);
        lvList = findViewById(R.id.lvList);
        btRegister = findViewById(R.id.btRegister);
        btSearch = findViewById(R.id.btSearch);
        btClean = findViewById(R.id.btClean);
        btList = findViewById(R.id.btList);
        btDelete = findViewById(R.id.btDelete);
        btUpdate = findViewById(R.id.btUpdate);
    }

    private void listUsers(View view){
        UserDao userDao = new UserDao(context,lvList);
        ArrayList<User> userList = userDao.getUsers();
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userList);
        lvList.setAdapter(adapter);
    }

    private void catchUser(){
        String document;
        String userName;
        String name;
        String lastName;
        String password;

        document = etDocument.getText().toString();
        userName = etUserName.getText().toString();
        name = etName.getText().toString();
        lastName = etLastName.getText().toString();
        password = etPassword.getText().toString();

        this.currentUser = new User(document,userName,name,lastName,password);
    }


    public void searchUser(View view){
        catchUser();
        UserDao userDao = new UserDao(context,lvList);
        ArrayList<User> userList = userDao.getUsers(currentUser);
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userList);
        lvList.setAdapter(adapter);
    }

    private void cleanFields(View view){
        etDocument.setText("");
        etUserName.setText("");
        etName.setText("");
        etLastName.setText("");
        etPassword.setText("");
    }

    private void updateUser(View view){
        catchUser();
        Toast.makeText(this, "lsdkf", Toast.LENGTH_SHORT).show();
        UserDao userDao = new UserDao(context,view,this.currentUser);
        userDao.updateUser();
    }

    private void deleteUser(View view){
        catchUser();
        UserDao userDao = new UserDao(context,view,this.currentUser);
        userDao.deleteUser();
    }
}