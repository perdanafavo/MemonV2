package com.example.projectalpha.Activity.ManagerActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.example.projectalpha.Adapter.ManagerFragmentAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Fragment.MalamFragment;
import com.example.projectalpha.Fragment.PagiFragment;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Models.SubModels.WitelData;
import com.example.projectalpha.Models.TimeModels;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.Presenter.TimePresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.example.projectalpha.Views.MainManagerViews;
import com.example.projectalpha.Views.TimeView;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.List;

public class MainManagerActivity extends CustomCompatActivity
        implements TimeView, MainManagerViews,ApplicationViews, ApplicationViews.WitelViews.GetRequest,
            ApplicationViews.StatusReport.GetAreaStatusRequest, ApplicationViews.StatusReport.GetTemperatureRequest, ApplicationViews.StatusReport.GetFuelRequest, ApplicationViews.StatusReport.GetPowerRequest{
    private ApplicationPresenter applicationPresenter;
    private ProgressDialog mDialog;

    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tvTanggal;
    private List<WitelData> witelData;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private HashMap<String, List<LaporanData>> mapWitelStatus;
    private HashMap<String, List<BIRData>> mapTemperatureStatus;
    private HashMap<String, List<FuelData>> mapFuelStatus;
    private HashMap<String, List<PowerData>> mapPowerStatus;
    private int pageIndex = 0;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_manager);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pageIndex = tabLayout.getSelectedTabPosition();

        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        } else {
            mDialog.show();
            new TimePresenter(MainManagerActivity.this).requestTimer();
        }
    }

    private void createView(){
        mDialog = new ProgressDialog(MainManagerActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
    }
    private void configViewPager(@NonNull ViewPager viewPager){
        ManagerFragmentAdapter managerFragmentAdapter = new ManagerFragmentAdapter(getSupportFragmentManager());
        managerFragmentAdapter.addFragment(new PagiFragment(), getString(R.string.pagi));
        managerFragmentAdapter.addFragment(new MalamFragment(), getString(R.string.malam));
        viewPager.setAdapter(managerFragmentAdapter);
    }
    private void setBtnFooter() {
        outClick();
        historyClick();
    }

    private void setVariable(){
        ENVIRONMENT.POWER_SELECTED_ON = getString(R.string.on);
        ENVIRONMENT.POWER_SELECTED_OFF = getString(R.string.off);

        tvTanggal = findViewById(R.id.txtTanggal);
        swipeRefreshLayout = findViewById(R.id.swlayout);
        tabLayout = findViewById(R.id.tabShift);
        viewPager = findViewById(R.id.viewPagerSTO);

        applicationPresenter = new ApplicationPresenter(MainManagerActivity.this);
        mapWitelStatus       = new HashMap<>();
        mapTemperatureStatus = new HashMap<>();
        mapFuelStatus = new HashMap<>();
        mapPowerStatus = new HashMap<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                mDialog.show();
                onResume();
            }
        });
    }

    @Override
    public void successRequestTimer(TimeModels dataResponse) {
        String txtWaktu, txtHari, txtTanggal;
        txtHari = dataResponse.getHari();
        txtTanggal = dataResponse.getBulan();
        txtWaktu                = txtHari+", "+ txtTanggal;

        tvTanggal.setText(txtWaktu);
        applicationPresenter.getWitel();
    }

    @Override
    public HashMap<String, List<LaporanData>> getMapWitelStatus() {
        return mapWitelStatus;
    }

    @Override
    public HashMap<String, List<BIRData>> getMapTemperatureStatus() {
        return this.mapTemperatureStatus;
    }

    @Override
    public HashMap<String, List<FuelData>> getMapFuelStatus() {
        return this.mapFuelStatus;
    }

    @Override
    public HashMap<String, List<PowerData>> getMapPowerStatus() {
        return this.mapPowerStatus;
    }

    @Override
    public List<WitelData> getDataWitel() {
        return this.witelData;
    }

    @Override
    public void requestFailled(String massage) {
        mDialog.dismiss();
        simpleToast(massage);
    }

    @Override
    public void successRequest() {

    }

    @Override
    public void failedRequest(String message) {
        simpleToast(message);
        mDialog.dismiss();
    }

    @Override
    public void SuccessRequestGetWitel(List<WitelData> data) {
        this.witelData = data;
        applicationPresenter.getAreaStatus();
    }

    @Override
    public void successAreaRequest(List<LaporanData> morning, List<LaporanData> night) {
        mapWitelStatus.put(ENVIRONMENT.MORNING_STATUS, morning);
        mapWitelStatus.put(ENVIRONMENT.NIGHT_STATUS, night);

        applicationPresenter.getTemperatureStatus();
    }

    @Override
    public void successTemperatureRequest(List<BIRData> morning, List<BIRData> night) {
        mapTemperatureStatus.put(ENVIRONMENT.MORNING_TEMPERATURE_STATUS, morning);
        mapTemperatureStatus.put(ENVIRONMENT.NIGHT_TEMPERATURE_STATUS, night);

        applicationPresenter.getPowerStatus();
    }

    @Override
    public void successPowerRequest(List<PowerData> morning, List<PowerData> night) {
        mapPowerStatus.put(ENVIRONMENT.MORNING_POWER_STATUS, morning);
        mapPowerStatus.put(ENVIRONMENT.NIGHT_POWER_STATUS, night);

        applicationPresenter.getFuelStatus();
    }

    @Override
    public void successFuelRequest(List<FuelData> morning, List<FuelData> night) {
        mapFuelStatus.put(ENVIRONMENT.MORNING_FUEL_STATUS, morning);
        mapFuelStatus.put(ENVIRONMENT.NIGHT_FUEL_STATUS, night);

        configViewPager(viewPager);
        viewPager.setCurrentItem(pageIndex);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setScrollPosition(pageIndex, 0f, true);

        mDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            finishAffinity();
        }
        else {
            simpleToast(ENVIRONMENT.BACKPRESSED_MESSAGE);
        }
        mBackPressed = System.currentTimeMillis();
    }
}
