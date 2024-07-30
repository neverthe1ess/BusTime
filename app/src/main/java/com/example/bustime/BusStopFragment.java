package com.example.bustime;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bustime.repositorydatabase.BusStop;
import com.example.bustime.repositorydatabase.BusStopDatabase;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BusStopFragment extends Fragment {
    private RecyclerView recyclerView;
    private StopsAdpater stopsAdpater;
    private BusStopDatabase busStopDatabase;
    private CompositeDisposable disposables = new CompositeDisposable();


    public BusStopFragment() {
        super(R.layout.fragment_stops);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stops, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView3);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        busStopDatabase = BusStopDatabase.getInstance(getContext());
        loadBusStops();
    }

    private void loadBusStops(){
        disposables.add(
                Single.fromCallable(() -> busStopDatabase.busStopDao().getAllBusStops())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(busStops -> {
                            stopsAdpater = new StopsAdpater(busStops, this::updateFavoriteStatus);
                            recyclerView.setAdapter(stopsAdpater);
                        }, throwable -> {
                            // 에러 처리
                        })
        );
    }


    private void updateFavoriteStatus(BusStop busStop){
        disposables.add(
                Completable.fromAction(() -> busStopDatabase.busStopDao().updateBusStop(busStop))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            stopsAdpater.notifyDataSetChanged();
                        }, throwable -> {
                            // 에러 처리
                        })
        );
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        disposables.clear();
    }
}