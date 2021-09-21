package com.android.app.covid19trackerapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class LatestUpdates_Adapter extends RecyclerView.Adapter<LatestUpdates_Adapter.CovidViewHolder> {
    List<CovidModel> arrayList;
    Activity context;

    public LatestUpdates_Adapter(List<CovidModel> arrayList, Activity context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CovidViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = context.getLayoutInflater().inflate(R.layout.latest_updates,null);
        CovidViewHolder viewHolder = new CovidViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CovidViewHolder holder, int position) {
        CovidModel covid = arrayList.get(position);
        String text ="";
        String countryName="";
        int activeCases = covid.getTodayCases();
        int activeDeaths = covid.getTodayDeaths();

        if(activeCases == 0 || activeDeaths == 0){
            text = "No new case and death in ";
        }
        else{
            text = activeCases+" new cases and "+ activeDeaths+" new deaths in ";
        }
        countryName = covid.getCountryName();

        holder.setCases(text);
        holder.setCountryName(countryName);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class CovidViewHolder extends RecyclerView.ViewHolder{
        TextView cases;
        TextView countryName;
        public CovidViewHolder(@NonNull View itemView) {
            super(itemView);
            cases = itemView.findViewById(R.id.cases);
            countryName =  itemView.findViewById(R.id.latest_updates_countryName);
        }

        public void setCases(String s){
            cases.setText(s+" ");
        }

        public void setCountryName(String name){
            countryName.setText(name);
        }
    }
}
