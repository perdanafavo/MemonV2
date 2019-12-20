package com.example.projectalpha.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projectalpha.Activity.ManagerActivity.STOSelectedActivity;
import com.example.projectalpha.Activity.ManagerActivity.ShowEmployeeActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class STOSelectedAdapter extends RecyclerView.Adapter<STOSelectedAdapter.ViewHolder> {

    private STOSelectedActivity stoSelectedActivity;

    private List<STOData> place;
    private List<BIRData> dataTemperature;
    private List<PowerData> dataPower;
    private List<FuelData> dataFuel;

    public STOSelectedAdapter(List<STOData> place, List<BIRData> dataTemperature, List<PowerData> dataPower, List<FuelData> dataFuel){
        setHasStableIds(true);
        this.place = place;
        this.dataTemperature = dataTemperature;
        this.dataPower = dataPower;
        this.dataFuel = dataFuel;
    }

    @NotNull
    @Override
    public STOSelectedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        stoSelectedActivity = (STOSelectedActivity) parent.getContext();
        return new STOSelectedAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sto, parent, false));
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.btnCall.setVisibility(View.GONE);
        holder.llWrapReport.setVisibility(View.GONE);

        ArrayList<String> value = new ArrayList<>();

        int LAPORAN = 0, count = 0;

        if (dataTemperature != null){
            for (BIRData data:dataTemperature){
                if (data.getSto() == place.get(position).getId()){
                    if (data.getSuhu() > 23 && data.getStatus_approved()==1){
                        LAPORAN = data.getLaporan();
                        switch (data.getRuangan()){
                            case 1:
                                count++;
                                value.add("Sentral : "+data.getSuhu()+" °C");
                                break;
                            case 2:
                                count++;
                                value.add("Transmisi : "+data.getSuhu()+" °C");
                                break;
                            case 3:
                                count++;
                                value.add("Rectifier : "+data.getSuhu()+" °C");
                                break;
                            case 4:
                                count++;
                                value.add("Batere Kering : "+data.getSuhu()+" °C");
                                break;
                            case 5:
                                count++;
                                value.add("Akses/GPON : "+data.getSuhu()+" °C");
                                break;
                            case 6:
                                count++;
                                value.add("Genset : "+data.getSuhu()+" °C");
                                break;
                            case 7:
                                count++;
                                value.add("OLO : "+data.getSuhu()+" °C");
                            case 8:
                                break;
                            default:
                                value.add("Terjadi Kesalahan");
                                break;
                        }
                    }
                }
            }
        } else if (dataPower != null){
            for (PowerData data:dataPower){
                if (data.getSto() == place.get(position).getId()){
                    if (data.getPln() != null && data.getPln().equals(ENVIRONMENT.POWER_SELECTED_ON)
                            && data.getGenset() != null && data.getGenset().equals(ENVIRONMENT.POWER_SELECTED_ON ) && data.getStatus_approved()==1) {
                        LAPORAN = data.getLaporan();
                        count++;
                        value.add("PLN : "+data.getPln());
                        value.add("GENSET : "+data.getGenset());
                        break;
                    } else if (data.getPln() != null && data.getPln().equals(ENVIRONMENT.POWER_SELECTED_OFF)
                            && data.getGenset() != null && data.getGenset().equals(ENVIRONMENT.POWER_SELECTED_OFF) && data.getStatus_approved()==1) {
                        LAPORAN = data.getLaporan();
                        count++;
                        value.add("PLN : "+data.getPln());
                        value.add("GENSET : "+data.getGenset());
                        break;
                    }
                }
            }
        } else if (dataFuel != null){
            for (FuelData data:dataFuel){
                if (data.getSto() == place.get(position).getId()){
                    if (data.getTanggal_shift() != null){
                        if (data.getTanki_bulanan() <= data.getKapasitas_rendah() && data.getStatus_approved()==1){
                            count++;
                            LAPORAN = data.getLaporan();
                            value.add("Kapasitas : "+data.getKapasitas()+" Liter");
                            value.add("Tanki Bulanan : "+data.getTanki_bulanan()+" %");
                            value.add("Sisa Tanki : "+Math.floor(data.getKapasitas()*data.getTanki_bulanan()/100));
                            break;
                        }
                    }
                }
            }
        }

        String text = place.get(position).getSingkatan()+" : "+count;
        holder.tvPlace.setText(text);

        if (count>0)holder.llSelected.setBackgroundResource(R.drawable.rectangle4);


        final ArrayList<String> finalValue = value;
        holder.llSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalValue.size() > 0){
                    holder.llWrapReport.setVisibility(View.VISIBLE);
                    holder.llWrapReport.removeAllViewsInLayout();
                    holder.btnCall.setVisibility(View.VISIBLE);
                    for (String data:finalValue){
                        TextView newTV = new TextView(stoSelectedActivity);
                        newTV.setText(data);
                        newTV.setTextSize(20);
                        holder.llWrapReport.addView(newTV);
                    }
                }
                v.setClickable(false);
            }
        });

        final int finalPosition = position;
        final int finalLAPORAN = LAPORAN;
        holder.btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stoSelectedActivity.startActivity(
                        new Intent(stoSelectedActivity, ShowEmployeeActivity.class)
                            .putExtra(ENVIRONMENT.ID_LAPORAN, finalLAPORAN)
                            .putExtra(ENVIRONMENT.ID_STO, place.get(finalPosition).getId())
                );
            }
        });
    }

    @Override
    public int getItemCount() {
        return place.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llSelected, llWrapReport;
        private TextView tvPlace;
        private Button btnCall;
        ViewHolder(View itemView) {
            super(itemView);
            llSelected = itemView.findViewById(R.id.viewSelect);
            tvPlace = itemView.findViewById(R.id.tvPlace);
            llWrapReport = itemView.findViewById(R.id.llWrapReport);
            btnCall = itemView.findViewById(R.id.btnCall);
        }
    }
}
