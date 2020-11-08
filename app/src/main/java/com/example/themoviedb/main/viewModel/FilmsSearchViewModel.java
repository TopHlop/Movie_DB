package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.themoviedb.SharedPreferencesHelper;
import com.example.themoviedb.main.data.FilmWrap;
import com.example.themoviedb.main.model.DataStorage;
import com.example.themoviedb.main.model.FavoritesLoadModelImpl;
import com.example.themoviedb.main.model.FavoritesLoadModelUseCase;
import com.example.themoviedb.main.model.FilmSearchModelImpl;
import com.example.themoviedb.main.model.FilmsSearchModelUseCase;
import com.example.themoviedb.main.model.UserModelImpl;
import com.example.themoviedb.main.model.UserModelUseCase;
import com.example.themoviedb.main.ui.RecyclerViewForm;

import java.util.List;

import javax.inject.Inject;

public class FilmsSearchViewModel extends ViewModel {

    private SharedPreferencesHelper sharedPreferencesHelper;
    private FilmsSearchModelUseCase filmsSearchModel;
    private UserModelUseCase userModel;
    private FavoritesLoadModelUseCase favoritesLoadModel;
    private DataStorage storage;

    private MutableLiveData<List<FilmWrap>> filmSearchResult = new MutableLiveData<>();

    @Inject
    public FilmsSearchViewModel(FilmSearchModelImpl filmSearchModel, UserModelImpl userModel,
                                SharedPreferencesHelper sharedPreferencesHelper,
                                DataStorage storage, FavoritesLoadModelImpl favoritesLoadModel) {
        this.sharedPreferencesHelper = sharedPreferencesHelper;
        this.userModel = userModel;
        this.filmsSearchModel = filmSearchModel;
        this.storage = storage;
        this.favoritesLoadModel = favoritesLoadModel;
        loadUserData();
        loadGenreList();
    }

    //get accountId for add, delete, load favorites
    private void loadUserData() {
        if(storage.getUserData() == null) {
            String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
            if (sessionId != null) {
                userModel.getUserData(sessionId);
                userModel.getUser().observeForever(user -> {
                    storage.setUserData(user);
                });
            } else {
                //ошибка хранения sessionId
            }
        }
    }

    private void loadGenreList() {
        if(storage.getGenres() == null) {
            filmsSearchModel.loadGenreList();
            filmsSearchModel.getGenreList().observeForever(genres -> {
                storage.setGenres(genres);
            });
        }
    }

    //to indicate in the description whether the film is a favorite
    private void loadFavorites() {
        if(storage.getFavoriteFilms() == null) {
            String sessionId = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_SESSION_ID, null);
            if (sessionId != null) {
                favoritesLoadModel.loadFavorites(storage.getUserData().getId(), sessionId);
                favoritesLoadModel.getFavoritesFilmsResult().observeForever(favorites -> {
                    for(FilmWrap film : favorites) {
                        film.setGenres(storage.getGenres());
                    }
                    storage.setFavoriteFilms(favorites);
                });
            } else {
                //ошибка хранения sessionId
            }
        }
    }

    public String getLastQuerySearchString() {
        return storage.getQuerySearch();
    }

    public List<FilmWrap> getFilmFromLastRequest() {
        return storage.getFilmsSearch();
    }

    public void searchFilms(String query) {
        loadFavorites();
        storage.setQuerySearch(query);
        filmsSearchModel.searchFilms(query, storage.getUserData().isIncludeAdult());
        filmsSearchModel.getResultSearch().observeForever(films -> {
            for(FilmWrap film : films) {
                film.setGenres(storage.getGenres());
            }
            storage.setFilmsSearch(films);
            filmSearchResult.setValue(films);
        });
    }

    public LiveData<List<FilmWrap>> getResultSearch() {
        return filmSearchResult;
    }

    public void setRecyclerViewForm(RecyclerViewForm recyclerViewForm) {
        sharedPreferencesHelper.putRecyclerViewForm(recyclerViewForm);
    }

    public RecyclerViewForm getRecyclerViewForm() {
        return sharedPreferencesHelper.getRecyclerViewForm();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        filmsSearchModel.clearDisposable();
    }
}
