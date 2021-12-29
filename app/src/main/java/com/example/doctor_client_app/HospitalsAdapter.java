package com.example.doctor_client_app;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.doctor_client_app.PatientTabs.RequestsAdapter;

import java.util.List;


public class HospitalsAdapter extends RecyclerView.Adapter<HospitalsAdapter.ViewHolder> {
     public List<Hospital> mHospitals;

    public HospitalsAdapter(List<Hospital> Hospitals) {
        mHospitals = Hospitals;
    }
    @NonNull
    @Override
    public HospitalsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_hospital, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // Get the data model based on position
        Hospital hospital = mHospitals.get(position);

        // Set item views based on your views and data model
        TextView textViewName = holder.nameTextView;
        textViewName.setText(" " + hospital.getName() + " ");
        Button button = holder.viewInMapButton;
        button.setText("View Location");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.google.com?q="+hospital.getLatitude()+","+hospital.getLongitude()));
                view.getContext().startActivity(browserIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mHospitals.size();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView nameTextView;
        public Button viewInMapButton;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.hospital_name);
            viewInMapButton = (Button) itemView.findViewById(R.id.viewInMapBtn);
        }
    }
}
