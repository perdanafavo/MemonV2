package com.example.projectalpha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectalpha.Activity.AdminActivity.ResetUserActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ResetUserAdapter extends RecyclerView.Adapter<ResetUserAdapter.ResetUserHolder> {

    private Context context;
    private List<UsersData> dataUsers;

    public ResetUserAdapter(List<UsersData> itemUpdate) {
        this.dataUsers = itemUpdate;
    }

    public void filterList(List<UsersData> filteredList) {
        this.dataUsers = filteredList;
    }

    @NotNull
    @Override
    public ResetUserHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ResetUserHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reset_user, parent, false));
    }

    @Override
    public void onBindViewHolder(ResetUserHolder holder, int position) {
        holder.tvNama.setText(dataUsers.get(position).getNama());
        holder.tvUsername.setText(dataUsers.get(position).getUsername());

        final int finalPosition = position;
        holder.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(
                        new Intent(context, ResetUserActivity.class)
                            .putExtra(ENVIRONMENT.ID_PETUGAS, dataUsers.get(finalPosition).getId())
                            .putExtra(ENVIRONMENT.USERNAME_PETUGAS, dataUsers.get(finalPosition).getUsername())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        if(dataUsers != null)return dataUsers.size();
        else return 0;
    }

    class ResetUserHolder extends RecyclerView.ViewHolder {
        private TextView tvNama, tvUsername;
        private Button btnReset;
        private ResetUserHolder(View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.txtNama);
            tvUsername = itemView.findViewById(R.id.txtUsername);
            btnReset = itemView.findViewById(R.id.btnReset);
        }
    }
}
