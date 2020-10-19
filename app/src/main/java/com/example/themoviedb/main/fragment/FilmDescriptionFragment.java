package com.example.themoviedb.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.themoviedb.databinding.FragmentFavoritesBinding;
import com.example.themoviedb.databinding.FragmentFilmDescriptionBinding;

public class FilmDescriptionFragment extends Fragment {

    private FragmentFilmDescriptionBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFilmDescriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
