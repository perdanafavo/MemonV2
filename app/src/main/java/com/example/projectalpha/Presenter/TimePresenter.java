package com.example.projectalpha.Presenter;

import com.example.projectalpha.Models.TimeModels;
import com.example.projectalpha.Network.RetrofitConnect;
import com.example.projectalpha.Views.TimeView;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimePresenter {

    private TimeView timeView;

    public TimePresenter(TimeView timeView){
        this.timeView = timeView;
    }

    public void requestTimer(){
        RetrofitConnect.getInstance()
                .getTime()
                .enqueue(new Callback<TimeModels>() {
                    @Override
                    public void onResponse(@NotNull Call<TimeModels> call, @NotNull Response<TimeModels> response) {
                        timeView.successRequestTimer(response.body());
                    }
                    @Override
                    public void onFailure(@NotNull Call<TimeModels> call, @NotNull Throwable t) {
                        t.printStackTrace();
                        timeView.failedRequest("Terjadi Kesalahan, Coba lagi nanti");
                    }
                });
    }
}
