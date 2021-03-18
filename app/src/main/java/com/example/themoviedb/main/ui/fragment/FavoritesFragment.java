package com.example.themoviedb.main.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
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
        initSearchView();
    }

    private void setListeners () {
        binding.topAppBar.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.recycler_view_form_item:
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
                    return true;
                case R.id.search_item:
                    return true;
                default:
                    return false;
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
                binding.topAppBar.getMenu().findItem(R.id.recycler_view_form_item).setIcon(R.drawable.ic_list_24px);
                break;
            case LIST_VIEW:
            default:
                binding.recyclerViewFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.topAppBar.getMenu().findItem(R.id.recycler_view_form_item).setIcon(R.drawable.ic_card_24px);
                break;
        }
    }

    private void notFoundFavorites(boolean isNotFound) {
        binding.recyclerViewFavorites.setVisibility(isNotFound ? View.GONE : View.VISIBLE);
        binding.notFoundLayout.setVisibility(isNotFound ? View.VISIBLE : View.GONE);
        binding.topAppBar.setVisibility(isNotFound ? View.INVISIBLE : View.VISIBLE);
    }

    private void changeRecyclerViewForm(RecyclerViewForm recyclerViewForm) {
        adapter.changeRecyclerViewForm(recyclerViewForm);
        binding.recyclerViewFavorites.setAdapter(adapter);
        setRecyclerViewLayoutManagerAndIcon(recyclerViewForm);
    }

    private void getFavoriteFilms() {
        List<FilmWrap> favorites = favoritesLoadViewModel.getFavoriteFilms();
        if(favorites != null) {
            if(favorites.size() != 0) {
                adapter.setFilms(favorites);
                notFoundFavorites(false);
            } else {
                notFoundFavorites(true);
            }
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

    @SuppressLint("ResourceAsColor")
    private void initSearchView() {
        MenuItem searchItem = binding.topAppBar.getMenu().findItem(R.id.search_item);
        SearchView searchView = (SearchView) searchItem.getActionView();
        //searchView.setBackgroundColor(R.color.search_color);
        ImageView iView = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
        iView.setImageResource(R.drawable.ic_delete_search);
        EditText etView = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
        etView.setTextAppearance(R.style.LightTextStyle);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchView.clearFocus();
                List<FilmWrap> films = favoritesLoadViewModel.searchFilmsAmongFavorites(query);
                if(films.size() == 0) {
                    Toast.makeText(getContext(), getString(R.string.not_found_films_among_favorites),
                            Toast.LENGTH_SHORT).show();
                } else {
                    adapter.setFilms(films);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                adapter.setFilms(favoritesLoadViewModel.getFavoriteFilms());
                return true;
            }
        });
    }



    public interface OnNavigateToFragmentListener {
        void navigateToFilmsFragment();
        void navigateToDescriptionFragmentFromFavorites(int id);
    }
}
