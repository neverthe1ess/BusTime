package com.example.bustime.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bustime.R;
import com.example.bustime.repository.api.dto.stopData.StopBusResults;

import java.util.List;

public class BusStopInfoAdapter extends RecyclerView.Adapter<BusStopInfoAdapter.ViewHolder> {
    private List<StopBusResults> mResultList;

    public BusStopInfoAdapter(List<StopBusResults> stopBusResultsList) {
        mResultList = stopBusResultsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.businfo_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getBusNumber().setText(mResultList.get(position).getRouteNum());
        holder.getBusVia().setText(mResultList.get(position).getVia());
        holder.getBusTime().setText(mResultList.get(position).getPredictTm() + "분 뒤 도착예정");
        holder.getRemainStation().setText(mResultList.get(position).getRemainStation() + "번째 정류장 남음");

    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (int i = 0; i < mResultList.size(); i++) {
            if(mResultList.get(i).getPredictTm() != null){
                count++;
            }
        }
        return count;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView busNumber;
        private TextView busTime;
        private TextView remainStation;
        private TextView BusVia;

        public ViewHolder(View view) {
            super(view);

            busNumber = (TextView) view.findViewById(R.id.busNumber);
            busTime = (TextView) view.findViewById(R.id.busTime);
            remainStation = (TextView) view.findViewById(R.id.remainStation);
            BusVia = (TextView) view.findViewById(R.id.busVia);
        }

        public TextView getBusNumber() {
            return busNumber;
        }

        public TextView getBusTime() {
            return busTime;
        }

        public TextView getRemainStation() {
            return remainStation;
        }

        public TextView getBusVia() {
            return BusVia;
        }
    }
}