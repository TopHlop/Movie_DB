package com.example.themoviedb.main.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.themoviedb.R;
import com.example.themoviedb.databinding.FragmentFilmsBinding;
import com.example.themoviedb.di.DI;
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.ui.FilmsAdapter;
import com.example.themoviedb.main.ui.RecyclerViewForm;
import com.example.themoviedb.main.viewModel.FilmsSearchViewModel;

import java.util.List;

import javax.inject.Inject;

public class FilmsFragment extends Fragment {

    private FragmentFilmsBinding binding;
    private OnNavigateToDescriptionFragmentListener listener;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private FilmsSearchViewModel filmsSearchViewModel;

    private FilmsAdapter adapter;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (FilmsFragment.OnNavigateToDescriptionFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNavigateToDescriptionFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFilmsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        DI.getAppComponent().inject(this);
        filmsSearchViewModel = new ViewModelProvider(this, viewModelFactory).get(FilmsSearchViewModel.class);

        initRecyclerView();
        getFilmFromLastRequest();
        filmsSearchViewModel.getResultSearch().observe(getViewLifecycleOwner(), resultSearch -> {
            if (resultSearch.size() == 0) {
                notFoundFilms(true);
            } else {
                adapter.setFilms(resultSearch);
            }
        });

        binding.searchFilm.setQuery(filmsSearchViewModel.getLastQuerySearchString(), false);
        setListeners();
    }

    private void setListeners() {
        binding.searchFilm.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filmsSearchViewModel.searchFilms(query);
                notFoundFilms(false);
                binding.searchFilm.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        binding.recyclerViewSetting.setOnClickListener(view -> {
            switch (adapter.getRecyclerViewForm()) {
                case CARD_VIEW:
                    filmsSearchViewModel.setRecyclerViewForm(RecyclerViewForm.LIST_VIEW);
                    changeRecyclerViewForm(RecyclerViewForm.LIST_VIEW);
                    break;
                case LIST_VIEW:
                    filmsSearchViewModel.setRecyclerViewForm(RecyclerViewForm.CARD_VIEW);
                    changeRecyclerViewForm(RecyclerViewForm.CARD_VIEW);
                    break;
            }
        });
    }

    private void getFilmFromLastRequest() {
        List<FilmWrap> films = filmsSearchViewModel.getFilmFromLastRequest();
        if(films != null) {
            adapter.setFilms(films);
        }
    }

    private void initRecyclerView() {
        adapter = new FilmsAdapter();
        adapter.attachDelegate(new FilmsAdapter.FilmsDelegate() {
            @Override
            public void openDescription(int id) {
                listener.navigateToDescriptionFragmentFromFilms(id);
            }
        });
        changeRecyclerViewForm(filmsSearchViewModel.getRecyclerViewForm());
    }

    private void notFoundFilms(boolean isNotFound) {
        binding.filmRecyclerView.setVisibility(isNotFound ? View.GONE : View.VISIBLE);
        binding.notFoundLayout.setVisibility(isNotFound ? View.VISIBLE : View.GONE);
    }

    private void setRecyclerViewLayoutManagerAndIcon(RecyclerViewForm recyclerViewForm) {
        switch (recyclerViewForm) {
            case CARD_VIEW:
                binding.filmRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                binding.recyclerViewSetting.setImageResource(R.drawable.ic_list_24px);
                break;
            case LIST_VIEW:
            default:
                binding.filmRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.recyclerViewSetting.setImageResource(R.drawable.ic_card_24px);
                break;
        }
    }

    private void changeRecyclerViewForm(RecyclerViewForm recyclerViewForm) {
        adapter.changeRecyclerViewForm(recyclerViewForm);
        binding.filmRecyclerView.setAdapter(adapter);
        setRecyclerViewLayoutManagerAndIcon(recyclerViewForm);
    }


    public interface OnNavigateToDescriptionFragmentListener {
        void navigateToDescriptionFragmentFromFilms(int id);
    }
}
