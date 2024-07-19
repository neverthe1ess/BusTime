package com.example.bustime;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bustime.repository.api.dto.stationData.MyPojo;
import com.example.bustime.repository.api.dto.stopData.StopBusResults;
import com.example.bustime.repository.api.dto.stopData.StopPostResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private StopPostResults mBusList;

    private List<StopBusResults> mResultList;

    public CustomAdapter(StopPostResults postResults, List<StopBusResults> stopBusResultsList){
        mBusList = postResults;
        mResultList = stopBusResultsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getBusTime().setText(mResultList.get(position).getPredictTm() + " 분 남음");
            holder.getRemainStation().setText(mResultList.get(position).getRemainStation() + " 정거장 남음");
            holder.getBusNumber().setText(mResultList.get(position).getRouteNm());
    }

    @Override
    public int getItemCount() {
        return mResultList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
       private TextView busNumber;
       private TextView busTime;
       private TextView remainStation;

       public ViewHolder(View view){
           super(view);

           busNumber = (TextView) view.findViewById(R.id.busNumber);
           busTime = (TextView) view.findViewById(R.id.busTime);
           remainStation = (TextView) view.findViewById(R.id.remainStation);
       }

       public TextView getBusNumber(){
           return busNumber;
       }

       public TextView getBusTime(){
           return busTime;
       }

       public TextView getRemainStation() {return remainStation; }

    }
}
