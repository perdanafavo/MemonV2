package com.example.projectalpha.Views;

import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Models.SubModels.WitelData;

import java.util.HashMap;
import java.util.List;

public interface MainManagerViews{
    List<WitelData> getDataWitel();
    HashMap<String, List<LaporanData>> getMapWitelStatus();
    HashMap<String, List<BIRData>> getMapTemperatureStatus();
    HashMap<String, List<FuelData>> getMapFuelStatus();
    HashMap<String, List<PowerData>> getMapPowerStatus();
}
