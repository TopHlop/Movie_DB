package com.example.themoviedb.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.themoviedb.R;
import com.example.themoviedb.databinding.ActivityLoginBinding;
import com.example.themoviedb.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}