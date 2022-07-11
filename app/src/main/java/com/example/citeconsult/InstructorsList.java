package com.example.citeconsult;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class InstructorsList extends AppCompatActivity {
    private static ArrayList<Instructors> instructorsArrayList = new ArrayList<>();
    ListView lvInstructors;
    InstructorsAdapter instructorsAdapter;
    ImageView ivPopUp;
    ProgressDialog progressDialog;
    String email;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    DatabaseReference dbUsers;
    TextView tvDetails;
    String uid;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructors_list);

        GlobalVariable globalVariables = (GlobalVariable) this.getApplication();
//        instructorsArrayList = globalVariables.getInstructors();

        lvInstructors = findViewById(R.id.lvInstructors);
        ivPopUp = findViewById(R.id.ivPopUp);
        tvDetails = findViewById(R.id.tvDetails);

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        uid = firebaseUser.getUid();

        getInformation(uid);


        lvInstructors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String key = instructorsArrayList.get(i).getKey();
                startActivity(new Intent(getApplicationContext(), AddAppointmentActivity.class).putExtra("key", key).putExtra("uid", uid));
            }
        });
    }

    private void getInformation(String uid) {

        progressDialog.setTitle("Fetching from database...");
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        dbUsers = FirebaseDatabase.getInstance().getReference("users").child(uid);
        dbUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users = snapshot.getValue(Users.class);
                tvDetails.setText("Hello,\n" + users.getName());
                getInstructors();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }


    private void getInstructors() {
        databaseReference = FirebaseDatabase.getInstance().getReference("instructors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                instructorsAdapter = new InstructorsAdapter(getApplicationContext(), instructorsArrayList);
                lvInstructors.setAdapter(instructorsAdapter);
                instructorsArrayList.clear();
                instructorsAdapter.notifyDataSetChanged();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Instructors instructors = dataSnapshot.getValue(Instructors.class);
                    instructorsArrayList.add(instructors);
                }
                instructorsAdapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void pop_up(View view) {
        PopupMenu popup = new PopupMenu(getApplicationContext(), ivPopUp);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.pop_up_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        progressDialog.setTitle("Logging out");
                        progressDialog.setMessage("Please wait...");
                        progressDialog.show();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                progressDialog.dismiss();
                                FirebaseAuth.getInstance().signOut();
                                Toast.makeText(getApplicationContext(), "Logged out successfully.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                finish();
                            }
                        }, 1500);
                        return true;
                    case R.id.help:
                        showDialog("Help");
                        return true;
                    case R.id.about:
                        showDialog("About Us");
                        return true;
                    default:
                        return true;
                }
            }
        });
        popup.show();//showing popup menu
    }

    private void showDialog(String title) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_menu);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        TextView tvMessage = dialog.findViewById(R.id.tvMessage);
        Button btnClose = dialog.findViewById(R.id.btnClose);
        dialog.setCancelable(false);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        tvTitle.setText(title);
        tvMessage.setText(getString(R.string.sample));

        dialog.show();
    }

}