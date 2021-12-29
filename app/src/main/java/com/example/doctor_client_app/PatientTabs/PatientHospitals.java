package com.example.doctor_client_app.PatientTabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.doctor_client_app.Hospital;
import com.example.doctor_client_app.HospitalsAdapter;
import com.example.doctor_client_app.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class PatientHospitals extends Fragment {

    ArrayList<Hospital> hospitals=new ArrayList<Hospital>();
    HospitalsAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_hospitals, container, false);
        RecyclerView paHos = (RecyclerView) v.findViewById(R.id.paHos);

        FirebaseDatabase.getInstance().getReference("Hospitals")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Hospital> hospitalsTmp=new ArrayList<Hospital>();
                for(DataSnapshot hospitalsSnapshot: snapshot.getChildren()){
                    String name = hospitalsSnapshot.child("name").getValue().toString();
                    String lng = hospitalsSnapshot.child("longitude").getValue().toString();
                    String lat = hospitalsSnapshot.child("latitude").getValue().toString();


                    Hospital hospital = new Hospital(name,lng,lat);
                    hospitalsTmp.add(hospital);

                }
                hospitals=hospitalsTmp;
                adapter = new HospitalsAdapter(hospitals);
                paHos.setAdapter(adapter);
                // Set layout manager to position the items
                paHos.setLayoutManager(new LinearLayoutManager(v.getContext()));

                hospitals=hospitalsTmp;
                adapter.mHospitals=hospitals;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if(hospitals.size()!=0){
            System.out.println("Size before Adapter : "+ hospitals.get(0).getName());
            HospitalsAdapter adapter = new HospitalsAdapter(hospitals);
            // Attach the adapter to the recyclerview to populate items
            paHos.setAdapter(adapter);
            // Set layout manager to position the items
            paHos.setLayoutManager(new LinearLayoutManager(v.getContext()));
            return v;
        }
        paHos.setLayoutManager(new LinearLayoutManager(v.getContext()));
        return v;

    }
}