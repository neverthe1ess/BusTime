package com.example.bustime.model;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class FavoriteDataStore {
    private static final String DATASTORE_NAME = "favorite_datas";
    private final RxDataStore<Preferences> dataStore;
    private static final Preferences.Key<Set<String>> FAVORITES_KEY = PreferencesKeys.stringSetKey("favorites");


    public FavoriteDataStore(Context context) {
        dataStore = new RxPreferenceDataStoreBuilder(context, DATASTORE_NAME).build();
    }

    public Flowable<Set<String>> getFavorites(){
        return dataStore.data().map(preferences -> preferences.get(FAVORITES_KEY));
    }

    public Single<Void> addFavorite(String busStopId) {
        return dataStore.updateDataAsync(preferences -> {
            MutablePreferences mutablePreferences = preferences.toMutablePreferences();
            Set<String> currentFavorites = mutablePreferences.get(FAVORITES_KEY);
            if (currentFavorites == null) {
                currentFavorites = new HashSet<>();
            }
            currentFavorites.add(busStopId);
            mutablePreferences.set(FAVORITES_KEY, currentFavorites);
            return Single.just(mutablePreferences);
        }).ignoreElement().toSingle(() -> null) ;
    }

    public Single<Void> removeFavorite(String busStopId) {
        return dataStore.updateDataAsync(preferences -> {
           MutablePreferences mutablePreferences = preferences.toMutablePreferences();
           Set<String> currentFavorites = mutablePreferences.get(FAVORITES_KEY);
           if (currentFavorites != null) {
               currentFavorites.remove(busStopId);
               mutablePreferences.set(FAVORITES_KEY, currentFavorites);
           }
           return Single.just(mutablePreferences);
        }).ignoreElement().toSingle(() -> null);
    }
}
