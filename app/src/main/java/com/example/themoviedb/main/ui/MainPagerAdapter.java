package com.example.themoviedb.main.ui;

import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainPagerAdapter extends FragmentStateAdapter {

    private final List<Fragment> fragmentList;
    private final List<String> fragmentTitleList;
    private final List<Drawable> fragmentIconList;

    public MainPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
        this.fragmentTitleList = new ArrayList<>();
        this.fragmentList = new ArrayList<>();
        this.fragmentIconList = new ArrayList<>();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String titleFragment, Drawable iconFragment){
        fragmentList.add(fragment);
        fragmentTitleList.add(titleFragment);
        fragmentIconList.add(iconFragment);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }

    public String getFragmentTitle(int position) {
        return fragmentTitleList.get(position);
    }

    public Drawable getFragmentIcon(int position) {
        return fragmentIconList.get(position);
    }
}
