package com.example.doctor_client_app.PatientTabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor_client_app.R;
import com.example.doctor_client_app.Request;

import java.util.List;

public class RequestsAdapter extends
        RecyclerView.Adapter<RequestsAdapter.ViewHolder> {
    // Store a member variable for the contacts
    private List<Request> mRequests;

    // Pass in the contact array into the constructor
    public RequestsAdapter(List<Request> Requests) {
        mRequests = Requests;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_request_patient, parent, false);

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
        textViewName.setText("Patient Name: "+contact.getName());
        TextView textViewDesc = holder.descTextView;
        textViewDesc.setText("Description : "+contact.description);
        holder.docReplyView.setText("Doc Reply "+contact.docReply);
        Button button = holder.messageButton;
        button.setText(contact.isReadByDoctor ? "Seen" : "Unseen");


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
        public Button messageButton;
        public TextView descTextView;
        public TextView docReplyView;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.contact_name_patient);
            docReplyView = (TextView) itemView.findViewById(R.id.reply_patient);
            descTextView = (TextView) itemView.findViewById(R.id.description_patient);

            messageButton = (Button) itemView.findViewById(R.id.message_button_pat);
        }
    }
}