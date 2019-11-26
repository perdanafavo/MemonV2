package com.example.projectalpha.Activity.UsersActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.projectalpha.Activity.UsersActivity.FormActivity.BbmActivity;
import com.example.projectalpha.Activity.UsersActivity.FormActivity.CatatanActivity;
import com.example.projectalpha.Activity.UsersActivity.FormActivity.CatuanActivity;
import com.example.projectalpha.Activity.UsersActivity.FormActivity.KondisiUmumActivity;
import com.example.projectalpha.Activity.UsersActivity.FormActivity.KontakPentingActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.ReportStatusData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectFormActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetReportStatusRequest, ApplicationViews.ReportViews.GetReportParam, ApplicationViews.ReportViews.PostRequest, ApplicationViews.ReportViews.GetRequestReport {

    private ApplicationPresenter applicationPresenter;

    private ProgressDialog mDialog;

    private ImageButton btnKondisi, btnCatuan, btnBBM, btnContact, btnBIRSuhu, btnCatatan;
    private ImageView umumStatus, catuanStatus, bbmStatus, BIRStatus;

    private boolean status = false;

    private String Users;
    private int STO, Witel, Shift, IDLaporan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_form);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (status) {
            mDialog.show();
            applicationPresenter.getStatus();
        }
    }


    private void setVariable(){
        mDialog = new ProgressDialog(SelectFormActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        applicationPresenter = new ApplicationPresenter(SelectFormActivity.this);
        applicationPresenter.deleteReportMonth();
        SessionManager sessionManager = new SessionManager(getApplicationContext());

        btnKondisi = findViewById(R.id.btnKondisi);

        btnCatuan = findViewById(R.id.btnCatuan);
        btnBBM = findViewById(R.id.btnBBM);
        btnContact = findViewById(R.id.btnContact);
        btnBIRSuhu = findViewById(R.id.btnBIRSuhu);
        btnCatatan = findViewById(R.id.btnCatatan);

        umumStatus = findViewById(R.id.umumStatus);
        catuanStatus = findViewById(R.id.catuanStatus);
        bbmStatus = findViewById(R.id.bbmStatus);
        BIRStatus = findViewById(R.id.BIRStatus);

        Users   = sessionManager.getSpIduser();
        STO     = sessionManager.getSpSTO();
        Witel   = sessionManager.getSpWitel();
        Shift   = getIntent().getIntExtra("SHIFT", 0);
    }

    private void createView(){
        mDialog.show();
        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            mDialog.dismiss();
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        } else {
            getRequest();
        }

    }

    private void setBtnFooter() {
        super.backClick(MainUserActivity.class);
        super.outClick();
    }

    private void getRequest(){
        applicationPresenter.requestLaporan();
    }

    private void MainMenuForm(){
        btnKondisi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectFormActivity.this, KondisiUmumActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan));
            }
        });

        btnCatuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectFormActivity.this, CatuanActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan));
            }
        });

        btnBBM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectFormActivity.this, BbmActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan));
            }
        });

        btnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectFormActivity.this, KontakPentingActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan));
            }
        });

        btnBIRSuhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectFormActivity.this, BirSuhuSelectActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan));
            }
        });

        btnCatatan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectFormActivity.this, CatatanActivity.class)
                        .putExtra(ENVIRONMENT.ID_LAPORAN, IDLaporan));
            }
        });
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
    public int getIndexWitel() {
        return Witel;
    }
    @Override
    public int getIndexShift() {
        return Shift;
    }
    @Override
    public int getIndexSto() {
        return STO;
    }

    @Override
    public String getDateReport() {
        return null;
    }

    @Override
    public void SuccessRequestStatus(ReportStatusData dataResponse) {
        mDialog.dismiss();
        if (dataResponse.getStatus_umum() == 1) umumStatus.setImageResource(R.drawable.ic_ok);
        else umumStatus.setImageResource(R.drawable.ic_warning);

        if (dataResponse.getStatus_catuan() == 1) catuanStatus.setImageResource(R.drawable.ic_ok);
        else catuanStatus.setImageResource(R.drawable.ic_warning);

        if (dataResponse.getStatus_bbm() == 1) bbmStatus.setImageResource(R.drawable.ic_ok);
        else bbmStatus.setImageResource(R.drawable.ic_warning);

        if (dataResponse.getStatus_akses() == 1 && dataResponse.getStatus_batere() == 1 && dataResponse.getStatus_genset() == 1
                && dataResponse.getStatus_rectifier() == 1 && dataResponse.getStatus_sentral() == 1 && dataResponse.getStatus_transmisi() == 1) BIRStatus.setImageResource(R.drawable.ic_ok);
        else BIRStatus.setImageResource(R.drawable.ic_warning);
    }

    @Override
    public Map<String, String> getRequestMapBody() {
        Map<String, String> MapRequestBody = new HashMap<>();
        MapRequestBody.put("users", this.Users);
        MapRequestBody.put("witel", String.valueOf(this.Witel));
        MapRequestBody.put("sto", String.valueOf(this.STO));
        MapRequestBody.put("shift", String.valueOf(this.Shift));
        return MapRequestBody;
    }

    @Override
    public void SuccessPostReport(List<LaporanData> data) {
        IDLaporan = data.get(0).getId();
        applicationPresenter.getStatus();
        status = true;
        MainMenuForm();
        mDialog.dismiss();
    }

    @Override
    public void SuccessRequestGetReport(List<LaporanData> data) {
        if (data == null){
            applicationPresenter.postLaporan();
        } else {
            status = true;
            IDLaporan = data.get(0).getId();
            applicationPresenter.getStatus();
            MainMenuForm();
            mDialog.dismiss();
        }
    }
}
