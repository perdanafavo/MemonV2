package com.example.projectalpha.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectalpha.Activity.ManagerActivity.MainManagerActivity;
import com.example.projectalpha.Activity.ManagerActivity.WitelActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.WitelData;
import com.example.projectalpha.R;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

public class MainManagerAdapter extends RecyclerView.Adapter<MainManagerAdapter.ViewHolder> {

    private MainManagerActivity mainManagerActivity;

    private List<WitelData> Place;
    private HashMap<String, List<LaporanData>> placeStatus;

    private String fragmentTitle;

    public MainManagerAdapter(List<WitelData> Place, HashMap<String, List<LaporanData>> placeStatus, String fragmentTitle){
        this.Place = Place;
        this.placeStatus = placeStatus;
        this.fragmentTitle = fragmentTitle;
    }

    @NotNull
    @Override
    public MainManagerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mainManagerActivity = (MainManagerActivity) parent.getContext();
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sto2, parent, false));
    }

    @Override
    public void onBindViewHolder(@NotNull MainManagerAdapter.ViewHolder holder, int position) {
        List<LaporanData> dataStatus = null;

        if (fragmentTitle.equals(ENVIRONMENT.MORNING_STATUS)) dataStatus = placeStatus.get(ENVIRONMENT.MORNING_STATUS);
        else if (fragmentTitle.equals(ENVIRONMENT.NIGHT_STATUS)) dataStatus = placeStatus.get(ENVIRONMENT.NIGHT_STATUS);

        byte status = 0;
        if (dataStatus != null) {
            for (LaporanData data:dataStatus){
                if (data.getWitel() == Place.get(position).getId()){
                        if(data.getId()!=0 && data.isStatus() && data.getStatus_approved()==1){
                            if (status == 0 || status ==1){
                                status = 1;
                                holder.linearLayout.setBackgroundResource(R.drawable.rectangle2);
                            } else {
                                holder.linearLayout.setBackgroundResource(R.drawable.rectangle);
                                break;
                            }
                        } else if (data.getId()!=0 && !data.isStatus() && data.getStatus_approved()==1){
                            holder.linearLayout.setBackgroundResource(R.drawable.rectangle);
                            break;
                        } else if (data.getId() == 0){
                            if (status == 0 || status == 2){
                                status =2;
                                holder.linearLayout.setBackgroundResource(R.drawable.rectangle3);
                            }else {
                                holder.linearLayout.setBackgroundResource(R.drawable.rectangle);
                                break;
                            }
                    }
                }
            }
        }

        final int finalPosition = position;

        holder.placeName.setText(Place.get(position).getSingkatan());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainManagerActivity.getApplicationContext(), WitelActivity.class);
                intent.putExtra(ENVIRONMENT.ID_WITEL, Place.get(finalPosition).getId());
                intent.putExtra(ENVIRONMENT.NAMA_WITEL, Place.get(finalPosition).getNama());
                intent.putExtra(ENVIRONMENT.SHIFT_SELECTED, fragmentTitle);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                mainManagerActivity.startActivity(intent);
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
