package com.example.doctor_client_app;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class Request {
   public String uid;
    public String requestId;
    public String docReply;
    private String mName;
    public String description;
    public boolean isReadByDoctor;


    public Request(String name,String description) {
        mName = name;
        docReply="no reply";
        this.description=description;
        this.isReadByDoctor=isReadByDoctor;
    }

    public String getName() {
        return mName;
    }






}