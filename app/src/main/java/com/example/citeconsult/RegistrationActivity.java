package com.example.citeconsult;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    EditText etName, etPassword, etConfirm, etEmail, etId;
    ProgressDialog progressDialog;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        etName = findViewById(R.id.etName);
        etPassword = findViewById(R.id.etPassword);
        etConfirm = findViewById(R.id.etConfirm);
        etEmail = findViewById(R.id.etEmail);
        etId = findViewById(R.id.etId);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

    }

    public void register(View view) {
        String name = etName.getText().toString();
        String password = etPassword.getText().toString();
        String confirm = etConfirm.getText().toString();
        String email = etEmail.getText().toString();
        String id = etId.getText().toString();

        if (name.isEmpty()) {
            etName.setError("Enter your name");
            etName.requestFocus();
        } else if (password.isEmpty()) {
            etPassword.setError("Enter your password");
            etPassword.requestFocus();
        } else if (password.length() < 6) {
            etPassword.setError("Password must be 6 characters");
            etPassword.requestFocus();
        } else if (!password.equalsIgnoreCase(confirm)) {
            etConfirm.setError("Passwords do not match!");
            etConfirm.requestFocus();
        } else if (email.isEmpty()) {
            etEmail.setError("Enter your email");
            etEmail.requestFocus();
        } else if (id.isEmpty() || id.length() != 8) {
            etId.setError("Wrong ID number format!");
            etId.requestFocus();
        } else {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading please wait...");
            progressDialog.setTitle("Registration");
            progressDialog.show();
            //add to database
            registerUser(name, password, email, id);
        }
    }

    private void registerUser(String name, String password, String email, String id) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //add user

                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    String uid = firebaseUser.getUid();

                    databaseReference = FirebaseDatabase.getInstance().getReference("users").child(uid);

                    Users user = new Users(uid, name, id);

                    databaseReference.setValue(user);

                    startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                    finish();
                    progressDialog.dismiss();

                    Toast.makeText(getApplicationContext(), "Registered Successfully!", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}