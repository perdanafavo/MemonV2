package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.ReportStatusData;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportStatusModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("data")
    private List<ReportStatusData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<ReportStatusData> getData() {
        return data;
    }

    public void setData(List<ReportStatusData> data) {
        this.data = data;
    }
}
