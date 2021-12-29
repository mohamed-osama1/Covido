package com.example.doctor_client_app.DoctorTabs;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor_client_app.PatientPage;
import com.example.doctor_client_app.PatientSigning;
import com.example.doctor_client_app.R;
import com.example.doctor_client_app.Request;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;


public class RequestsAdapter extends
        RecyclerView.Adapter<RequestsAdapter.ViewHolder> {
    static Context c;
    String mytext;
    String patientId;
    String requestID;
    // Store a member variable for the contacts
    private List<Request> mRequests;


    // Pass in the contact array into the constructor
    public RequestsAdapter(List<Request> Requests) {
        ;mRequests = Requests;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        RequestsAdapter.c=context;
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_request_doctor, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the data model based on position
        Request contact = mRequests.get(position);

        // Set item views based on your views and data model
        TextView textViewName = holder.nameTextView;
        textViewName.setText("Name : "+contact.getName());
        TextView textViewDesc = holder.descTextView;
        textViewDesc.setText("Description : "+contact.description);
        LinearLayout l=holder.l;

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertbuilder = new AlertDialog.Builder(v.getContext());
                final EditText reply = new EditText(v.getContext());
                reply.setInputType(InputType.TYPE_CLASS_TEXT);
                alertbuilder.setView(reply);
                alertbuilder.setTitle("Medical Reply")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                mytext = reply.getText().toString();

                                patientId = contact.uid;
                                requestID=contact.requestId;
                                contact.docReply=mytext;
                                contact.isReadByDoctor=true;
                                System.out.println("Mesamehxx  "+contact.uid+"   "+contact.requestId+"   "+contact.docReply);

                                DatabaseReference requests = FirebaseDatabase.getInstance().getReference("Requests");
                                requests.child(patientId).child(requestID).setValue(contact).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RequestsAdapter.c, "successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(RequestsAdapter.c, "failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(v.getContext(),"Selected Option: No",Toast.LENGTH_SHORT).show();
                            }
                        });

                //Creating dialog box
                AlertDialog dialog  = alertbuilder.create();
                dialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return mRequests.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public TextView descTextView;
        public LinearLayout l;

       // public Button messageButton;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {

            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            l=(LinearLayout) itemView.findViewById(R.id.request_layout);
            nameTextView = (TextView) itemView.findViewById(R.id.contact_name_doctor);
            descTextView = (TextView) itemView.findViewById(R.id.description_doctor);

         //   messageButton = (Button) itemView.findViewById(R.id.message_button_doc);
        }
    }
}