package com.example.doctor_client_app;

import java.util.ArrayList;

public class Request {
    private String mName;
    private boolean mOnline;

    public Request(String name, boolean online) {
        mName = name;
        mOnline = online;
    }

    public String getName() {
        return mName;
    }

    public boolean isOnline() {
        return mOnline;
    }

    private static int lastRequestId = 0;

    public static ArrayList<Request> createRequestsList(int numRequests) {
        ArrayList<Request> Requests = new ArrayList<Request>();

        for (int i = 1; i <= numRequests; i++) {
            Requests.add(new Request("Person " + ++lastRequestId, i <= numRequests / 2));
        }

        return Requests;
    }
}