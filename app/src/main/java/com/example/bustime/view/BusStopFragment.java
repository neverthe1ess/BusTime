package com.example.bustime.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bustime.R;
import com.example.bustime.adapter.BusStopsAdpater;
import com.example.bustime.repositorydatabase.BusStop;
import com.example.bustime.repositorydatabase.BusStopDatabase;
import com.example.bustime.viewmodel.MainViewModel;


import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class BusStopFragment extends Fragment {
    private RecyclerView recyclerView;
    private BusStopsAdpater busStopsAdpater;
    private BusStopDatabase busStopDatabase;
    private CompositeDisposable disposables = new CompositeDisposable();
    private MainViewModel viewModel;


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

        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            if(busStopsAdpater != null){
                busStopsAdpater.filter(query);
            }
        });
    }

    private void loadBusStops(){
        disposables.add(
                Single.fromCallable(() -> busStopDatabase.busStopDao().getAllBusStops())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(busStops -> {
                            busStopsAdpater = new BusStopsAdpater(busStops, this::updateFavoriteStatus, busStop -> {
                                Intent intent = new Intent(getActivity(), StopInfoActivity.class);
                                intent.putExtra("BUS_STOP_ID", busStop.busStopId);
                                intent.putExtra("BUS_STOP_NAME", busStop.stationName);
                                intent.putExtra("BUS_STOP_ENG_NAME", busStop.stationEngName);
                                startActivity(intent);
                            });
                            recyclerView.setAdapter(busStopsAdpater);
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
                            busStopsAdpater.notifyDataSetChanged();
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