package com.example.practivca4.entities;

import java.util.HashMap;

public class User {
    private String document;
    private String userName;
    private String name;
    private String lastName;
    private String password;

    public User() {
    }
    public User(String document, String userName, String name, String lastName, String password) {
        this.document = document;
        this.userName = userName;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
    }

    public String getDocument() {
        return document.toString();
    }

    public String getUserName() {
        return userName;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "document=" + document +
                ", userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public HashMap<String,String> toArray(){
        HashMap<String,String> attributes = new HashMap<String,String>();

        if(!this.document.toString().isEmpty()){
            attributes.put("user_document",this.document.toString());
        }
        if(!this.userName.isEmpty()){
            attributes.put("user_userName",this.userName);
        }
        if(!this.name.isEmpty()){
            attributes.put("user_name",this.name);
        }
        if(!this.lastName.isEmpty()){
            attributes.put("user_lastName",this.lastName);
        }
        if(!this.password.isEmpty()){
            attributes.put("user_password",this.password);
        }
        return attributes;
    }
}
