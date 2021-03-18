package com.example.themoviedb.login.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.themoviedb.MovieDBError;
import com.example.themoviedb.R;
import com.example.themoviedb.databinding.FragmentLoginBinding;
import com.example.themoviedb.di.DI;
import com.example.themoviedb.login.viewModel.LoginViewModel;

import java.util.Objects;

import javax.inject.Inject;

public class LoginFragment extends Fragment {
    private final String TAG = "LoginActivity";

    private FragmentLoginBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private LoginViewModel loginViewModel;

    private OnNavigateToMainMenuListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnNavigateToMainMenuListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNavigateToMainMenuListener");
        }
    }

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
        if (loginViewModel.isUserLogin()) {
            listener.navigateToMainMenu();
        }

        customizeEditTextFields();

        binding.loginButton.setOnClickListener(v -> {
            loginUser();
        });

        loginViewModel.getIsSuccessLogin().observe(getViewLifecycleOwner(), isSuccessAuth -> {
            if (isSuccessAuth) {
                binding.errorText.setVisibility(View.GONE);
                listener.navigateToMainMenu();
            }
        });

        loginViewModel.getErrorCode().observe(getViewLifecycleOwner(), errorCode -> {
            binding.errorText.setText(MovieDBError.getErrorMessage(errorCode, getContext()));
            binding.errorText.setVisibility(View.VISIBLE);
            setEnabledFields(true);
        });
    }

    private void setEnabledFields(boolean isEnabled) {
        binding.loginEditText.setEnabled(isEnabled);
        binding.passwordEditText.setEnabled(isEnabled);
        binding.loginButton.setClickable(isEnabled);
    }

    private void loginUser() {
        loginViewModel.loginUser(Objects.requireNonNull(binding.loginEditText.getText()).toString(),
                Objects.requireNonNull(binding.passwordEditText.getText()).toString());
        binding.errorText.setVisibility(View.INVISIBLE);
        setEnabledFields(false);
    }

    private void customizeEditTextFields() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (Objects.requireNonNull(binding.passwordEditText.getText()).length() != 0 &&
                        Objects.requireNonNull(binding.loginEditText.getText()).length() != 0) {
                    setLoginButtonClickable(true);
                } else {
                    setLoginButtonClickable(false);
                }
            }
        };

        binding.passwordEditText.setOnEditorActionListener((view, actionId, keyEvent) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginUser();
                return true;
            }
            return false;
        });
        binding.passwordEditText.setImeActionLabel(getString(R.string.login_button),
                EditorInfo.IME_ACTION_DONE);
        binding.passwordEditText.addTextChangedListener(textWatcher);
        binding.loginEditText.addTextChangedListener(textWatcher);
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    private void setLoginButtonClickable(boolean clickable) {
        binding.loginButton.setClickable(clickable);
        binding.loginButton.setBackgroundTintList(clickable ?
                ContextCompat.getColorStateList(Objects.requireNonNull(getContext()), R.color.button_orange_color) :
                ContextCompat.getColorStateList(Objects.requireNonNull(getContext()), R.color.unclicked_button_color));
        binding.loginButton.setTextColor(clickable ?
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.text_light_color) :
                ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.text_gray_color));
    }

    public interface OnNavigateToMainMenuListener {
        void navigateToMainMenu();
    }
}
