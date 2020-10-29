package com.example.themoviedb.main.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.themoviedb.R;
import com.example.themoviedb.databinding.ActivityMainBinding;
import com.example.themoviedb.main.ui.fragment.FavoritesFragment;
import com.example.themoviedb.main.ui.fragment.FilmsFragment;
import com.example.themoviedb.main.ui.fragment.UserFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    //@Inject
    MainPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initViewPager();
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void initViewPager() {
        pagerAdapter = new MainPagerAdapter(this);
        pagerAdapter.addFragment(new FilmsFragment(), getString(R.string.title_films),
                getDrawable(R.drawable.ic_film));
        pagerAdapter.addFragment(new FavoritesFragment(), getString(R.string.title_favorites),
                getDrawable(R.drawable.ic_favorite));
        pagerAdapter.addFragment(new UserFragment(), getString(R.string.title_user),
                getDrawable(R.drawable.ic_user));
        binding.viewPager.setAdapter(pagerAdapter);
        new TabLayoutMediator(binding.tabs, binding.viewPager,
                (tab, position) -> {
                    tab.setText(pagerAdapter.getFragmentTitle(position));
                    Drawable icon = pagerAdapter.getFragmentIcon(position);
                    tab.setIcon(icon);
                }).attach();
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).
                        setTint(getResources().getColor(R.color.button_orange_color));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Objects.requireNonNull(tab.getIcon()).
                        setTint(getResources().getColor(R.color.text_light_color));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}