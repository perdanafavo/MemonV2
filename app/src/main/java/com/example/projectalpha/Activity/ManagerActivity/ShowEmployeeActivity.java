package com.example.projectalpha.Activity.ManagerActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ShowEmployeeActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetRequestReport, ApplicationViews.UsersViews, ApplicationViews.UsersViews.GetRequest, ApplicationViews.ReportViews.GetReportParam {

    private final int REQUEST_CALL = 1;

    private ApplicationPresenter applicationPresenter;

    private ProgressDialog mDialog;

    private String PETUGAS, phoneNumber, Tanggal;
    private int STO, LAPORAN;

    private TextView tvNama, tvHandphone, tvWitel, tvSTO;
    private ImageView ivProfile;
    private ImageButton ibCall;
    private LinearLayout llWrap, llDial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_employee);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setVariable() {
        applicationPresenter = new ApplicationPresenter(ShowEmployeeActivity.this);

        LAPORAN = getIntent().getIntExtra(ENVIRONMENT.ID_LAPORAN, 0);
        STO = getIntent().getIntExtra(ENVIRONMENT.ID_STO, 0);
        Tanggal = getIntent().getStringExtra(ENVIRONMENT.TANGGAL_LAPORAN);

        tvNama = findViewById(R.id.txtNamaPetugas);
        tvHandphone = findViewById(R.id.txtHandphone);
        tvSTO   =   findViewById(R.id.txtNamaSTO);
        tvWitel = findViewById(R.id.txtWitel);
        ivProfile = findViewById(R.id.imgPetugas);
        ibCall  = findViewById(R.id.btnCall);
        llWrap  = findViewById(R.id.llWrap);
        llDial  = findViewById(R.id.llDial);

        Picasso.get().setLoggingEnabled(false);
        Picasso.get().setIndicatorsEnabled(false);
    }

    private void createView() {
        mDialog = new ProgressDialog(ShowEmployeeActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();

        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            mDialog.dismiss();
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        } else {
            if (LAPORAN != 0){
                applicationPresenter.getReportByID();
            } else {
                applicationPresenter.getUsersBySTO();
            }
        }
    }

    private void setBtnFooter() {
        backClick();
        homeClick(MainManagerActivity.class);
        outClick();
    }

    private void makePhoneCall(){
        if (ContextCompat.checkSelfPermission(ShowEmployeeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ShowEmployeeActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL);
        } else {
            String dial = "tel:"+phoneNumber;
            startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(dial)));
        }
    }

    @Override
    public void requestFailled(String message) {
        simpleToast(message);
        llWrap.setVisibility(View.VISIBLE);
        llDial.setVisibility(View.VISIBLE);
        mDialog.dismiss();
    }

    @Override
    public void successRequest() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            } else {
                simpleToast("Permission DENIED");
            }
        }
    }

    @Override
    public int getIndexReport() {
        return LAPORAN;
    }

    @Override
    public void SuccessRequestGetReport(List<LaporanData> data) {
        PETUGAS = data.get(0).getUsers();
        applicationPresenter.getUsersByID();
    }

    @Override
    public int getStoArea() {
        return STO;
    }

    @Override
    public String getEmployee() {
        return PETUGAS;
    }

    @Override
    public void SuccessRequestGetUsers(ArrayList<UsersData> data) {
        phoneNumber = "0"+data.get(0).getHandphone();

        tvNama.setText(data.get(0).getNama());
        tvWitel.setText(data.get(0).getNama_witel());
        tvHandphone.setText(phoneNumber);
        tvSTO.setText(data.get(0).getNama_sto());
        Picasso.get().load(ENVIRONMENT.PROFILE_IMAGE+data.get(0).getFoto())
                .resize(ivProfile.getWidth(), ivProfile.getHeight())
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(ivProfile);
        ibCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

        llWrap.setVisibility(View.VISIBLE);
        llDial.setVisibility(View.VISIBLE);
        mDialog.dismiss();
    }

    @Override
    public int getIndexWitel() {
        return 0;
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
        return Tanggal;
    }
}
