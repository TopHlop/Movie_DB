package com.example.themoviedb.main.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.themoviedb.R;
import com.example.themoviedb.databinding.FragmentFavoritesBinding;
import com.example.themoviedb.di.DI;
import com.example.themoviedb.main.ui.FilmsAdapter;
import com.example.themoviedb.main.ui.RecyclerViewForm;
import com.example.themoviedb.main.viewModel.FavoritesLoadViewModel;

import javax.inject.Inject;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoritesLoadViewModel favoritesLoadViewModel;

    private FilmsAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        DI.getAppComponent().inject(this);
        favoritesLoadViewModel = new ViewModelProvider(this, viewModelFactory).get(FavoritesLoadViewModel.class);
        loadFavoriteFilms();

        adapter = new FilmsAdapter();
        changeRecyclerViewForm(favoritesLoadViewModel.getRecyclerViewForm());

        favoritesLoadViewModel.getFavoriteFilmsResult().observe(getViewLifecycleOwner(), favoriteFilmsResult -> {
            if(favoriteFilmsResult.size() == 0) {
                notFoundFavorites(true);
            } else {
                adapter.setFilms(favoriteFilmsResult);
                notFoundFavorites(false);
            }
        });

        binding.recyclerViewSetting.setOnClickListener(view -> {
            switch (adapter.getRecyclerViewForm()) {
                case CARD_VIEW:
                    favoritesLoadViewModel.setRecyclerViewForm(RecyclerViewForm.LIST_VIEW);
                    changeRecyclerViewForm(RecyclerViewForm.LIST_VIEW);
                    break;
                case LIST_VIEW:
                    favoritesLoadViewModel.setRecyclerViewForm(RecyclerViewForm.CARD_VIEW);
                    changeRecyclerViewForm(RecyclerViewForm.CARD_VIEW);
                    break;
            }
        });
    }

    private void setRecyclerViewLayoutManagerAndIcon(RecyclerViewForm recyclerViewForm) {
        switch (recyclerViewForm) {
            case CARD_VIEW:
                binding.recyclerViewFavorites.setLayoutManager(new GridLayoutManager(getContext(), 2));
                binding.recyclerViewSetting.setImageResource(R.drawable.ic_list_24px);
                break;
            case LIST_VIEW:
            default:
                binding.recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewSetting.setImageResource(R.drawable.ic_card_24px);
                break;
        }
    }

    private void notFoundFavorites(boolean isNotFound) {
        binding.recyclerViewFavorites.setVisibility(isNotFound ? View.GONE : View.VISIBLE);
        binding.notFoundLayout.setVisibility(isNotFound? View.VISIBLE : View.GONE);
        binding.searchSettingLayout.setVisibility(isNotFound ? View.INVISIBLE : View.VISIBLE);
    }

    private void changeRecyclerViewForm(RecyclerViewForm recyclerViewForm) {
        adapter.changeRecyclerViewForm(recyclerViewForm);
        binding.recyclerViewFavorites.setAdapter(adapter);
        setRecyclerViewLayoutManagerAndIcon(recyclerViewForm);
    }

    private void loadFavoriteFilms() {
        favoritesLoadViewModel.loadFavoriteFilms();
    }
}
