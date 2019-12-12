package com.example.projectalpha.Activity.AdminActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectalpha.Adapter.VerifikasiAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class ViewVerifikasiLaporanActivity extends CustomCompatActivity implements ApplicationViews, ApplicationViews.ReportViews.GetReportValidator, ApplicationViews.ReportViews.GetRequestValidator, ApplicationViews.StoViews.GetRequest{

    private ProgressDialog mDialog = null;
    private SearchView searchView;
    private RecyclerView mRecyclerview;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SimpleDateFormat dateFormat;
    private ImageButton btnCalendar;

    private ApplicationPresenter applicationPresenter;
    private VerifikasiAdapter mAdapter;
    private List<LaporanData> itemVerifikasi = null;
    private List<STOData> allSTO;
    private String DATE = null;

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
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        mDialog = new ProgressDialog(ViewVerifikasiLaporanActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        applicationPresenter = new ApplicationPresenter(ViewVerifikasiLaporanActivity.this);
        mRecyclerview = findViewById(R.id.recycleViewVerifikasiLaporan);
        swipeRefreshLayout = findViewById(R.id.swlayout);
        searchView = findViewById(R.id.searchViewVerifikasiLaporan);
        btnCalendar = findViewById(R.id.btnCalendar);
    }

    private void createView(){
        mDialog.show();

        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            mDialog.dismiss();
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        } else {
            getRequest();
        }

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                onResume();
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return true;
            }
        });
        btnCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });
    }

    private void filter(String text) {
        List<Integer> stoList = new ArrayList<>();

        List<LaporanData> filteredList = new ArrayList<>();
        for (STOData item : allSTO){
            if(item.getNama().toLowerCase().contains(text.toLowerCase())){
                stoList.add(item.getId());
            }
        }
        if (!stoList.isEmpty()){
            for (int a : stoList){
                for (LaporanData item : itemVerifikasi){
                    if (a!=0 && item.getSto()==a){
                        if(item.getStatus_approved() == 0) {
                            filteredList.add(item);
                        }
                    }
                }
            }
        }
        else {
            for (LaporanData item : itemVerifikasi){
                if (item.getTanggal_shift().equals(text)){
                    if(item.getStatus_approved() == 0) {
                        filteredList.add(item);
                    }
                }
            }
        }

        if (text.equals("")){
            filteredList.clear();
            for (LaporanData item : itemVerifikasi){
                if (item.getStatus_approved() == 0){
                    filteredList.add(item);
                }
            }
        }

        mAdapter.isiVerifikasi(filteredList, allSTO);
        mAdapter.notifyDataSetChanged();
    }

    private void showDatePicker(){
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DATE = dateFormat.format(newDate.getTime());
                searchView.setQuery(DATE,true);
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void getRequest(){
        applicationPresenter.getAllSto();
    }


    @Override
    protected void onResume() {
        super.onResume();
        createView();
        mAdapter.isiVerifikasi(itemVerifikasi, allSTO);
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
    public int getIndexSto() {
        return 0;
    }

    @Override
    public void SuccessRequestGetValidator(List<LaporanData> dataVerifikasi) {
        itemVerifikasi = new ArrayList<>();
        for (LaporanData item : dataVerifikasi){
            if (item.getStatus_approved() == 0){
                itemVerifikasi.add(item);
            }
        }
        Collections.reverse(itemVerifikasi);
        mAdapter.isiVerifikasi(itemVerifikasi, allSTO);
        mAdapter.notifyDataSetChanged();
        mDialog.dismiss();
    }

    @Override
    public void SucessRequestGetSTO(List<STOData> data) {
        allSTO = data;
        applicationPresenter.requestLaporanBySTO();
    }
}
