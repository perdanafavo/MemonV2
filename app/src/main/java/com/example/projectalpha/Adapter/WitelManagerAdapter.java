package com.example.projectalpha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.projectalpha.Activity.ManagerActivity.STOActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.R;

import java.util.List;

public class WitelManagerAdapter extends RecyclerView.Adapter<WitelManagerAdapter.ViewHolder> {

    private Context context;

    private List<LaporanData> dataStatus;
    private List<STOData> Place;
    private String tanggal = null;

    public WitelManagerAdapter(List<STOData> place, List<LaporanData> laporanData) {
        setHasStableIds(true);
        this.Place = place;
        this.dataStatus = laporanData;
    }

    public WitelManagerAdapter(List<STOData> place, List<LaporanData> laporanData, String tanggal) {
        setHasStableIds(true);
        this.Place = place;
        this.dataStatus = laporanData;
        this.tanggal = tanggal;
    }

    @Override
    public WitelManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sto2, parent, false));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(WitelManagerAdapter.ViewHolder holder, int position) {
        int index = 0;
        if (dataStatus != null){
            for (LaporanData data:dataStatus){
                if (data.getSto() == Place.get(position).getId()){
                    if(data.getId()!=0 && data.isStatus()){
                        holder.linearLayout.setBackgroundResource(R.drawable.rectangle2);
                    } else if (data.getId()!=0 && !data.isStatus()){
                        holder.linearLayout.setBackgroundResource(R.drawable.rectangle);
                    }else if (data.getId() == 0){
                        holder.linearLayout.setBackgroundResource(R.drawable.rectangle3);
                    }
                    break;
                }
                index++;
            }
        }

        final int finalPosition = position;
        final int finalIndex = index;
        holder.placeName.setText(Place.get(position).getSingkatan());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context.getApplicationContext(), STOActivity.class);
                if (dataStatus != null && dataStatus.size()>finalIndex){
                    intent.putExtra(ENVIRONMENT.ID_LAPORAN, dataStatus.get(finalIndex).getId());
                    intent.putExtra(ENVIRONMENT.TANGGAL_LAPORAN, dataStatus.get(finalIndex).getTanggal_shift());
                } else if (tanggal != null){
                    intent.putExtra(ENVIRONMENT.TANGGAL_LAPORAN, tanggal);
                }
                intent.putExtra(ENVIRONMENT.NAMA_STO, Place.get(finalPosition).getNama());
                intent.putExtra(ENVIRONMENT.ID_STO, Place.get(finalPosition).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Place.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView placeName;
        private LinearLayout linearLayout;
        private ViewHolder(View itemView) {
            super(itemView);
            placeName = itemView.findViewById(R.id.tvListManager);
            linearLayout = itemView.findViewById(R.id.llListManager);
        }
    }
}
