package com.example.projectalpha.Activity.ValidatorActivity;

import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectalpha.Activity.AdminActivity.UpdateMenuUserActivity;
import com.example.projectalpha.Adapter.ItemUpdateAdapter;
import com.example.projectalpha.Adapter.ValidatorAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.example.projectalpha.Views.UpdateMenuUserViews;

import java.util.ArrayList;
import java.util.List;

public class MainValidatorActivity extends CustomCompatActivity implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetReportValidator, ApplicationViews.ReportViews.GetRequestValidator {
    private SessionManager sessionManager;
    private ApplicationPresenter applicationPresenter;
    private RecyclerView mRecyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ValidatorAdapter mAdapter;
    private List<LaporanData> itemLaporan = null;
    private ProgressDialog mDialog = null;
    private TextView txtNama, txtHandphone, txtSTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_validator);
        setVariable();
        createView();
        setBtnFooter();
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
        txtNama = findViewById(R.id.txtNama);
        txtHandphone = findViewById(R.id.txtHandphone);
        txtSTO = findViewById(R.id.txtSTO);

        txtNama.setText(sessionManager.getSpFullname());
        txtHandphone.setText("0"+sessionManager.getSpHandphone());
        txtSTO.setText(sessionManager.getSpNamaSTO());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                onResume();
            }
        });
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
    private void getRequest(){
        applicationPresenter.requestLaporanBySTO();
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
//             if (data.getStatus_approved() == 0){
//                 System.out.println(dataValidator.toString());
                itemLaporan.add(data);
 //            }
            }
        }
        mAdapter.isiValidator(itemLaporan, sessionManager.getSpNamaSTO());
        mAdapter.notifyDataSetChanged();
        mDialog.dismiss();
    }

}
