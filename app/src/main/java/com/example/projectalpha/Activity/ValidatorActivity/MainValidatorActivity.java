package com.example.projectalpha.Activity.ValidatorActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectalpha.Adapter.ValidatorAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainValidatorActivity extends CustomCompatActivity implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetReportValidator, ApplicationViews.ReportViews.GetRequestValidator {
    private SessionManager sessionManager;
    private ApplicationPresenter applicationPresenter;
    private RecyclerView mRecyclerview;
    private SwipeRefreshLayout swipeRefreshLayout, emptyRefreshLayout;
    private ValidatorAdapter mAdapter;
    private List<LaporanData> itemLaporan = null;
    private ProgressDialog mDialog = null;
    private ImageView ivUser;
    private TextView tvNama, tvHandphone, tvSTO, emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_validator);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setBtnFooter() {
        super.outClick();
    }

    private void setVariable(){
        mDialog = new ProgressDialog(MainValidatorActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        sessionManager = new SessionManager(getApplicationContext());
        applicationPresenter = new ApplicationPresenter(MainValidatorActivity.this);

        mRecyclerview = findViewById(R.id.listValidator);
        swipeRefreshLayout = findViewById(R.id.swipeValidator);
        tvNama = findViewById(R.id.txtNama);
        tvHandphone = findViewById(R.id.txtHandphone);
        tvSTO = findViewById(R.id.txtSTO);
        ivUser = findViewById(R.id.ivUser);
        emptyView = findViewById(R.id.emptyView);
        emptyRefreshLayout = findViewById(R.id.emptyswipeValidator);

        Picasso.get().setLoggingEnabled(false);
        Picasso.get().setIndicatorsEnabled(false);
    }

    private void createView(){
        String Handphone = "0" + sessionManager.getSpHandphone();
        tvNama.setText(sessionManager.getSpFullname());
        tvHandphone.setText(Handphone);
        tvSTO.setText(sessionManager.getSpNamaSTO());

        Picasso.get().load(ENVIRONMENT.PROFILE_IMAGE + sessionManager.getSpFoto())
                .resize(380, 510)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(ivUser);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                onResume();
            }
        });

        emptyRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                emptyRefreshLayout.setRefreshing(false);
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
        applicationPresenter.requestLaporanBySTO();
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
        mAdapter = new ValidatorAdapter(itemLaporan);
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
    public int getIndexReport() {
        return 0;
    }

    @Override
    public int getIndexSto() {
        return sessionManager.getSpSTO();
    }

    @Override
    public void SuccessRequestGetValidator(List<LaporanData> dataValidator) {
        itemLaporan = new ArrayList<>();
        if (dataValidator != null){
            for (LaporanData data:dataValidator){
             if (data.getStatus_approved() == 0){
                itemLaporan.add(data);
             }
             if (itemLaporan.isEmpty()) {
                 mRecyclerview.setVisibility(View.GONE);
                 swipeRefreshLayout.setVisibility(View.GONE);
                 emptyView.setVisibility(View.VISIBLE);
                 emptyRefreshLayout.setVisibility(View.VISIBLE);
             } else {
                 mRecyclerview.setVisibility(View.VISIBLE);
                 swipeRefreshLayout.setVisibility(View.VISIBLE);
                 emptyView.setVisibility(View.GONE);
                 emptyRefreshLayout.setVisibility(View.GONE);
             }
            }
        }
        mAdapter.isiValidator(itemLaporan, sessionManager.getSpNamaSTO());
        mAdapter.notifyDataSetChanged();
        mDialog.dismiss();
    }

}
