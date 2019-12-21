package com.example.projectalpha.Views;

import com.example.projectalpha.Models.SubModels.LaporanData;

import java.util.List;

public interface ValidatorViews {
    List<LaporanData> getValidasiStatus();
    String getNamaSTO();
    void refreshValidator();
}
