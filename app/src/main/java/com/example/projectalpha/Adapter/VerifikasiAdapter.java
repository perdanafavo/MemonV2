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
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.R;

import java.util.List;

public class VerifikasiAdapter extends RecyclerView.Adapter<VerifikasiAdapter.VerifikasiHolder> {

    private Context context;
    private List<LaporanData> itemVerifikasi;
    private List<STOData> allSTO;
    private String stoid;

    public VerifikasiAdapter(List<LaporanData> itemVerifikasi) {
        this.itemVerifikasi = itemVerifikasi;
    }

    public void isiVerifikasi(List<LaporanData> list, String stoid){
        itemVerifikasi = list;
        this.stoid = stoid
    }

    public void isiVerifikasi(List<LaporanData> list, List<STOData> sto){
        itemVerifikasi = list;
        allSTO = sto;
    }

    @NonNull
    @Override
    public VerifikasiAdapter.VerifikasiHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new VerifikasiAdapter.VerifikasiHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_verifikasi_laporan, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VerifikasiHolder holder, final int position) {
        String waktuUpload = itemVerifikasi.get(position).getJam_upload();
        String tanggalUpload = itemVerifikasi.get(position).getTanggal_upload();
        holder.txtTanggal.setText(tanggalUpload);
        holder.txtSTO.setText(allSTO.get(itemVerifikasi.get(position).getSto()-1).getNama());
        holder.txtWaktu.setText(waktuUpload);
        holder.rlViewVerifikasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(
                        new Intent(context, STOActivity.class)
                                .putExtra(ENVIRONMENT.ID_LAPORAN, itemVerifikasi.get(position).getId())
                                .putExtra(ENVIRONMENT.TANGGAL_LAPORAN, itemVerifikasi.get(position).getTanggal_shift())
                                .putExtra(ENVIRONMENT.NAMA_STO, allSTO.get(itemVerifikasi.get(position).getSto()-1).getNama())
                                .putExtra(ENVIRONMENT.ID_STO, itemVerifikasi.get(position).getSto())
                );
            }
        });

    }

    @Override
    public int getItemCount() {
        if(itemVerifikasi != null)return itemVerifikasi.size();
        else return 0;
    }

    public static class VerifikasiHolder extends RecyclerView.ViewHolder {
        private TextView txtTanggal, txtWaktu, txtSTO;
        private RelativeLayout rlViewVerifikasi;

        private VerifikasiHolder(View itemView) {
            super(itemView);
            txtTanggal = itemView.findViewById(R.id.txtTanggal);
            txtWaktu = itemView.findViewById(R.id.txtWaktu);
            txtSTO = itemView.findViewById(R.id.txtSTO);
            rlViewVerifikasi = itemView.findViewById(R.id.relativeViewVerifikasiLaporan);
        }
    }
}
