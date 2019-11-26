package com.example.projectalpha.Adapter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.projectalpha.Activity.AdminActivity.UpdateContactActivity;
import com.example.projectalpha.Activity.AdminActivity.UpdateMenuContactActivity;
import com.example.projectalpha.Activity.UsersActivity.FormActivity.KontakPentingActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.R;

import java.util.List;

public class KontakAdapter extends RecyclerView.Adapter<KontakAdapter.ViewHolder> {

    private final int REQUEST_CALL = 1;

    private List<UsersData> itemKontak;
    private Context context;

    public KontakAdapter(List<UsersData> itemKontak) {
        this.itemKontak = itemKontak;
    }

    public void setData(List<UsersData> itemKontak) {
        this.itemKontak = itemKontak;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new KontakAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_update_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String [] data = {
                itemKontak.get(position).getNama(),
                "0"+itemKontak.get(position).getHandphone(),
                String.valueOf(itemKontak.get(position).getWitel())
        };

        final String handphone = "0"+itemKontak.get(position).getHandphone();
        holder.tvNama.setText(itemKontak.get(position).getNama());
        holder.tvHandphone.setText(handphone);
        holder.tvWitel.setText(itemKontak.get(position).getNama_witel());

        if (context instanceof KontakPentingActivity){
            holder.btnUpdate.setVisibility(View.GONE);
            holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ContextCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
                    } else {
                        String dial = "tel:"+handphone;
                        context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
                    }
                }
            });
        }

        final int finalPosition = position;
        if (context instanceof UpdateMenuContactActivity){
            holder.btnUpdate.setVisibility(View.VISIBLE);
            holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    context.startActivity(
                            new Intent(context, UpdateContactActivity.class)
                                .putExtra(ENVIRONMENT.ID_PETUGAS, itemKontak.get(finalPosition).getId())
                                .putExtra(ENVIRONMENT.DATA_PETUGAS, data)
                    );
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (itemKontak == null) return 0;
        return itemKontak.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWitel, tvNama, tvHandphone;
        private RelativeLayout relativeLayout;
        private ImageButton btnUpdate;
        private ViewHolder(View itemView) {
            super(itemView);
            tvWitel = itemView.findViewById(R.id.txtWitel);
            tvNama = itemView.findViewById(R.id.txtNama);
            tvHandphone = itemView.findViewById(R.id.txtHandphone);
            relativeLayout = itemView.findViewById(R.id.kontakWrap);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
