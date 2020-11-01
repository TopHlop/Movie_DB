package com.example.themoviedb.login.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.themoviedb.App;
import com.example.themoviedb.R;
import com.example.themoviedb.databinding.FragmentLoginBinding;
import com.example.themoviedb.di.DI;
import com.example.themoviedb.login.viewModel.LoginViewModel;
import com.example.themoviedb.main.ui.MainActivity;

import java.util.Objects;

import javax.inject.Inject;

public class LoginFragment extends Fragment {
    private final String TAG = "LoginActivity";

    private FragmentLoginBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LoginViewModel loginViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DI.getAppComponent().inject(this);
        loginViewModel = new ViewModelProvider(this, viewModelFactory).get(LoginViewModel.class);
        if(loginViewModel.isUserLogin()) {
            navigateToFilmsFragment();
        }

        setTextWatcherForEditTextFields();

        binding.loginButton.setOnClickListener(v -> {
            loginViewModel.loginUser(binding.loginEditText.getText().toString(),
                    binding.passwordEditText.getText().toString());
            setEnabledFields(false);
        });

        loginViewModel.getIsSuccessLogin().observe(getViewLifecycleOwner(), isSuccessAuth -> {
            if (isSuccessAuth) {
                binding.errorText.setVisibility(View.GONE);
                navigateToFilmsFragment();
            }
        });

        loginViewModel.getErrorMessage().observe(getViewLifecycleOwner(), errorMessage-> {
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

    private void navigateToFilmsFragment() {
        Navigation.findNavController(binding.welcomeText).navigate(R.id.films_fragment);
        ((MainActivity) Objects.requireNonNull(getActivity())).setBottomNavigationVisible(true);
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
