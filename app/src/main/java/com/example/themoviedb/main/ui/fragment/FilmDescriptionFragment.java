package com.example.themoviedb.main.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.themoviedb.R;
import com.example.themoviedb.databinding.FragmentFilmDescriptionBinding;
import com.example.themoviedb.di.DI;
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.viewModel.DescriptionFilmViewModel;
import com.example.themoviedb.main.viewModel.FavoritesLoadViewModel;
import com.example.themoviedb.main.viewModel.FilmsSearchViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import javax.inject.Inject;

public class FilmDescriptionFragment extends Fragment {

    private FragmentFilmDescriptionBinding binding;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private DescriptionFilmViewModel descriptionFilmViewModel;

    private OnNavigateBackListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            listener = (OnNavigateBackListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnNavigateBackListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFilmDescriptionBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        assert getArguments() != null;
        FilmDescriptionFragmentArgs args = FilmDescriptionFragmentArgs.fromBundle(getArguments());
        String sourceName = args.getSourceName();
        int filmId = args.getIdFilm();

        DI.getAppComponent().inject(this);
        descriptionFilmViewModel = new ViewModelProvider(this, viewModelFactory).get(DescriptionFilmViewModel.class);

        bindFilm(descriptionFilmViewModel.getFilm(sourceName, filmId));

        binding.topAppBar.setNavigationOnClickListener(clickListener -> {
            listener.navigateBack();
        });

        binding.topAppBar.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.favorite:
                    boolean isFavorite = descriptionFilmViewModel.getIsFavorite().getValue();
                    descriptionFilmViewModel.setFilmFavorite(!isFavorite, filmId);
                    return true;
                default:
                    return false;
            }
        });

        descriptionFilmViewModel.getIsFavorite().observe(getViewLifecycleOwner(), this::setFavoriteMenuIcon);

        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                listener.navigateBack();
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), callback);
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void bindFilm(FilmWrap film) {
        binding.titleText.setText(film.getTitle());
        binding.originalTitleText.setText(film.getOriginalTitle() + film.getYearReleaseDate());
        binding.voteAverageText.setText(String.valueOf(film.getVoteAverage()));
        binding.voteCountText.setText(String.valueOf(film.getVoteCount()));
        if (film.getOverview().isEmpty()) {
            binding.filmOverviewText.setTextAppearance(R.style.GrayTextStyle);
            binding.filmOverviewText.setText(R.string.not_found_overview);
        } else {
            binding.filmOverviewText.setText(film.getOverview());
        }
        Picasso.get()
                .load(String.format(requireContext().getString(R.string.poster_url),
                        film.getPosterPath()))
                .placeholder(Objects.requireNonNull(requireContext().getDrawable(R.drawable.film_poster_placeholder)))
                .into(binding.poster);
        setFavoriteMenuIcon(descriptionFilmViewModel.getIsFavorite().getValue());
    }

    private void setFavoriteMenuIcon(boolean isFavorite) {
        binding.topAppBar.getMenu().findItem(R.id.favorite).setIcon(isFavorite ? R.drawable.ic_favorite
                : R.drawable.ic_no_favorite);
    }

    public interface OnNavigateBackListener {
        void navigateBack();
    }


}
