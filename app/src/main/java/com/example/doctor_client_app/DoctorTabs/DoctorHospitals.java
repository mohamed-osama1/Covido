package com.example.doctor_client_app.DoctorTabs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.doctor_client_app.Hospital;
import com.example.doctor_client_app.HospitalsAdapter;
import com.example.doctor_client_app.PatientTabs.RequestsAdapter;
import com.example.doctor_client_app.R;
import com.example.doctor_client_app.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.UUID;


public class DoctorHospitals extends Fragment {
    static HospitalsAdapter adapter;
    private FloatingActionButton addHosBtn;
    String HosName;
    String lng;
    String lat;
    ArrayList<Hospital> hospitals=new ArrayList<Hospital>();
    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor_hospitals, container, false);

        RecyclerView docHos = (RecyclerView) v.findViewById(R.id.docHos);



        hospitals=new ArrayList<Hospital>();
        // Inflate the layout for this fragment

        addHosBtn = v.findViewById(R.id.addHosBtn);
        addHosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Add Hospital");
                final View HospitalFormView = getLayoutInflater().inflate(R.layout.hospital_alertdialog, null);
                // Set above view in alert dialog.
                builder.setView(HospitalFormView);

                EditText hospitalName = (EditText)HospitalFormView.findViewById(R.id.hospitalName);
                EditText longitude = (EditText)HospitalFormView.findViewById(R.id.longitude);
                EditText latitude = (EditText)HospitalFormView.findViewById(R.id.latitude);


                Button addBtn = (Button)HospitalFormView.findViewById(R.id.addBtn);
                addBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {



                            HosName = hospitalName.getText().toString();
                            lng = longitude.getText().toString();
                            lat = latitude.getText().toString();

                            Hospital hospital = new Hospital(HosName,lng,lat);
                            String hosID= UUID.randomUUID().toString();

                            if(HosName.isEmpty()||lng.isEmpty()||lat.isEmpty()){
                                Toast.makeText(getContext(), "Enter a valid location", Toast.LENGTH_SHORT).show();
                            }
                            else {

                                DatabaseReference hospitals = FirebaseDatabase.getInstance().getReference("Hospitals");
                                hospitals.child(hosID).setValue(hospital).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getContext(), "successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }

                    }
                });

                //Creating dialog box
                builder.setCancelable(true);
                AlertDialog dialog  = builder.create();
                dialog.show();

            }
        });

        FirebaseDatabase.getInstance().getReference("Hospitals").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                ArrayList<Hospital>hospitalsTmp=new ArrayList<Hospital>();

                    for (DataSnapshot hospitalsSnapshot : snapshot.getChildren()) {
                        String name = hospitalsSnapshot.child("name").getValue().toString();
                        String lng = hospitalsSnapshot.child("longitude").getValue().toString();
                        String lat = hospitalsSnapshot.child("latitude").getValue().toString();

                        hospitalsTmp.add(new Hospital(name,lng,lat));

                        System.out.println("Mesamehx : " + hospitalsTmp.size());
                    }
                adapter = new HospitalsAdapter(hospitals);
                docHos.setAdapter(adapter);
                // Set layout manager to position the items
                docHos.setLayoutManager(new LinearLayoutManager(v.getContext()));

                hospitals=hospitalsTmp;
                    adapter.mHospitals=hospitals;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        /*FirebaseDatabase.getInstance().getReference("Hospitals")
                .child(userId).addValueEventListener(new ValueEventListener() {
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
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        HospitalsAdapter adapter = new HospitalsAdapter(hospitals);

        if(hospitals.size()!=0){
            System.out.println("Size before Adapter : "+ hospitals.get(0).getName());
            // Attach the adapter to the recyclerview to populate items

            docHos.setAdapter(adapter);
            // Set layout manager to position the items
            docHos.setLayoutManager(new LinearLayoutManager(v.getContext()));
adapter.notifyDataSetChanged();
System.out.println("Test adapter " +adapter.mHospitals.size());
            return v;
        }
        adapter.notifyDataSetChanged();
        docHos.setLayoutManager(new LinearLayoutManager(v.getContext()));
        System.out.println("Test adapter 1 " +adapter.mHospitals.size());
        return v;

    }
}