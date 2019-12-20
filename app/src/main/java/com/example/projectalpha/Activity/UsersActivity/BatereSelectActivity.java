package com.example.projectalpha.Activity.UsersActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.projectalpha.Activity.UsersActivity.FormActivity.BIRActivity;
import com.example.projectalpha.Activity.UsersActivity.FormActivity.KontakPentingActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.ReportStatusData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

public class BatereSelectActivity extends CustomCompatActivity implements ApplicationViews, ApplicationViews.ReportViews.GetReportStatusRequest, ApplicationViews.ReportViews {

    private ApplicationPresenter applicationPresenter;
    private ProgressDialog mDialog;
    private Button btnBatereBasah, btnBatereKering;
    private ImageView batereBasahStatus, batereKeringStatus;
    private int IDLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batere_select);
        setVariable();
        createSelectBatere();
        setBtnFooter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mDialog.show();
        applicationPresenter.getStatus();
    }

    private void setVariable(){
        applicationPresenter = new ApplicationPresenter(BatereSelectActivity.this);
        mDialog = new ProgressDialog(BatereSelectActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        btnBatereBasah = findViewById(R.id.btnBatereBasah);
        btnBatereKering = findViewById(R.id.btnBatereKering);
        batereBasahStatus = findViewById(R.id.batereBasahStatus);
        batereKeringStatus = findViewById(R.id.batereKeringStatus);
        IDLaporan = getIntent().getIntExtra(ENVIRONMENT.ID_LAPORAN, 0);
    }

    private void createSelectBatere(){
        btnBatereKering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BatereSelectActivity.this, BIRActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan)
                        .putExtra(ENVIRONMENT.RUANGAN, 4)
                );
            }
        });

        btnBatereBasah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BatereSelectActivity.this, BIRActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan)
                        .putExtra(ENVIRONMENT.RUANGAN, 8)
                );
            }
        });
    }

    private void setBtnFooter(){
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
    public int getIndexReport() {
        return IDLaporan;
    }

    @Override
    public void SuccessRequestStatus(ReportStatusData dataResponse) {
        mDialog.dismiss();

        if (dataResponse.getStatus_batere() == 1) batereKeringStatus.setImageResource(R.drawable.ic_ok);
        else batereKeringStatus.setImageResource(R.drawable.ic_warning);

        if (dataResponse.getStatus_batere_basah() == 1) batereBasahStatus.setImageResource(R.drawable.ic_ok);
        else batereBasahStatus.setImageResource(R.drawable.ic_warning);
    }
}
