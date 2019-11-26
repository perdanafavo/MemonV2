package com.example.projectalpha.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectalpha.Activity.ManagerActivity.STOSelectedActivity;
import com.example.projectalpha.Activity.ManagerActivity.WitelSelectedActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Models.SubModels.WitelData;
import com.example.projectalpha.R;

import java.util.List;

public class WitelSelectedAdapter extends RecyclerView.Adapter<WitelSelectedAdapter.ViewHolder> {

    private WitelSelectedActivity witelSelectedActivity;

    private List<WitelData> place;
    private List<BIRData> dataTemperature;
    private List<PowerData> dataPower;
    private List<FuelData> dataFuel;

    public WitelSelectedAdapter(List<WitelData> place, List<BIRData> dataTemperature, List<PowerData> dataPower, List<FuelData> dataFuel){
        this.place = place;
        this.dataTemperature = dataTemperature;
        this.dataPower = dataPower;
        this.dataFuel = dataFuel;
    }

    @Override
    public WitelSelectedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        witelSelectedActivity = (WitelSelectedActivity) parent.getContext();
        return new WitelSelectedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sto, parent, false));
    }

    @Override
    public void onBindViewHolder(WitelSelectedAdapter.ViewHolder holder, int position) {
        String TITLE = null;

        int count = 0;

        if (dataTemperature != null){
            TITLE = ENVIRONMENT.TEPERATURE_TITLE;
            for (BIRData data:dataTemperature){
                if (data.getWitel() == place.get(position).getId()){
                    if (data.getSuhu() > 23){
                        count++;
                    }
                }
            }
        } else if (dataPower != null){
            TITLE = ENVIRONMENT.POWER_TITLE;
            for (PowerData data:dataPower){
                if (data.getWitel() == place.get(position).getId()){
                    if (data.getPln() != null && data.getPln().equals(ENVIRONMENT.POWER_SELECTED_ON)
                            && data.getGenset() != null && data.getGenset().equals(ENVIRONMENT.POWER_SELECTED_ON)) {
                        count++;
                    } else if (data.getPln() != null && data.getPln().equals(ENVIRONMENT.POWER_SELECTED_OFF)
                            && data.getGenset() != null && data.getGenset().equals(ENVIRONMENT.POWER_SELECTED_OFF)) {
                        count++;
                    }
                }
            }
        } else if (dataFuel != null){
            TITLE = ENVIRONMENT.FUEL_TITLE;
            for (FuelData data:dataFuel){
                if (data.getWitel() == place.get(position).getId()){
                    if (data.getTanggal_shift() != null){
                        if (data.getTanki_bulanan() <= data.getKapasitas_rendah()) count++;
                    }
                }
            }
        }

        String text = place.get(position).getSingkatan()+" : "+count;
        holder.tvPlace.setText(text);

        if (count >0)holder.llSelected.setBackgroundResource(R.drawable.rectangle4);

        final int finalPosition = position;
        final String finalTITLE = TITLE;
        holder.llSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                witelSelectedActivity.startActivity(
                        new Intent(witelSelectedActivity, STOSelectedActivity.class)
                                .putExtra(ENVIRONMENT.INFORMATION_TITLE, finalTITLE)
                                .putExtra(ENVIRONMENT.ID_WITEL, place.get(finalPosition).getId())
                                .putExtra(ENVIRONMENT.NAMA_WITEL, place.get(finalPosition).getNama())
                                .putExtra(ENVIRONMENT.SHIFT_SELECTED, witelSelectedActivity.getSHIFT())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return place.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llSelected;
        TextView tvPlace;
        ViewHolder(View itemView) {
            super(itemView);
            llSelected = itemView.findViewById(R.id.viewSelect);
            tvPlace = itemView.findViewById(R.id.tvPlace);
        }
    }
}
