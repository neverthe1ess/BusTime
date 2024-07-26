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

import java.util.List;

public class BusStopFragment extends Fragment {
    private RecyclerView recyclerView;
    private StopsAdpater stopsAdpater;
    private BusStopDatabase busStopDatabase;


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
        // Inflate the layout for this fragment
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
        new Thread(() -> {
            List<BusStop> busStops = busStopDatabase.busStopDao().getFavoriteBusStops();
            getActivity().runOnUiThread(() -> {
                stopsAdpater = new StopsAdpater(busStops, new StopsAdpater.FavoriteClickListener() {
                    @Override
                    public void onFavoriteClick(BusStop busStop) {
                        updateFavoriteStatus(busStop);
                    }
                });
                recyclerView.setAdapter(stopsAdpater);
            });
        }).start();
    }

    private void updateFavoriteStatus(BusStop busStop){
        new Thread(() -> {
            busStopDatabase.busStopDao().updateBusStop(busStop);
            getActivity().runOnUiThread(() -> {
                stopsAdpater.notifyDataSetChanged();
            });
        }).start();
    }
}