package com.example.projectalpha.Activity.ManagerActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectalpha.Adapter.STOSelectedAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Models.TimeModels;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.Presenter.TimePresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.example.projectalpha.Views.TimeView;

import java.util.List;

public class STOSelectedActivity extends CustomCompatActivity
        implements TimeView, ApplicationViews, ApplicationViews.ReportViews.GetReportParam, ApplicationViews.StoViews.GetRequest, ApplicationViews.StatusReport,
            ApplicationViews.StatusReport.GetTemperatureRequest, ApplicationViews.StatusReport.GetFuelRequest, ApplicationViews.StatusReport.GetPowerRequest{

    private ApplicationPresenter applicationPresenter;

    private ProgressDialog mDialog;

    private String REPORTING;
    private int WITEL, SHIFT;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvTanggal, tvWitel, tvKondisi;
    private RecyclerView rvSTO;

    private List<STOData> place;
    private List<BIRData> dataTemperature = null;
    private List<PowerData> dataPower = null;
    private List<FuelData> dataFuel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sto_selected);

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
            new TimePresenter(STOSelectedActivity.this).requestTimer();
        }
    }

    private void setVariable() {
        mDialog = new ProgressDialog(STOSelectedActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        WITEL = getIntent().getIntExtra(ENVIRONMENT.ID_WITEL, 0);
        SHIFT = getIntent().getIntExtra(ENVIRONMENT.SHIFT_SELECTED, 0);
        REPORTING = getIntent().getStringExtra(ENVIRONMENT.INFORMATION_TITLE);

        applicationPresenter = new ApplicationPresenter(STOSelectedActivity.this);

        tvTanggal = findViewById(R.id.txtTanggal);
        tvKondisi = findViewById(R.id.txtKondisi);
        tvWitel   = findViewById(R.id.txtWitel);
        rvSTO = findViewById(R.id.rvSTO);

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
        backClick();
        homeClick(MainManagerActivity.class);
        outClick();
    }



    @Override
    public void successRequest() {

    }
    @Override
    public int getIndexWitel() {
        return WITEL;
    }
    @Override
    public int getIndexShift() {
        return 0;
    }
    @Override
    public int getIndexSto() {
        return 0;
    }
    @Override
    public String getDateReport() {
        return null;
    }

    @Override
    public void successRequestTimer(TimeModels dataResponse) {
        String txtWaktu = null, txtHari, txtTanggal, kondisi = null;
        txtHari = dataResponse.getHari();
        txtTanggal = dataResponse.getBulan();
        if (SHIFT == 1) txtWaktu = txtHari+", "+ txtTanggal+" - Shift Pagi";
        else if (SHIFT == 2) txtWaktu = txtHari+", "+ txtTanggal+" - Shift Malam";

        tvTanggal.setText(txtWaktu);
        tvWitel.setText(getIntent().getStringExtra(ENVIRONMENT.NAMA_WITEL));

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
        applicationPresenter.getStoByWitel();
    }

    @Override
    public void SucessRequestGetSTO(List<STOData> data) {
        place = data;
        switch (REPORTING) {
            case ENVIRONMENT.TEPERATURE_TITLE:
                applicationPresenter.getTemperatureStatusByWitel();
                break;
            case ENVIRONMENT.POWER_TITLE:
                applicationPresenter.getPowerStatusByWitel();
                break;
            case ENVIRONMENT.FUEL_TITLE:
                applicationPresenter.getFuelStatusByWitel();
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
        rvSTO.setLayoutManager(new LinearLayoutManager(STOSelectedActivity.this));
        rvSTO.onScrollStateChanged(0);
        rvSTO.setAdapter(new STOSelectedAdapter(place, dataTemperature, null, null));
        mDialog.dismiss();
    }

    @Override
    public void successFuelRequest(List<FuelData> morning, List<FuelData> night) {
        if (SHIFT == 1){
            dataFuel = morning;
        } else if(SHIFT == 2){
            dataFuel = night;
        }
        rvSTO.setLayoutManager(new LinearLayoutManager(STOSelectedActivity.this));
        rvSTO.setAdapter(new STOSelectedAdapter(place, null, null, dataFuel));
        mDialog.dismiss();
    }

    @Override
    public void successPowerRequest(List<PowerData> morning, List<PowerData> night) {
        if (SHIFT == 1){
            dataPower = morning;
        } else if(SHIFT == 2){
            dataPower = night;
        }
        rvSTO.setLayoutManager(new LinearLayoutManager(STOSelectedActivity.this));
        rvSTO.setAdapter(new STOSelectedAdapter(place, null, dataPower, null));
        mDialog.dismiss();
    }

    @Override
    public void failedRequest(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    @Override
    public void requestFailled(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }
}
