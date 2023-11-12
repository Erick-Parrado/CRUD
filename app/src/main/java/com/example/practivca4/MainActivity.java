package com.example.practivca4;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ListAdapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteTransactionListener;
import android.graphics.PathEffect;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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

    private User currentUser;

    SQLiteDatabase SQLGestor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        beginning();
        btRegister.setOnClickListener(this::createUser);
        btList.setOnClickListener(this::listUserShow);
        btClean.setOnClickListener(this::clean);
        btSearch.setOnClickListener(this::search);
    }

    private void createUser(View view) {
        catchUser();
        UserDao userDao = new UserDao(this.context,view,this.currentUser);
        userDao.insertUser();
        //Call invocate lister
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
    }

    private void listUserShow(View view){
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


    public void search(View view){
        catchUser();
        UserDao userDao = new UserDao(context,lvList);
        ArrayList<User> userList = userDao.getUsers(currentUser);
        ArrayAdapter<User> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,userList);
        lvList.setAdapter(adapter);
    }

    private void clean(View view){
        etDocument.setText("");
        etUserName.setText("");
        etName.setText("");
        etLastName.setText("");
        etPassword.setText("");
    }
}