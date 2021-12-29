package com.example.doctor_client_app;

import java.util.ArrayList;

public class User {
    private static int lastRequestId = 0;
    public String name , email , password ;
    public boolean isDoc;


    public User(String name , String email , String password,boolean isDoc){
        this.name= name;
        this.email= email;
        this.password= password;
this.isDoc=isDoc;

    }



}
