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
    private FavoriteClickListener favoriteClickListener;

    public interface FavoriteClickListener {
        void onFavoriteClick(BusStop busStop);
    }

    public StopsAdpater(List<BusStop> busStopList, FavoriteClickListener listener){
        this.busStopList = busStopList;
        this.favoriteClickListener = listener;
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
        BusStop busStopEntity = busStopList.get(position);
        holder.getBusStopName().setText(busStopEntity.stationName);
        holder.getBusStopNumber().setText(String.valueOf(busStopEntity.busStopId));
        holder.getFavoriteButton().setImageResource(busStopEntity.isFavorite ?
                R.drawable.baseline_favorite_24 : R.drawable.baseline_favorite_border_24);
        holder.getFavoriteButton().setOnClickListener(v -> {
            busStopEntity.isFavorite = !busStopEntity.isFavorite;
            notifyItemChanged(position);
            favoriteClickListener.onFavoriteClick(busStopEntity);
        });
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

