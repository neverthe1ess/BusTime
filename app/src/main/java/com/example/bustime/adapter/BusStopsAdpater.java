package com.example.bustime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bustime.R;
import com.example.bustime.repositorydatabase.BusStop;

import java.util.ArrayList;
import java.util.List;

public class BusStopsAdpater extends RecyclerView.Adapter<BusStopsAdpater.ViewHolder> {
    private List<BusStop> busStopList;
    private List<BusStop> busStopListFull;
    private FavoriteClickListener favoriteClickListener;
    private ItemClickListener itemClickListener;

    public interface FavoriteClickListener {
        void onFavoriteClick(BusStop busStop);
    }

    public interface ItemClickListener {
        void onItemClick(BusStop busStop);
    }

    public BusStopsAdpater(List<BusStop> busStopList, FavoriteClickListener listener, ItemClickListener itemClickListener){
        this.busStopList = busStopList;
        this.favoriteClickListener = listener;
        this.itemClickListener = itemClickListener;
        busStopListFull = new ArrayList<>(busStopList); // 기존 List copy
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
        holder.itemView.setOnClickListener(v -> {
            if(itemClickListener != null){
                itemClickListener.onItemClick(busStopEntity);
            }
        });
    }

    public void filter(String text){
        busStopList.clear();
        if(text.isEmpty()){
            busStopList.addAll(busStopListFull);
        } else {
            text = text.toLowerCase();
            for(BusStop busStopItem : busStopListFull){
                if(busStopItem.stationName.toLowerCase().contains(text)){
                    busStopList.add(busStopItem);
                }
            }
        }
        notifyDataSetChanged();
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

