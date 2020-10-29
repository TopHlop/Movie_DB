package com.example.themoviedb.main.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.themoviedb.App;
import com.example.themoviedb.databinding.FragmentUserBinding;
import com.example.themoviedb.login.ui.LoginActivity;
import com.example.themoviedb.main.viewModel.UserViewModel;

import java.util.Objects;

import javax.inject.Inject;


public class UserFragment extends Fragment {

    private FragmentUserBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        App.getAppComponent().inject(this);
        userViewModel = new ViewModelProvider(this, viewModelFactory).get(UserViewModel.class);
        userViewModel.getIsSuccessDeleteSession().observe(getViewLifecycleOwner(), isSuccessDeleteSession -> {
            if(isSuccessDeleteSession) {
                startLoginActivity();
            }
        });
        binding.exitButton.setOnClickListener(view -> {
            userViewModel.deleteSession();
            setExitButtonClickable(false);
        });
    }

    private void setExitButtonClickable(boolean clickable) {
        binding.exitButton.setClickable(clickable);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        Objects.requireNonNull(getActivity()).finish();
    }
}
