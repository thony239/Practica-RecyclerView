package com.example.app5eartquake;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.Instrumentation;
import android.os.Bundle;

import com.example.app5eartquake.databinding.ActivityMonitorBinding;


public class Monitor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMonitorBinding binding= ActivityMonitorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Bundle extras = getIntent().getExtras();
        String id= extras.getString("id");

        binding.txtNumLatitud.setText(extras.getDouble("latitude")+"");
        binding.txtFecha.setText(extras.getLong("time")+"");
        binding.txtNumLongitud.setText(extras.getDouble("longitude")+"");
        binding.txtMetros.setText(extras.getDouble("magnitude")+"");
        binding.txtUbi.setText(extras.getString("place"));


    }
}