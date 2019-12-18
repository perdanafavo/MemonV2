package com.example.projectalpha.Activity.UsersActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.projectalpha.Activity.UsersActivity.FormActivity.BIRActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.ReportStatusData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

public class BirSuhuSelectActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.ReportViews.GetReportStatusRequest, ApplicationViews.ReportViews {

    private ApplicationPresenter applicationPresenter;

    private ProgressDialog mDialog;

    private Button btnSentral, btnTransmisi, btnRectifier, btnBatere, btnAkses, btnGenset, btnOlo;
    private ImageView sentralStatus,transmisiStatus, rectifierStatus, batereStatus, aksesStatus, gensetStatus, oloStatus;

    private int IDLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bir_suhu_select);

        setVariable();
        createMenuBIR();
        SetBtnFooter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDialog.show();
        applicationPresenter.getStatus();
    }

    private void setVariable(){
        mDialog = new ProgressDialog(BirSuhuSelectActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        applicationPresenter = new ApplicationPresenter(BirSuhuSelectActivity.this);

        btnSentral = findViewById(R.id.btnSentral);
        btnTransmisi = findViewById(R.id.btnTransmisi);
        btnRectifier = findViewById(R.id.btnRectifier);
        btnBatere = findViewById(R.id.btnBatere);
        btnAkses = findViewById(R.id.btnAkses);
        btnGenset = findViewById(R.id.btnGenset);
        btnOlo = findViewById(R.id.btnOlo);

        sentralStatus = findViewById(R.id.sentralStatus);
        batereStatus = findViewById(R.id.batereStatus);
        rectifierStatus = findViewById(R.id.rectifierStatus);
        aksesStatus = findViewById(R.id.aksesStatus);
        transmisiStatus = findViewById(R.id.transmisiStatus);
        gensetStatus = findViewById(R.id.gensetStatus);
        oloStatus = findViewById(R.id.oloStatus);

        IDLaporan = getIntent().getIntExtra(ENVIRONMENT.ID_LAPORAN, 0);
    }

    private void createMenuBIR() {
        btnSentral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BirSuhuSelectActivity.this, BIRActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan)
                        .putExtra(ENVIRONMENT.RUANGAN, 1)
                );
            }
        });

        btnTransmisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BirSuhuSelectActivity.this, BIRActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan)
                        .putExtra(ENVIRONMENT.RUANGAN, 2)
                );
            }
        });

        btnRectifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BirSuhuSelectActivity.this, BIRActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan)
                        .putExtra(ENVIRONMENT.RUANGAN, 3)
                );
            }
        });

        btnBatere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BirSuhuSelectActivity.this, BIRActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan)
                        .putExtra(ENVIRONMENT.RUANGAN, 4)
                );
            }
        });

        btnAkses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BirSuhuSelectActivity.this, BIRActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan)
                        .putExtra(ENVIRONMENT.RUANGAN, 5)
                );
            }
        });

        btnGenset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BirSuhuSelectActivity.this, BIRActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan)
                        .putExtra(ENVIRONMENT.RUANGAN, 6)
                );
            }
        });
    }

    private void SetBtnFooter(){
        homeClick(MainUserActivity.class);
        backClick();
    }

    @Override
    public void requestFailled(String message) {
        simpleToast(message);
        mDialog.dismiss();
    }
    @Override
    public void successRequest() {

    }
    @Override
    public void SuccessRequestStatus(ReportStatusData dataResponse) {
        mDialog.dismiss();

        if (dataResponse.getStatus_sentral() == 1) sentralStatus.setImageResource(R.drawable.ic_ok);
        else sentralStatus.setImageResource(R.drawable.ic_warning);

        if (dataResponse.getStatus_rectifier() == 1) rectifierStatus.setImageResource(R.drawable.ic_ok);
        else rectifierStatus.setImageResource(R.drawable.ic_warning);

        if (dataResponse.getStatus_batere() == 1) batereStatus.setImageResource(R.drawable.ic_ok);
        else batereStatus.setImageResource(R.drawable.ic_warning);

        if (dataResponse.getStatus_transmisi() == 1) transmisiStatus.setImageResource(R.drawable.ic_ok);
        else transmisiStatus.setImageResource(R.drawable.ic_warning);

        if (dataResponse.getStatus_akses() == 1) aksesStatus.setImageResource(R.drawable.ic_ok);
        else aksesStatus.setImageResource(R.drawable.ic_warning);

        if (dataResponse.getStatus_genset() == 1) gensetStatus.setImageResource(R.drawable.ic_ok);
        else gensetStatus.setImageResource(R.drawable.ic_warning);
    }

    @Override
    public int getIndexReport() {
        return IDLaporan;
    }
}
