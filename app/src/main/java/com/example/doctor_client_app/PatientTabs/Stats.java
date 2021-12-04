package com.example.doctor_client_app.PatientTabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctor_client_app.R;
import com.example.doctor_client_app.Request;
import com.example.doctor_client_app.RequestsAdapter;

import java.util.ArrayList;


public class Stats extends Fragment {

    ArrayList<Request> requests;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        // Lookup the recyclerview in activity layout
        RecyclerView rvContacts = (RecyclerView) v.findViewById(R.id.rv);

        // Initialize contacts
        requests = Request.createRequestsList(20);
        // Create adapter passing in the sample user data
        RequestsAdapter adapter = new RequestsAdapter(requests);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;

    }
}