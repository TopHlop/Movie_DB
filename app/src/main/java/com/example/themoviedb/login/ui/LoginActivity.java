package com.example.themoviedb.login.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.example.themoviedb.App;
import com.example.themoviedb.R;
import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.databinding.ActivityLoginBinding;
import com.example.themoviedb.main.ui.MainActivity;
import com.example.themoviedb.login.viewModel.LoginViewModel;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {

    private final String TAG = "LoginActivity";

    private ActivityLoginBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;
    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        App.getAppComponent().inject(this);
        loginViewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);
        if(loginViewModel.isUserLogin()) {
            startMainActivity();
        }

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setTextWatcherForEditTextFields();

        binding.loginButton.setOnClickListener(v -> {
                loginViewModel.loginUser(binding.loginEditText.getText().toString(),
                        binding.passwordEditText.getText().toString());
                setEnabledFields(false);
        });

        loginViewModel.getIsSuccessLogin().observe(this, isSuccessAuth -> {
                if (isSuccessAuth) {
                    binding.errorText.setVisibility(View.GONE);
                    startMainActivity();
                }
        });

        loginViewModel.getErrorMessage().observe(this, errorMessage-> {
                setErrorMessage(errorMessage);
                setEnabledFields(true);
        });
    }

    private void setEnabledFields(boolean isEnabled) {
        binding.loginEditText.setEnabled(isEnabled);
        binding.passwordEditText.setEnabled(isEnabled);
        binding.loginButton.setClickable(isEnabled);
    }

    private void setErrorMessage(String errorMessage) {
        binding.errorText.setText(errorMessage);
        binding.errorText.setVisibility(View.VISIBLE);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void setTextWatcherForEditTextFields() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!binding.passwordEditText.getText().toString().equals("") &&
                        !binding.loginEditText.getText().toString().equals("")) {
                    setLoginButtonClickable(true);
                } else {
                    setLoginButtonClickable(false);
                }
            }
        };
        binding.passwordEditText.addTextChangedListener(textWatcher);
        binding.loginEditText.addTextChangedListener(textWatcher);
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private void setLoginButtonClickable(boolean clickable) {
        binding.loginButton.setClickable(clickable);
        binding.loginButton.setBackgroundTintList(clickable ?
                getResources().getColorStateList(R.color.button_orange_color) :
                getResources().getColorStateList(R.color.unclicked_button_color));
        binding.loginButton.setTextColor(clickable ?
                getResources().getColor(R.color.text_light_color) :
                getResources().getColor(R.color.text_gray_color));
    }


}