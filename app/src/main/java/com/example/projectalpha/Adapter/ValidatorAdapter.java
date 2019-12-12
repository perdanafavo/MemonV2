package com.example.projectalpha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectalpha.Activity.ManagerActivity.STOActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.R;

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
        return new ValidatorAdapter.ValidatorHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_verifikasi_laporan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ValidatorHolder holder, final int position) {
        String tanggalUpload = itemLaporan.get(position).getTanggal_upload();
        String waktuUpload = itemLaporan.get(position).getJam_upload();
        holder.tvItemTanggal.setText(tanggalUpload);
        holder.tvItemWaktu.setText(waktuUpload);
        holder.relativeVerifikasiLaporan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(
                        new Intent(context, STOActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, itemLaporan.get(position).getId())
                        .putExtra(ENVIRONMENT.TANGGAL_LAPORAN, itemLaporan.get(position).getTanggal_shift())
                        .putExtra(ENVIRONMENT.NAMA_STO, namaSTO)
                        .putExtra(ENVIRONMENT.ID_STO, itemLaporan.get(position).getSto())
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
        private TextView tvItemTanggal, tvItemWaktu;
        private RelativeLayout relativeVerifikasiLaporan;

        private ValidatorHolder(View itemView) {
            super(itemView);
            tvItemTanggal = itemView.findViewById(R.id.tvItemTanggal);
            tvItemWaktu = itemView.findViewById(R.id.tvItemWaktu);
            relativeVerifikasiLaporan = itemView.findViewById(R.id.relativeVerifikasiLaporan);
        }
    }
}
