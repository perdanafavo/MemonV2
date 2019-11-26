package com.example.projectalpha.Activity.UsersActivity.FormActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.projectalpha.Activity.UsersActivity.MainUserActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatuanActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetPowerRequest, ApplicationViews.ReportViews.PostRequest {

    private int IDLaporan;

    private RadioGroup radioPLN, radioGenset;
    private Button btnSimpan;

    private ApplicationPresenter applicationPresenter;

    private PowerData dataReport;
    private ProgressDialog mDialog;

    private Map<String, String> mapRequest = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catuan);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setVariable(){
        IDLaporan = getIntent().getIntExtra(ENVIRONMENT.ID_LAPORAN, 0);

        applicationPresenter = new ApplicationPresenter(CatuanActivity.this);

        radioGenset = findViewById(R.id.radioGenset);
        radioPLN = findViewById(R.id.radioPLN);
        btnSimpan = findViewById(R.id.btnSimpan);

        mDialog = new ProgressDialog(CatuanActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
    }

    private void setBtnFooter() {
        super.homeClick(MainUserActivity.class);
        super.backClick();
    }

    private void createView() {
        mDialog.show();
        applicationPresenter.getPower();

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postReport();
            }
        });
    }

    private void postReport() {
        mDialog.show();
        mapRequest.clear();
        mapRequest.put("laporan", String.valueOf(IDLaporan));

        RadioButton btnPLN = findViewById(radioPLN.getCheckedRadioButtonId());
        String pln = btnPLN.getText().toString();
        if (dataReport.getPln() == null || !dataReport.getPln().equals(pln))mapRequest.put("pln", pln);

        RadioButton btnGenset = findViewById(radioGenset.getCheckedRadioButtonId());
        String genset = btnGenset.getText().toString();
        if (dataReport.getGenset() == null || !dataReport.getGenset().equals(genset)) mapRequest.put("genset", genset);

        applicationPresenter.putPower();
    }

    @Override
    public void requestFailled(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    @Override
    public void successRequest() {
        mDialog.dismiss();
        new AlertDialog.Builder(CatuanActivity.this)
                .setMessage("Laporan berhasil ditambahkan")
                .setTitle("Informasi Laporan")
                .setCancelable(false)
                .setPositiveButton("Selesai", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();
    }

    @Override
    public int getIndexReport() {
        return IDLaporan;
    }

    @Override
    public void SuccessRequestPower(PowerData data) {
        dataReport = data;

        String pln, genset;

        if (data.getPln()==null) pln="Laporan Kosong";
        else pln=data.getPln();

        if (data.getGenset()==null) genset="Laporan Kosong";
        else genset=data.getGenset();

        switch (pln){
            case "ON":
                radioPLN.check(R.id.radioPLNON);
                break;
            case "OFF":
                radioPLN.check(R.id.radioPLNOFF);
                break;
            default:
                radioPLN.check(R.id.radioPLNON);
                break;
        }

        switch (genset){
            case "ON":
                radioGenset.check(R.id.radioGensetON);
                break;
            case "OFF":
                radioGenset.check(R.id.radioGensetOFF);
                break;
            default:
                radioGenset.check(R.id.radioGensetON);
                break;
        }
        mDialog.dismiss();
    }

    @Override
    public Map<String, String> getRequestMapBody() {
        return mapRequest;
    }

    @Override
    public void SuccessPostReport(List<LaporanData> data) {

    }
}
