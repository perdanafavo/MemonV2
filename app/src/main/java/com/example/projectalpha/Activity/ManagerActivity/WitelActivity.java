package com.example.projectalpha.Activity.ManagerActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectalpha.Adapter.WitelManagerAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Models.TimeModels;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.Presenter.TimePresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.example.projectalpha.Views.TimeView;

import java.util.HashMap;
import java.util.List;

public class WitelActivity extends CustomCompatActivity
        implements TimeView, ApplicationViews, ApplicationViews.StatusReport.GetPowerRequest, ApplicationViews.StatusReport.GetFuelRequest, ApplicationViews.StatusReport.GetTemperatureRequest,
        ApplicationViews.StatusReport.GetAreaStatusRequest, ApplicationViews.StoViews.GetRequest, ApplicationViews.ReportViews.GetReportParam, ApplicationViews.StatusReport{

    private ProgressDialog mDialog;

    private ApplicationPresenter applicationPresenter;

    private List<STOData> stoData;

    private HashMap<String, List<LaporanData>> mapSTOStatus;
    private HashMap<String, List<BIRData>> mapTemperatureStatus;
    private HashMap<String, List<FuelData>> mapFuelStatus;
    private HashMap<String, List<PowerData>> mapPowerStatus;

    private Button btnSuhu, btnBBM, btnCatuan;
    private TextView tvWitel, tvTanggal;
    private RecyclerView rvSTO;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout contentWrap;

    private int WITEL, IDSHIFT = 0;
    private String shiftSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_witel);

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
            new TimePresenter(WitelActivity.this).requestTimer();
        }
    }

    private void setVariable(){
        mDialog = new ProgressDialog(WitelActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        ENVIRONMENT.POWER_SELECTED_ON = getString(R.string.on);
        ENVIRONMENT.POWER_SELECTED_OFF = getString(R.string.off);

        btnSuhu                 =   findViewById(R.id.btnStatusSuhu);
        btnBBM                  =   findViewById(R.id.btnStatusBBM);
        btnCatuan               =   findViewById(R.id.btnStatusCatuan);
        rvSTO                   =   findViewById(R.id.rvSTO);
        contentWrap             =   findViewById(R.id.contentWrap);
        tvWitel                 =   findViewById(R.id.txtManagerWitel);
        tvTanggal               =   findViewById(R.id.tglLaporan);
        swipeRefreshLayout      = findViewById(R.id.swlayout);

        applicationPresenter    = new ApplicationPresenter(WitelActivity.this);

        WITEL                   = getIntent().getIntExtra(ENVIRONMENT.ID_WITEL, 0);
        shiftSelect             = getIntent().getStringExtra(ENVIRONMENT.SHIFT_SELECTED);

        if (shiftSelect.equals(ENVIRONMENT.MORNING_STATUS))IDSHIFT= 1;
        else if (shiftSelect.equals(ENVIRONMENT.NIGHT_STATUS)) IDSHIFT = 2;

        mapSTOStatus            = new HashMap<>();
        mapTemperatureStatus    = new HashMap<>();
        mapFuelStatus           = new HashMap<>();
        mapPowerStatus          = new HashMap<>();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                onResume();
            }
        });
    }

    private void setBtnFooter() {
        outClick();
        backClick(MainManagerActivity.class);
    }

    private void setInterfaceUsers() {
        if (shiftSelect.equals(ENVIRONMENT.MORNING_STATUS)){
            rvSTO.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
            rvSTO.setAdapter(new WitelManagerAdapter(stoData, mapSTOStatus.get(ENVIRONMENT.MORNING_STATUS)));

            setTemperature(ENVIRONMENT.MORNING_TEMPERATURE_STATUS);
            setFuel(ENVIRONMENT.MORNING_FUEL_STATUS);
            setPower(ENVIRONMENT.MORNING_POWER_STATUS);

        } else if (shiftSelect.equals(ENVIRONMENT.NIGHT_STATUS)){
            rvSTO.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
            rvSTO.setAdapter(new WitelManagerAdapter(stoData, mapSTOStatus.get(ENVIRONMENT.NIGHT_STATUS)));

            setTemperature(ENVIRONMENT.NIGHT_TEMPERATURE_STATUS);
            setFuel(ENVIRONMENT.NIGHT_FUEL_STATUS);
            setPower(ENVIRONMENT.NIGHT_POWER_STATUS);
        }
        contentWrap.setVisibility(View.VISIBLE);
        mDialog.dismiss();
    }

    private void setTemperature(String SHIFT){
        List<BIRData> temperature = mapTemperatureStatus.get(SHIFT);
        int count = 0;
        if (temperature != null) {
            for (BIRData data:temperature){
                if (data.getSuhu() > 23 && data.getStatus_approved()==1 && data.getRuangan()!=6 && data.getRuangan()!=8) count++;
            }
        }
        btnSuhu.setText(String.valueOf(count));
        btnSuhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(
                        new Intent(WitelActivity.this, STOSelectedActivity.class)
                                .putExtra(ENVIRONMENT.INFORMATION_TITLE, ENVIRONMENT.TEPERATURE_TITLE)
                                .putExtra(ENVIRONMENT.ID_WITEL, WITEL)
                                .putExtra(ENVIRONMENT.NAMA_WITEL, getIntent().getStringExtra(ENVIRONMENT.NAMA_WITEL))
                                .putExtra(ENVIRONMENT.SHIFT_SELECTED, IDSHIFT)
                );
            }
        });
    }

    private void setFuel(String SHIFT){
        List<FuelData> fuel = mapFuelStatus.get(SHIFT);
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
                startActivity(
                        new Intent(WitelActivity.this, STOSelectedActivity.class)
                                .putExtra(ENVIRONMENT.INFORMATION_TITLE, ENVIRONMENT.FUEL_TITLE)
                                .putExtra(ENVIRONMENT.ID_WITEL, WITEL)
                                .putExtra(ENVIRONMENT.NAMA_WITEL, getIntent().getStringExtra(ENVIRONMENT.NAMA_WITEL))
                                .putExtra(ENVIRONMENT.SHIFT_SELECTED, IDSHIFT)
                );
            }
        });
    }

    private void setPower(String SHIFT){
        List<PowerData> power = mapPowerStatus.get(SHIFT);
        int count = 0;
        if (power != null) {
            for (PowerData data: power){
                if (data.getPln() != null && data.getPln().equals(ENVIRONMENT.POWER_SELECTED_ON)
                        && data.getGenset() != null && data.getGenset().equals(ENVIRONMENT.POWER_SELECTED_ON) && data.getStatus_approved()==1) {
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
                startActivity(
                        new Intent(WitelActivity.this, STOSelectedActivity.class)
                                .putExtra(ENVIRONMENT.INFORMATION_TITLE, ENVIRONMENT.POWER_TITLE)
                                .putExtra(ENVIRONMENT.ID_WITEL, WITEL)
                                .putExtra(ENVIRONMENT.NAMA_WITEL, getIntent().getStringExtra(ENVIRONMENT.NAMA_WITEL))
                                .putExtra(ENVIRONMENT.SHIFT_SELECTED, IDSHIFT)
                );
            }
        });
    }

    @Override
    public void successRequestTimer(TimeModels dataResponse) {
        String txtWaktu, txtHari, txtTanggal;
        txtHari = dataResponse.getHari();
        txtTanggal = dataResponse.getBulan();
        txtWaktu = txtHari+", "+ txtTanggal;

        tvWitel.setText(getIntent().getStringExtra(ENVIRONMENT.NAMA_WITEL));
        tvTanggal.setText(txtWaktu);

        applicationPresenter.getStoByWitel();
    }

    @Override
    public void SucessRequestGetSTO(List<STOData> data) {
        stoData = data;
        applicationPresenter.getAreaStatusByWitel();
    }

    @Override
    public void successTemperatureRequest(List<BIRData> morning, List<BIRData> night) {
        mapTemperatureStatus.put(ENVIRONMENT.MORNING_TEMPERATURE_STATUS, morning);
        mapTemperatureStatus.put(ENVIRONMENT.NIGHT_TEMPERATURE_STATUS, night);

        applicationPresenter.getFuelStatusByWitel();
    }

    @Override
    public void successFuelRequest(List<FuelData> morning, List<FuelData> night) {
        mapFuelStatus.put(ENVIRONMENT.MORNING_FUEL_STATUS, morning);
        mapFuelStatus.put(ENVIRONMENT.NIGHT_FUEL_STATUS, night);

        applicationPresenter.getPowerStatusByWitel();
    }


    @Override
    public void successAreaRequest(List<LaporanData> morning, List<LaporanData> night) {
        mapSTOStatus.put(ENVIRONMENT.MORNING_STATUS, morning);
        mapSTOStatus.put(ENVIRONMENT.NIGHT_STATUS, night);

        applicationPresenter.getTemperatureStatusByWitel();
    }

    @Override
    public void successPowerRequest(List<PowerData> morning, List<PowerData> night) {
        mapPowerStatus.put(ENVIRONMENT.MORNING_POWER_STATUS, morning);
        mapPowerStatus.put(ENVIRONMENT.NIGHT_POWER_STATUS, night);

        setInterfaceUsers();
    }

    @Override
    public void requestFailled(String massage) {
        simpleToast(massage);
        mDialog.dismiss();
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
}
