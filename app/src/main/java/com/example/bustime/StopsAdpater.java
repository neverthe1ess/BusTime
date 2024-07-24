package com.example.bustime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bustime.bustimedatabase.BusStop;

import java.util.List;

public class StopsAdpater extends RecyclerView.Adapter<StopsAdpater.ViewHolder> {
    private List<BusStop> busStopList;

    public StopsAdpater(List<BusStop> busStopList){
        this.busStopList = busStopList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stops_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getBusStopName().setText(busStopList.get(position).stationName);
            holder.getBusStopNumber().setText(String.valueOf(busStopList.get(position).busStopId));
    }

    @Override
    public int getItemCount() {
        return busStopList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView busStopName;
        private TextView busStopNumber;
        private ImageButton favoriteButton;

        public ViewHolder(View view){
            super(view);

            busStopName = (TextView) view.findViewById(R.id.busStopsName);
            busStopNumber = (TextView) view.findViewById(R.id.busStopsNumber);
            favoriteButton = (ImageButton) view.findViewById(R.id.favoriteBtn);
        }

        public TextView getBusStopName(){
            return busStopName;
        }

        public TextView getBusStopNumber(){
            return busStopNumber;
        }

        public ImageButton getFavoriteButton(){
            return favoriteButton;
        }
    }
}
