package com.example.themoviedb.login.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.themoviedb.databinding.ActivityPinCodeBinding;

public class PinCodeActivity extends AppCompatActivity {

    private ActivityPinCodeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPinCodeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}