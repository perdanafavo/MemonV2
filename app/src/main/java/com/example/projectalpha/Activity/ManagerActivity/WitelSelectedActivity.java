package com.example.projectalpha.Activity.ManagerActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectalpha.Adapter.WitelSelectedAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Models.SubModels.WitelData;
import com.example.projectalpha.Models.TimeModels;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.Presenter.TimePresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.example.projectalpha.Views.TimeView;

import java.util.List;

public class WitelSelectedActivity extends CustomCompatActivity
        implements TimeView, ApplicationViews, ApplicationViews.WitelViews.GetRequest, ApplicationViews.StatusReport.GetTemperatureRequest,
            ApplicationViews.StatusReport.GetFuelRequest, ApplicationViews.StatusReport.GetPowerRequest{

    private ApplicationPresenter applicationPresenter;

    private ProgressDialog mDialog;

    private List<WitelData> place;
    private List<BIRData> dataTemperature;
    private List<PowerData> dataPower;
    private List<FuelData> dataFuel;

    private String REPORTING;

    private int SHIFT;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvKondisi, tvTanggal;
    private RecyclerView rvWitel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witel_selected);

        setVariable();
        setBtnFooter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDialog.show();

        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            mDialog.dismiss();
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        } else {
            new TimePresenter(WitelSelectedActivity.this).requestTimer();
        }
    }

    private void setVariable() {
        mDialog = new ProgressDialog(WitelSelectedActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        REPORTING = getIntent().getStringExtra(ENVIRONMENT.INFORMATION_TITLE);
        SHIFT     = getIntent().getIntExtra(ENVIRONMENT.SHIFT_SELECTED, 0);
        applicationPresenter = new ApplicationPresenter(WitelSelectedActivity.this);

        tvKondisi = findViewById(R.id.txtKondisi);
        tvTanggal = findViewById(R.id.txtTanggal);
        rvWitel   = findViewById(R.id.rvWitel);
        swipeRefreshLayout = findViewById(R.id.swlayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                onResume();
            }
        });
    }
    private void setBtnFooter() {
        backClick(MainManagerActivity.class);
        homeClick(MainManagerActivity.class);
        outClick();
    }

    @Override
    public void successRequestTimer(TimeModels dataResponse) {
        String txtWaktu = null, txtHari, txtTanggal, kondisi = null;
        txtHari = dataResponse.getHari();
        txtTanggal = dataResponse.getBulan();
        if (SHIFT == 1) txtWaktu = txtHari+", "+ txtTanggal+" - Shift Pagi";
        else if (SHIFT == 2) txtWaktu = txtHari+", "+ txtTanggal+" - Shift Malam";

        tvTanggal.setText(txtWaktu);

        switch (REPORTING) {
            case ENVIRONMENT.TEPERATURE_TITLE:
                kondisi = "Suhu Ruangan > 23°C";
                break;
            case ENVIRONMENT.POWER_TITLE:
                kondisi = "Catuan Anomali";
                break;
            case ENVIRONMENT.FUEL_TITLE:
                kondisi = "BBM Kritis";
                break;
        }
        tvKondisi.setText(kondisi);
        applicationPresenter.getWitel();
    }

    @Override
    public void requestFailled(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    @Override
    public void successRequest() {

    }

    @Override
    public void failedRequest(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    public int getSHIFT() {
        return SHIFT;
    }

    @Override
    public void SuccessRequestGetWitel(List<WitelData> data) {
        place = data;
        switch (REPORTING) {
            case ENVIRONMENT.TEPERATURE_TITLE:
                applicationPresenter.getTemperatureStatus();
                break;
            case ENVIRONMENT.POWER_TITLE:
                applicationPresenter.getPowerStatus();
                break;
            case ENVIRONMENT.FUEL_TITLE:
                applicationPresenter.getFuelStatus();
                break;
        }
    }

    @Override
    public void successTemperatureRequest(List<BIRData> morning, List<BIRData> night) {
        if (SHIFT == 1){
            dataTemperature = morning;
        } else if(SHIFT == 2){
            dataTemperature = night;
        }
        rvWitel.setLayoutManager(new LinearLayoutManager(WitelSelectedActivity.this));
        rvWitel.setAdapter(new WitelSelectedAdapter(place, dataTemperature, null, null));
        mDialog.dismiss();
    }

    @Override
    public void successFuelRequest(List<FuelData> morning, List<FuelData> night) {
        if (SHIFT == 1){
            dataFuel = morning;
        } else if(SHIFT == 2){
            dataFuel = night;
        }
        rvWitel.setLayoutManager(new LinearLayoutManager(WitelSelectedActivity.this));
        rvWitel.setAdapter(new WitelSelectedAdapter(place, null, null, dataFuel));
        mDialog.dismiss();
    }

    @Override
    public void successPowerRequest(List<PowerData> morning, List<PowerData> night) {
        if (SHIFT == 1){
            dataPower = morning;
        } else if(SHIFT == 2){
            dataPower = night;
        }
        rvWitel.setLayoutManager(new LinearLayoutManager(WitelSelectedActivity.this));
        rvWitel.setAdapter(new WitelSelectedAdapter(place, null, dataPower, null));
        mDialog.dismiss();
    }
}
