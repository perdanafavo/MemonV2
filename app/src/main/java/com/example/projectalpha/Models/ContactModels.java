package com.example.projectalpha.Models;

import com.example.projectalpha.Models.SubModels.ContactData;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ContactModels {
    @SerializedName("status")
    private boolean status;

    @SerializedName("message")
    private String message;

    @SerializedName("data")
    private ArrayList<ContactData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<ContactData> getData() {
        return data;
    }

    public void setData(ArrayList<ContactData> data) {
        this.data = data;
    }
}
