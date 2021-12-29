package com.example.doctor_client_app.PatientTabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctor_client_app.R;
import com.example.doctor_client_app.Request;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Stats extends Fragment {

    ArrayList<Request> requests=new ArrayList<Request>();
    String patientId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_stats, container, false);
        // Lookup the recyclerview in activity layout


        RecyclerView rvContacts = (RecyclerView) v.findViewById(R.id.rv);
        FirebaseDatabase.getInstance().getReference("Requests")
                .child(patientId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Request> requestsTmp=new ArrayList<Request>();
                for(DataSnapshot requestsSnapshot: snapshot.getChildren()){
                    String description = requestsSnapshot.child("description").getValue().toString();
                    String name = requestsSnapshot.child("name").getValue().toString();
                    String docReply = requestsSnapshot.child("docReply").getValue().toString();
                    boolean isReadByDoc = (boolean )requestsSnapshot.child("isReadByDoctor").getValue();

                    Request request = new Request(name,description);
                    request.isReadByDoctor=isReadByDoc;
                    request.docReply=docReply;
                    requestsTmp.add(request);


                }
                requests=requestsTmp;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Create adapter passing in the sample user data
        System.out.println("Size before Adapter : "+ requests.size());
//        System.out.println("Size before Adapter : "+ requests.get(0).getName());
        if(requests.size()!=0){
                    System.out.println("Size before Adapter : "+ requests.get(0).getName());
            RequestsAdapter adapter = new RequestsAdapter(requests);
            // Attach the adapter to the recyclerview to populate items
            rvContacts.setAdapter(adapter);
            // Set layout manager to position the items
            rvContacts.setLayoutManager(new LinearLayoutManager(v.getContext()));

            return v;
        }
        rvContacts.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;

    }
}