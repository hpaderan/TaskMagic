/*
 * Copyright (c)  2018 Team 24 CMPUT301 University of Alberta - All Rights Reserved.
 * You may use distribute or modify this code under terms and conditions of COde of Student Behavious at University of Alberta.
 * You can find a copy of the license ini this project. Otherwise, please contact harrold@ualberta.ca
 *
 */

package com.example.taskmagic;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Yipu on 21/02/2018.
 */

public class User implements Serializable {
    private String id;
    private String token;
    private String fullName;
    private String emailAddress;
    private String userName;
    private String password;
    private String phoneNumber;
    private String photoUri;


    public User(){
    }
    public User(String fullName, String emailAddress, String userName, String phoneNumber){
        this.fullName=fullName;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.phoneNumber = phoneNumber;

    }

    public User(String fullName, String emailAddress, String userName, String password, String phoneNumber, String photo) {
        this.fullName=fullName;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.photoUri = photo;

    }

    public User(String fullName, String emailAddress, String userName, String password, String phoneNumber) {
        this.fullName=fullName;
        this.emailAddress = emailAddress;
        this.userName = userName;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public String getFullName(){
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getphotoUri() {
        return photoUri;
    }

    public void setFullName(String fullName) {
       this.fullName=fullName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setPhotoUri(String uri) {
        this.photoUri = uri;
    }
    public void setId(String id){
        this.id=id;
    }
    public String getId(){
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
