package com.example.citeconsult;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.ScrollCaptureTarget;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    ImageView ivPopUp;
    ProgressDialog progressDialog;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPopUp = findViewById(R.id.ivPopUp);
        progressDialog = new ProgressDialog(this);




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
                                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
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
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);



        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if (title.equals("Help")) {
            tvTitle.setText(title);
            tvMessage.setText(getString(R.string.sample));
            dialog.show();
        }else if (title.equals("About Us")) {
            tvTitle.setText(title);
            tvMessage.setText("James Patrick B. Espiritu\nJonalyn Siute\nNicole Lobaton\nXylene Gisele Mendez\nGeomel Alvarez");
            dialog.show();
        }
    }

    public void set(View view) {
        startActivity(new Intent(MainActivity.this, InstructorsList.class));
    }
    public void view(View view){
        //TODO view appointments
        Intent intent = new Intent(MainActivity.this, AppointmentList.class);
        startActivity(intent);
    }

    public void instructorAdd(View view) {
        startActivity(new Intent(MainActivity.this, AddInstructorActivity.class));
    }
}