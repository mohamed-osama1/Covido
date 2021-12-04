package com.example.doctor_client_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class forgotPassword extends AppCompatActivity implements View.OnClickListener {
    private EditText newPass ,newPassConfirm ;
    private Button save;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        newPass = findViewById(R.id.newPass);
        newPassConfirm= findViewById(R.id.newPassCheck);
        save = findViewById(R.id.saveNewPass);
        save.setOnClickListener(this::onClick);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.saveNewPass)
        {
            forgetPass();
        }
    }

    private void forgetPass(){
        String pass = newPass.getText().toString().trim();
        String passCheck = newPassConfirm.getText().toString().trim();


        if (pass.isEmpty()) {
            newPass.setError("Password required");
            newPass.requestFocus();
            return;
        }

        if (pass.length() < 6) {
            newPass.setError("password length should be greater than or equal 6");
            newPass.requestFocus();
        }

        if (passCheck.contentEquals(passCheck)!=true) {
            newPassConfirm.setError("Password does not match");
            newPassConfirm.requestFocus();
            return;
        }


String email = "testing@gmail.com";
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull @org.jetbrains.annotations.NotNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            User user = new User("TestName" ,email, pass);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull @NotNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(forgotPassword.this, "password has been changed successfully", Toast.LENGTH_LONG).show();

                                    } else {
                                        Toast.makeText(forgotPassword.this, "Failed!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(forgotPassword.this, "Failed !!!", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }
}