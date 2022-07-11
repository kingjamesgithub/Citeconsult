package com.example.citeconsult;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.citeconsult.R;
import com.example.citeconsult.Appointment;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class AppointmentsAdapter extends ArrayAdapter<Appointment> {
    Context context;
    private static ArrayList<Appointment> appointmentArrayList = new ArrayList<>();
    public AppointmentsAdapter(@NonNull Context context, ArrayList<Appointment> appointmentArrayList) {
        super(context, R.layout.layout_appointment,appointmentArrayList);
        this.appointmentArrayList = appointmentArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_appointment,null,true);
        TextView tvDayTime = view.findViewById(R.id.tvDayTime);
        TextView tvStatus = view.findViewById(R.id.tvStatus);

        String day = appointmentArrayList.get(position).getDay();
        String time = appointmentArrayList.get(position).getTime();
        String status = appointmentArrayList.get(position).getStatus();

        tvDayTime.setText(day+"/"+time);
        tvStatus.setText(status);
        return view;
    }
}
