package com.example.themoviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;

import com.example.themoviedb.R;
import com.example.themoviedb.databinding.ActivityMainBinding;
import com.example.themoviedb.login.ui.LoginFragment;
import com.example.themoviedb.main.ui.fragment.UserFragment;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnNavigateToMainMenuListener,
        UserFragment.OnNavigateToLoginFragmentListener {

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
    }

    public void setBottomNavigationVisible(boolean visible) {
        binding.bottomNavigation.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void navigateToMainMenu() {
        navController.navigate(R.id.films_fragment);
        setBottomNavigationVisible(true);
    }

    @Override
    public void navigateToLoginFragment() {
        navController.navigate(R.id.login_fragment);
        setBottomNavigationVisible(false);
    }
}