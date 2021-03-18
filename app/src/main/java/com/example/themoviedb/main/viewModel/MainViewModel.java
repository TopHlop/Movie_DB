package com.example.themoviedb.main.viewModel;

import androidx.lifecycle.ViewModel;

import com.example.themoviedb.main.model.DataStorage;

import javax.inject.Inject;

public class MainViewModel extends ViewModel {

    private DataStorage storage;

    @Inject
    public MainViewModel(DataStorage storage) {
        this.storage = storage;
    }

    public void clearData() {
        storage.clearData();
    }
}
