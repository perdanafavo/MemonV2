package com.example.projectalpha.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projectalpha.Activity.AdminActivity.InfoUserActivity;
import com.example.projectalpha.Activity.AdminActivity.UpdateUserActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.UpdateMenuUserViews;

import java.util.List;

public class ItemUpdateAdapter extends RecyclerView.Adapter<ItemUpdateAdapter.ItemUpdateHolder> {

    private Context context;
    private UpdateMenuUserViews updateMenuUserViews;
    private List<UsersData> itemUpdate;

    public ItemUpdateAdapter(List<UsersData> itemUpdate) {
        this.itemUpdate = itemUpdate;
    }

    public void filterList(List<UsersData> list){
        itemUpdate = list;
    }

    @Override
    public ItemUpdateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        updateMenuUserViews = (UpdateMenuUserViews) parent.getContext();
        return new ItemUpdateHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_update_user, parent, false));
    }

    @Override
    public void onBindViewHolder(ItemUpdateHolder holder, int position) {
        final String [] info = {
                itemUpdate.get(position).getNama(),
                "0"+itemUpdate.get(position).getHandphone(),
                itemUpdate.get(position).getNama_witel(),
                itemUpdate.get(position).getNama_sto(),
                itemUpdate.get(position).getUsername(),
                itemUpdate.get(position).getPassword(),
                itemUpdate.get(position).getFoto()
        };

        holder.tvNama.setText(itemUpdate.get(position).getNama());
        holder.tvUsername.setText(itemUpdate.get(position).getUsername());

        holder.btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(
                        new Intent(context, InfoUserActivity.class)
                                .putExtra(ENVIRONMENT.DATA_PETUGAS, info)
                );
            }
        });

        final int finalPosition = position;
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context).setMessage("Apa Anda yakin ingin menghapus User?")
                        .setTitle("Konfirmasi Hapus")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                updateMenuUserViews.deleteUsers(itemUpdate.get(finalPosition).getId());
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).create().show();
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(
                        new Intent(context, UpdateUserActivity.class)
                                .putExtra(ENVIRONMENT.ID_PETUGAS, itemUpdate.get(finalPosition).getId())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        if(itemUpdate != null)return itemUpdate.size();
        else return 0;
    }

    class ItemUpdateHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvUsername;
        private ImageButton btnInfo, btnUpdate, btnDelete;

        ItemUpdateHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.txtNamaUser);
            tvUsername = itemView.findViewById(R.id.txtUsername);
            btnInfo = itemView.findViewById(R.id.btnInfo);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
