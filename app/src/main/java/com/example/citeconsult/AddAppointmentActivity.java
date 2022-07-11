package com.example.citeconsult;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.citeconsult.Appointment;
import com.example.citeconsult.Instructors;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class AddAppointmentActivity extends AppCompatActivity {

    private static ArrayList<String> daysArrayList = new ArrayList<>();
    private static ArrayList<String> timesArrayList = new ArrayList<>();

    AutoCompleteTextView spinnerDays;
    AutoCompleteTextView spinnerTimes;
    ImageView ivPhoto;
    TextView tvName;
    String key, uid;
    DatabaseReference databaseReference, dbAppointment;
    EditText etPurpose;

    String[] dayArray = {
            "Monday",
            "Tuesday",
            "Wednesday",
            "Thursday",
            "Friday",
            "Saturday"

    };
    String[] timeArray = {
            "8:00am - 8:30am",
            "8:30am - 9:00am",
            "9:00am - 9:30am",
            "9:30am - 10:00am",
            "10:00am - 10:30am",
            "10:30am - 11:00am",
            "11:00am - 11:30am",
            "11:30am - 12:00pm",
            //afternoon
            "1:00pm - 1:30pm",
            "1:30pm - 2:00pm",
            "2:00pm - 2:30pm",
            "2:30pm - 3:00pm",
            "3:00pm - 3:30pm",
            "3:30pm - 4:00pm",
            "4:00pm - 4:30pm",
            "4:30pm - 5:00pm"

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        spinnerDays = findViewById(R.id.tvDays);
        spinnerTimes = findViewById(R.id.tvTimes);
        etPurpose = findViewById(R.id.etPurpose);

        tvName = findViewById(R.id.tvName);
        ivPhoto = findViewById(R.id.ivPhoto);
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        uid = intent.getStringExtra("uid");

        databaseReference = FirebaseDatabase.getInstance().getReference("instructors").child(key);
        dbAppointment = FirebaseDatabase.getInstance().getReference("appointments");

        getInstructorData();
    }

    private void getInstructorData() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Instructors instructors = snapshot.getValue(Instructors.class);
                tvName.setText(instructors.getName().toUpperCase());
                Glide.with(getApplicationContext()).load(instructors.getPhoto()).into(ivPhoto);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //get time
        databaseReference.child("days").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                daysArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Integer days = dataSnapshot.getValue(Integer.class);

                    String dayString = dayArray[days];
                    daysArrayList.add(dayString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.child("time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                timesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Integer times = dataSnapshot.getValue(Integer.class);
                    String timeString = timeArray[times];
                    timesArrayList.add(timeString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        setSpinnerItems();
    }

    private void setSpinnerItems() {

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.dropdown_item, daysArrayList);
        spinnerDays.setAdapter(adapter1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.dropdown_item, timesArrayList);
        spinnerTimes.setAdapter(adapter2);
    }


    public void submit(View view) {
        if (spinnerTimes.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select time...", Toast.LENGTH_SHORT).show();
        } else if (spinnerDays.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please select day", Toast.LENGTH_SHORT).show();
        } else if (etPurpose.getText().toString().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please enter your purpose..", Toast.LENGTH_SHORT).show();
        } else {
            String dbKey = dbAppointment.push().getKey();

            Appointment appointment = new Appointment(dbKey, spinnerTimes.getText().toString(),spinnerDays.getText().toString(), uid, key,"PENDING");

            dbAppointment.child(dbKey).setValue(appointment).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    Toast.makeText(getApplicationContext(), "Form submitted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void cancel(View view) {
        finish();
    }
}