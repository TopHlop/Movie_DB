package com.example.themoviedb.main.ui.fragment;

import android.content.Context;
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
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.ui.FilmsAdapter;
import com.example.themoviedb.main.ui.RecyclerViewForm;
import com.example.themoviedb.main.viewModel.FavoritesLoadViewModel;

import java.util.List;

import javax.inject.Inject;

public class FavoritesFragment extends Fragment {

    private FragmentFavoritesBinding binding;

    private OnNavigateToFragmentListener listener;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FavoritesLoadViewModel favoritesLoadViewModel;

    private FilmsAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnNavigateToFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNavigateToFilmsFragmentListener");
        }
    }

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
        initRecyclerView();
        getFavoriteFilms();
        setListeners();

    }

    private void setListeners () {
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

        binding.foundFilmsLink.setOnClickListener(view -> {
            listener.navigateToFilmsFragment();
        });
    }

    private void initRecyclerView() {
        adapter = new FilmsAdapter();
        adapter.attachDelegate(new FilmsAdapter.FilmsDelegate() {
            @Override
            public void openDescription(int id) {
                listener.navigateToDescriptionFragmentFromFavorites(id);
            }
        });
        changeRecyclerViewForm(favoritesLoadViewModel.getRecyclerViewForm());
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
        binding.notFoundLayout.setVisibility(isNotFound ? View.VISIBLE : View.GONE);
        binding.searchSettingLayout.setVisibility(isNotFound ? View.INVISIBLE : View.VISIBLE);
    }

    private void changeRecyclerViewForm(RecyclerViewForm recyclerViewForm) {
        adapter.changeRecyclerViewForm(recyclerViewForm);
        binding.recyclerViewFavorites.setAdapter(adapter);
        setRecyclerViewLayoutManagerAndIcon(recyclerViewForm);
    }

    private void getFavoriteFilms() {
        List<FilmWrap> favorites = favoritesLoadViewModel.getFavoriteFilms();
        if(favorites != null) {
            adapter.setFilms(favorites);
            notFoundFavorites(false);
        } else {
            favoritesLoadViewModel.loadFavoriteFilms();
            favoritesLoadViewModel.getFavoriteFilmsResult().observe(getViewLifecycleOwner(), favoriteFilmsResult -> {
                if (favoriteFilmsResult.size() == 0) {
                    notFoundFavorites(true);
                } else {
                    adapter.setFilms(favoriteFilmsResult);
                    notFoundFavorites(false);
                }
            });
        }
    }

    public interface OnNavigateToFragmentListener {
        void navigateToFilmsFragment();
        void navigateToDescriptionFragmentFromFavorites(int id);
    }
}
