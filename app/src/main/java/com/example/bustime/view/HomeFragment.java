package com.example.bustime.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.bustime.R;
import com.example.bustime.adapter.BusStopsAdpater;
import com.example.bustime.repositorydatabase.BusStop;
import com.example.bustime.repositorydatabase.BusStopDatabase;
import com.example.bustime.viewmodel.MainViewModel;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private BusStopsAdpater busStopsAdpater;
    private BusStopDatabase busStopDatabase;
    private ImageView emptyWarningImgView;
    private MainViewModel viewModel;

    public HomeFragment() {
        super(R.layout.fragment_home);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        emptyWarningImgView = view.findViewById(R.id.empty_warning_imgView);

        busStopDatabase = BusStopDatabase.getInstance(getContext());
        viewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);
        viewModel.getSearchQuery().observe(getViewLifecycleOwner(), query -> {
            if(busStopsAdpater != null){
                busStopsAdpater.filter(query);
            }
        });
        loadBusStops();
    }

    private void loadBusStops(){
        viewModel.fetchFavoriteBusStops();
        viewModel.getFavoriteBusStops().observe(getViewLifecycleOwner(), busStops -> {
            if(busStops.isEmpty()) {
                emptyWarningImgView.setVisibility(View.VISIBLE);
            } else {
                emptyWarningImgView.setVisibility(View.INVISIBLE);
            }
            busStopsAdpater = new BusStopsAdpater(busStops, new BusStopsAdpater.FavoriteClickListener() {
                @Override
                public void onFavoriteClick(BusStop busStop) {
                    updateFavoriteStatus(busStop);
                }
            }, busStop -> {
                Intent intent = new Intent(getActivity(), StopInfoActivity.class);
                intent.putExtra("BUS_STOP_ID", busStop.busStopId);
                intent.putExtra("BUS_STOP_NAME", busStop.stationName);
                intent.putExtra("BUS_STOP_ENG_NAME", busStop.stationEngName);
                startActivity(intent);
            });
            recyclerView.setAdapter(busStopsAdpater);
        });
    }

    private void updateFavoriteStatus(BusStop busStop){
        viewModel.updateFavoriteStatus(busStop);
        viewModel.getFavoriteUpdateStatus().observe(getViewLifecycleOwner(), success -> {
            if(success){
                busStopsAdpater.notifyDataSetChanged();
            }
        });
    }
}