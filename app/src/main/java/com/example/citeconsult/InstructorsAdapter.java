package com.example.citeconsult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.citeconsult.R;
import com.example.citeconsult.Instructors;

import java.util.ArrayList;
import java.util.Arrays;

public class InstructorsAdapter extends ArrayAdapter<Instructors> {
    private static ArrayList<Instructors> instructorsArrayList = new ArrayList<>();
    Context context;

    public InstructorsAdapter(@NonNull Context context, ArrayList<Instructors> instructorsArrayList) {
        super(context, R.layout.layout_instructors, instructorsArrayList);
        this.context = context;
        this.instructorsArrayList = instructorsArrayList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_instructors, null, true);

        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvEmail = view.findViewById(R.id.tvEmail);
        ImageView ivPhoto = view.findViewById(R.id.ivPhoto);

        tvName.setText(instructorsArrayList.get(position).getName().toUpperCase());
        tvEmail.setText(instructorsArrayList.get(position).getEmail());
        Glide.with(context).load(instructorsArrayList.get(position).getPhoto()).into(ivPhoto);
        return view;
    }
}
