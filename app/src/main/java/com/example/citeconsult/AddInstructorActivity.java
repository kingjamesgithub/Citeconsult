package com.example.citeconsult;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class AddInstructorActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Uri uri;
    Dialog timesDialog, daysDialog;
    boolean[] selectedDay, selectedTime;
    ArrayList<Integer> dayList = new ArrayList<>();
    ArrayList<Integer> timeList = new ArrayList<>();
    ImageView ivPhoto;
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
    ProgressDialog progressDialog;
    TextView tvDays, tvTimes;
    EditText etName, etEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_instructor);
        progressDialog = new ProgressDialog(this);
        ivPhoto = findViewById(R.id.ivPhoto);
        tvDays = findViewById(R.id.tvDays);
        tvTimes = findViewById(R.id.tvTimes);
        selectedDay = new boolean[dayArray.length];
        selectedTime = new boolean[timeArray.length];
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);

        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        databaseReference = FirebaseDatabase.getInstance().getReference("instructors");
    }

    public void days(View view) {
        AlertDialog.Builder alertDay = new AlertDialog.Builder(this);
        alertDay.setTitle("Select days...");
        alertDay.setCancelable(false);

        alertDay.setMultiChoiceItems(dayArray, selectedDay, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    dayList.add(i);
                    Collections.sort(dayList);
                } else {
                    for (int j = 0; j < dayList.size(); j++) {
                        if (dayList.get(j) == i) {
                            dayList.remove(j);
                        }
                    }
                }
            }
        });
        alertDay.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder stringBuilder = new StringBuilder();

                for (int j = 0; j < dayList.size(); j++) {
                    stringBuilder.append(dayArray[dayList.get(j)]);
                    if (j != dayList.size() - 1) {
                        stringBuilder.append("\n");
                    }
                }
                tvDays.setText(stringBuilder.toString());
            }
        });
        alertDay.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDay.show();

    }


    public void times(View view) {
        AlertDialog.Builder alertTime = new AlertDialog.Builder(this);
        alertTime.setTitle("Select time...");
        alertTime.setCancelable(false);

        alertTime.setMultiChoiceItems(timeArray, selectedTime, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                if (b) {
                    timeList.add(i);
                    Collections.sort(timeList);
                } else {
                    for (int j = 0; j < timeList.size(); j++) {
                        if (timeList.get(j) == i) {
                            timeList.remove(j);
                        }
                    }
                }
            }
        });
        alertTime.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                StringBuilder stringBuilder = new StringBuilder();

                for (int j = 0; j < timeList.size(); j++) {
                    stringBuilder.append(timeArray[timeList.get(j)]);
                    if (j != timeList.size() - 1) {
                        stringBuilder.append("\n");
                    }
                }
                tvTimes.setText(stringBuilder.toString());
            }
        });
        alertTime.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertTime.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData();

            Glide.with(this).load(uri).into(ivPhoto);

        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void add(View view) {
        if (uri != null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.setCancelable(false);
            progressDialog.show();

            String fileName = System.currentTimeMillis() + "." + getFileExtension(uri);

            //reduce image quality before saving start
            Bitmap bmp = null;
            try {
                bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 25, bytes);
            byte[] fileInBytes = bytes.toByteArray();
            //reduce image quality before saving end

            StorageReference fileReference = storageReference.child(fileName);

            fileReference.putBytes(fileInBytes).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri fileUri) {

                            String key = databaseReference.push().getKey();

                            HashMap<String, Object> hashMap = new HashMap<>();
                            hashMap.put("photo", fileUri.toString());
                            hashMap.put("name", etName.getText().toString());
                            hashMap.put("email", etEmail.getText().toString());
                            hashMap.put("days", dayList);
                            hashMap.put("time", timeList);
                            hashMap.put("key", key);

                            databaseReference.child(key).setValue(hashMap);
                            clearDays();
                            clearTime();
                            ivPhoto.setBackground(getDrawable(R.mipmap.cite));
                            progressDialog.dismiss();

                            Toast.makeText(getApplicationContext(), "Upload successful!", Toast.LENGTH_SHORT).show();
                            uri = null;
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0 * snapshot.getBytesTransferred() / snapshot.getTotalByteCount());
                    progressDialog.setMessage("Progress: " + (int) progress + "%");

                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    public void upload(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    private void clearDays() {
        for (int j = 0; j < selectedDay.length; j++) {
            selectedDay[j] = false;
            dayList.clear();
            tvDays.setText("Select days...");
            etEmail.setText("@ucu.edu.ph");
            etName.setText("");
        }
    }

    private void clearTime() {
        for (int j = 0; j < selectedTime.length; j++) {
            selectedTime[j] = false;
            timeList.clear();
            tvTimes.setText("Select times...");
        }
    }

}