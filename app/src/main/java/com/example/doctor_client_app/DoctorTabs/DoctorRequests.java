package com.example.doctor_client_app.DoctorTabs;

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

public class DoctorRequests extends Fragment {
    static RequestsAdapter adapter;
    ArrayList<Request> requests = new ArrayList<Request>();
    String patientId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_doctor_requests, container, false);
        // Lookup the recyclerview in activity layout


        RecyclerView rvContacts = (RecyclerView) v.findViewById(R.id.rvDoctor);
        FirebaseDatabase.getInstance().getReference("Requests").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requests=new ArrayList<Request>();
                for (DataSnapshot requestsSnapshot : snapshot.getChildren()) {
                    for (DataSnapshot rs : requestsSnapshot.getChildren()) {

                        String description = rs.child("description").getValue().toString();
                        String name = rs.child("name").getValue().toString();
                        boolean is=(boolean)rs.child("isReadByDoctor").getValue();

                        if(!is) {
                            Request request = new Request(name,description);
                            request.uid=rs.child("uid").getValue().toString();
                            request.requestId=rs.child("requestId").getValue().toString();
                            requests.add(request);
                        }
                        //adapter.notifyDataSetChanged();
                    }
                    //adapter.notifyDataSetChanged();
                }
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Create adapter passing in the sample user data
        adapter = new RequestsAdapter(requests);
        // Attach the adapter to the recyclerview to populate items
        rvContacts.setAdapter(adapter);
        // Set layout manager to position the items
        rvContacts.setLayoutManager(new LinearLayoutManager(v.getContext()));

        return v;

    }
}
