package com.example.projectalpha.Activity.UsersActivity.FormActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.projectalpha.Activity.UsersActivity.MainUserActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.OthersData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatatanActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetOthersRequest, ApplicationViews.ReportViews.PostRequest {

    private int IDLaporan;

    private ProgressDialog mDialog;
    private ApplicationPresenter applicationPresenter;

    private OthersData othersData;
    private Map<String, String> mapRequest = new HashMap<>();

    private EditText edCatatan;
    private Button btnSimpan;
    private SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catatan);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setVariable(){
        IDLaporan = getIntent().getIntExtra(ENVIRONMENT.ID_LAPORAN, 0);
        applicationPresenter = new ApplicationPresenter(CatatanActivity.this);

        edCatatan = findViewById(R.id.editCatatan);
        btnSimpan = findViewById(R.id.btnSimpan);
        sessionManager = new SessionManager(getApplicationContext());
        mDialog = new ProgressDialog(CatatanActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
    }

    private void setBtnFooter() {
        super.homeClick(MainUserActivity.class);
        super.backClick();
    }

    private void createView(){
        mDialog.show();

        applicationPresenter.getOthersReport();

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

        String catatan = "";
        if (!edCatatan.getText().toString().matches("")) catatan = edCatatan.getText().toString();
        if (sessionManager.getSpPrivileges()==3){
            if (othersData.getCatatan() == null || !othersData.getCatatan().equals(catatan))mapRequest.put("catatan", catatan);
        }
        else if(sessionManager.getSpPrivileges()==4){
            if (othersData.getCatatan_validator() == null || !othersData.getCatatan_validator().equals(catatan))mapRequest.put("catatan_validator", catatan);
        }
        else if(sessionManager.getSpPrivileges()==2){
            if (othersData.getCatatan_manager() == null || !othersData.getCatatan_manager().equals(catatan))mapRequest.put("catatan_manager", catatan);
        }
        applicationPresenter.putOthers();
    }


    @Override
    public int getIndexReport() {
        return IDLaporan;
    }

    @Override
    public void SuccessRequestOthers(OthersData data) {
        mDialog.dismiss();
        othersData = data;
        if (sessionManager.getSpPrivileges()==3){
            if (data.getCatatan()!=null) edCatatan.setText(data.getCatatan());
        }
        else if(sessionManager.getSpPrivileges()==4){
            if (data.getCatatan_validator()!=null) edCatatan.setText(data.getCatatan_validator());
        }
        else if(sessionManager.getSpPrivileges()==2){
            if (data.getCatatan_manager()!=null) edCatatan.setText(data.getCatatan_manager());
        }
    }

    @Override
    public Map<String, String> getRequestMapBody() {
        return mapRequest;
    }

    @Override
    public void SuccessPostReport(List<LaporanData> data) {

    }

    @Override
    public void requestFailled(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    @Override
    public void successRequest() {
        mDialog.dismiss();
        new AlertDialog.Builder(CatatanActivity.this)
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
}
