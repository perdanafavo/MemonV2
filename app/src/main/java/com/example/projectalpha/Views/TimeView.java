package com.example.projectalpha.Views;

import com.example.projectalpha.Models.TimeModels;

public interface TimeView {
    void successRequestTimer(TimeModels dataResponse);
    void failedRequest(String message);
}
