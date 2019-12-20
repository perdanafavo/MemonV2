package com.example.projectalpha.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectalpha.Activity.ManagerActivity.MainManagerActivity;
import com.example.projectalpha.Activity.ManagerActivity.WitelSelectedActivity;
import com.example.projectalpha.Adapter.MainManagerAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.MainManagerViews;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MalamFragment extends Fragment {

    private final int SHIFT = 2;

    private MainManagerViews mainManagerViews;
    private Context context;

    private Button btnSuhu, btnBBM, btnCatuan;

    public MalamFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_malam, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvWitel = view.findViewById(R.id.rvWitel);
        btnBBM      = view.findViewById(R.id.btnStatusBBM);
        btnSuhu     = view.findViewById(R.id.btnStatusSuhu);
        btnCatuan   = view.findViewById(R.id.btnStatusCatuan);

        rvWitel.setLayoutManager(new GridLayoutManager(view.getContext(), 5));
        rvWitel.setAdapter(new MainManagerAdapter(mainManagerViews.getDataWitel(), mainManagerViews.getMapWitelStatus(), ENVIRONMENT.NIGHT_STATUS));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setTemperature();
        setFuel();
        setPower();
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mainManagerViews = (MainManagerActivity) context;
       this.context = context;
    }

    private void setTemperature(){
        List<BIRData> temperature = mainManagerViews.getMapTemperatureStatus().get(ENVIRONMENT.NIGHT_TEMPERATURE_STATUS);
        int count = 0;
        if (temperature != null) {
            for (BIRData data:temperature){
                if (data.getSuhu() > 23 && data.getStatus_approved()==1 && data.getRuangan()!=8) count++;
            }
        }
        btnSuhu.setText(String.valueOf(count));
        btnSuhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(
                        new Intent(context, WitelSelectedActivity.class)
                                .putExtra(ENVIRONMENT.SHIFT_SELECTED, SHIFT)
                                .putExtra(ENVIRONMENT.INFORMATION_TITLE, ENVIRONMENT.TEPERATURE_TITLE)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            }
        });
    }
    private void setFuel(){
        List<FuelData> fuel = mainManagerViews.getMapFuelStatus().get(ENVIRONMENT.NIGHT_FUEL_STATUS);
        int count = 0;
        if (fuel != null) {
            for (FuelData data:fuel){
                if (data.getTanggal_shift() != null && data.getStatus_approved()==1){
                    if (data.getTanki_bulanan() <= data.getKapasitas_rendah()) count++;
                }
            }
        }
        btnBBM.setText(String.valueOf(count));
        btnBBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(
                        new Intent(context, WitelSelectedActivity.class)
                                .putExtra(ENVIRONMENT.SHIFT_SELECTED, SHIFT)
                                .putExtra(ENVIRONMENT.INFORMATION_TITLE, ENVIRONMENT.FUEL_TITLE)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            }
        });
    }
    private void setPower(){
        List<PowerData> power = mainManagerViews.getMapPowerStatus().get(ENVIRONMENT.NIGHT_POWER_STATUS);
        int count = 0;
        if (power != null) {
            for (PowerData data:power){
                if (data.getPln() != null && data.getPln().equals(ENVIRONMENT.POWER_SELECTED_ON)
                        && data.getGenset() != null && data.getGenset().equals(ENVIRONMENT.POWER_SELECTED_ON ) && data.getStatus_approved()==1) {
                    count++;
                } else if (data.getPln() != null && data.getPln().equals(ENVIRONMENT.POWER_SELECTED_OFF)
                        && data.getGenset() != null && data.getGenset().equals(ENVIRONMENT.POWER_SELECTED_OFF) && data.getStatus_approved()==1) {
                    count++;
                }
            }
        }
        btnCatuan.setText(String.valueOf(count));
        btnCatuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(
                        new Intent(context, WitelSelectedActivity.class)
                                .putExtra(ENVIRONMENT.SHIFT_SELECTED, SHIFT)
                                .putExtra(ENVIRONMENT.INFORMATION_TITLE, ENVIRONMENT.POWER_TITLE)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK)
                );
            }
        });
    }
}
