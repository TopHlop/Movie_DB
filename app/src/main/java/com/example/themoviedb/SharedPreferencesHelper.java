package com.example.themoviedb;

import android.content.SharedPreferences;

import com.example.themoviedb.main.ui.RecyclerViewForm;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesHelper {

    public static final String KEY_SESSION_ID = "session_id";

    public static final String RECYCLER_VIEW_FORM = "recycler_view_form";
    private static final String CARD_VIEW_FORM = "card";
    private static final String LIST_VIEW_FORM = "list";
    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesHelper(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public void putString(String key, String value) {
        sharedPreferences.edit().putString(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return sharedPreferences.getString(key, defaultValue);
    }

    public void putRecyclerViewForm(RecyclerViewForm recyclerViewForm) {
        switch (recyclerViewForm) {
            case LIST_VIEW:
                sharedPreferences.edit().putString(RECYCLER_VIEW_FORM, LIST_VIEW_FORM).apply();
                break;
            case CARD_VIEW:
                sharedPreferences.edit().putString(RECYCLER_VIEW_FORM, CARD_VIEW_FORM).apply();
                break;
        }
    }

    public RecyclerViewForm getRecyclerViewForm() {
         switch (sharedPreferences.getString(RECYCLER_VIEW_FORM, LIST_VIEW_FORM)) {
             case CARD_VIEW_FORM:
                 return RecyclerViewForm.CARD_VIEW;
             default:
                 return RecyclerViewForm.LIST_VIEW;
         }
    }

    public void deleteDataByKey(String key) {
        sharedPreferences.edit().remove(key).apply();
    }
}
