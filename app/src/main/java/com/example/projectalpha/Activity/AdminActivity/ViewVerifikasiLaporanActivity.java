package com.example.projectalpha.Activity.AdminActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectalpha.Adapter.VerifikasiAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewVerifikasiLaporanActivity extends CustomCompatActivity implements ApplicationViews, ApplicationViews.ReportViews.GetReportParam, ApplicationViews.ReportViews.GetRequestReport, ApplicationViews.StatusReport{

    private ProgressDialog mDialog=null;
    private SessionManager sessionManager;
    private ApplicationPresenter applicationPresenter;
    private RecyclerView mRecyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private VerifikasiAdapter mAdapter;
    private List<LaporanData> itemVerifikasi = null;
    private int WITEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_verifikasi_laporan);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setBtnFooter() {
        super.outClick();
        homeClick(MainAdminActivity.class);
    }


    private void setVariable(){
        mDialog = new ProgressDialog(ViewVerifikasiLaporanActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        sessionManager = new SessionManager(getApplicationContext());
        WITEL = getIntent().getIntExtra(ENVIRONMENT.ID_WITEL, 0);
        applicationPresenter = new ApplicationPresenter(ViewVerifikasiLaporanActivity.this);


        mRecyclerview = findViewById(R.id.recycleViewVerifikasiLaporan);
        swipeRefreshLayout = findViewById(R.id.swlayout);
    }

    private void createView(){

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                onResume();
            }
        });

        mDialog.show();
        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            mDialog.dismiss();
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        } else {
            getRequest();
        }
    }

    private void getRequest(){
        applicationPresenter.requestLaporan();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createView();
        mAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter = new VerifikasiAdapter(itemVerifikasi);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(mAdapter);
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
    public int getIndexWitel() {
        return WITEL;
    }

    @Override
    public void SuccessRequestGetReport(List<LaporanData> dataVerifikasi) {
        itemVerifikasi = new ArrayList<>();
        if (dataVerifikasi != null){
            System.out.println("dataverif null");
            for (LaporanData data:dataVerifikasi) {
                System.out.println("data");
                itemVerifikasi.add(data);

            }
        }
        Collections.reverse(itemVerifikasi);
        mAdapter.notifyDataSetChanged();
        mDialog.dismiss();

    }
}
