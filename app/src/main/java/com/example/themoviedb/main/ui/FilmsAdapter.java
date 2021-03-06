package com.example.themoviedb.main.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.themoviedb.R;
import com.example.themoviedb.databinding.RecyclerViewFilmCardFormBinding;
import com.example.themoviedb.databinding.RecyclerViewFilmListFormBinding;
import com.example.themoviedb.main.data.FilmWrap;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class FilmsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FilmWrap> films = new LinkedList<>();
    private RecyclerViewForm recyclerViewForm;

    private FilmsDelegate delegate;

    public interface FilmsDelegate {
        void openDescription(int id);
    }

    public void setFilms(List<FilmWrap> films) {
        this.films.clear();
        this.films.addAll(films);
        notifyDataSetChanged();
    }

    public void attachDelegate(FilmsDelegate delegate) {
        this.delegate = delegate;
    }

    public void changeRecyclerViewForm(RecyclerViewForm recyclerViewForm) {
        this.recyclerViewForm = recyclerViewForm;
    }

    public RecyclerViewForm getRecyclerViewForm() {
        return recyclerViewForm;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (recyclerViewForm) {
            case CARD_VIEW:
                RecyclerViewFilmCardFormBinding bindingList = RecyclerViewFilmCardFormBinding.inflate(inflater, parent, false);
                return new ViewHolderFilmsCard(bindingList, delegate);
            case LIST_VIEW:
            default:
                RecyclerViewFilmListFormBinding bindingCard = RecyclerViewFilmListFormBinding.inflate(inflater, parent, false);
                return new ViewHolderFilmsList(bindingCard, delegate);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (recyclerViewForm) {
            case CARD_VIEW:
                ((ViewHolderFilmsCard) holder).bind(films.get(position));
                break;
            case LIST_VIEW:
            default:
                ((ViewHolderFilmsList) holder).bind(films.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        return films.size();
    }

    public class ViewHolderFilmsList extends RecyclerView.ViewHolder {
        RecyclerViewFilmListFormBinding binding;
        FilmsDelegate delegate;

        ViewHolderFilmsList(RecyclerViewFilmListFormBinding binding, FilmsDelegate delegate) {
            super(binding.getRoot());
            this.binding = binding;
            this.delegate = delegate;
        }

        @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
        void bind(FilmWrap film) {
            binding.itemLayout.setOnClickListener(view -> {
                delegate.openDescription(film.getId());
            });
            binding.titleText.setText(film.getTitle());
            binding.originalTitleText.setText(film.getOriginalTitle() + film.getYearReleaseDate());
            binding.voteAverageText.setText(String.valueOf(film.getVoteAverage()));
            binding.voteCountText.setText(String.valueOf(film.getVoteCount()));
            binding.genreText.setText(film.getGenres());
            Context context = binding.poster.getContext();
            Picasso.get()
                    .load(String.format(context.getString(R.string.poster_url),
                            film.getPosterPath()))
                    .placeholder(Objects.requireNonNull(context.getDrawable(R.drawable.film_poster_placeholder)))
                    .into(binding.poster);
        }
    }

    public class ViewHolderFilmsCard extends RecyclerView.ViewHolder {
        RecyclerViewFilmCardFormBinding binding;
        FilmsDelegate delegate;

        ViewHolderFilmsCard(RecyclerViewFilmCardFormBinding binding, FilmsDelegate delegate) {
            super(binding.getRoot());
            this.binding = binding;
            this.delegate = delegate;
        }

        @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
        void bind(FilmWrap film) {
            binding.itemLayout.setOnClickListener(view -> {
                delegate.openDescription(film.getId());
            });
            binding.titleText.setText(film.getTitle());
            binding.originalTitleText.setText(film.getOriginalTitle() + film.getYearReleaseDate());
            binding.voteAverageText.setText(String.valueOf(film.getVoteAverage()));
            binding.voteCountText.setText(String.valueOf(film.getVoteCount()));
            binding.genreText.setText(film.getGenres());
            Context context = binding.poster.getContext();
            Picasso.get()
                    .load(String.format(context.getString(R.string.poster_url),
                            film.getPosterPath()))
                    .placeholder(Objects.requireNonNull(context.getDrawable(R.drawable.film_poster_placeholder)))
                    .into(binding.poster);
        }
    }
}
