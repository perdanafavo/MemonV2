package com.example.projectalpha.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectalpha.Activity.AdminActivity.InfoUserActivity;
import com.example.projectalpha.Activity.AdminActivity.UpdateUserActivity;
import com.example.projectalpha.Activity.ManagerActivity.STOActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.UpdateMenuUserViews;
import com.example.projectalpha.Views.ValidatorViews;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ValidatorAdapter extends RecyclerView.Adapter<ValidatorAdapter.ValidatorHolder>{
    private Context context;
    private List<LaporanData> itemLaporan;
    private String namaSTO;

    public ValidatorAdapter(List<LaporanData> itemLaporan) {
        this.itemLaporan = itemLaporan;
    }
    public void isiValidator(List<LaporanData> list, String namaSTO){
        itemLaporan = list;
        this.namaSTO = namaSTO;
    }

    @NotNull
    @Override
    public ValidatorAdapter.ValidatorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
       // validatorViews = (ValidatorViews) parent.getContext();
        return new ValidatorAdapter.ValidatorHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verifikasi_laporan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ValidatorHolder holder, final int position) {
        final String [] info = {
                Integer.toString(itemLaporan.get(position).getId()),
                itemLaporan.get(position).getUsers(),
                Integer.toString(itemLaporan.get(position).getWitel()),
                Integer.toString(itemLaporan.get(position).getSto()),
                Integer.toString(itemLaporan.get(position).getShift()),
                itemLaporan.get(position).getTanggal_shift(),
                itemLaporan.get(position).getTanggal_upload(),
                itemLaporan.get(position).getJam_upload(),
                Byte.toString(itemLaporan.get(position).getStatus()),
                Byte.toString(itemLaporan.get(position).getStatus_approved())

        };

        holder.tv_item_name.setText(itemLaporan.get(position).getTanggal_upload()+" "+itemLaporan.get(position).getJam_upload());

        holder.linear_verifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(
                        new Intent(context, STOActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, itemLaporan.get(position).getId())
                        .putExtra(ENVIRONMENT.TANGGAL_LAPORAN, itemLaporan.get(position).getTanggal_shift())
                        .putExtra(ENVIRONMENT.NAMA_STO, namaSTO)
                        .putExtra(ENVIRONMENT.ID_STO, itemLaporan.get(position).getSto())
//                                .putExtra(ENVIRONMENT.DATA_PETUGAS, info)
                );
            }
        });

    }



    @Override
    public int getItemCount() {
        if(itemLaporan != null)return itemLaporan.size();
        else return 0;
    }

    public static class ValidatorHolder extends RecyclerView.ViewHolder {
        private TextView tv_item_name;
        private LinearLayout linear_verifikasi;

        public ValidatorHolder(View itemView) {
            super(itemView);
            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            linear_verifikasi = itemView.findViewById(R.id.linear_verifikasi);
        }
    }
}
