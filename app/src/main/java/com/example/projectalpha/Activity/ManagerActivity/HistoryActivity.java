package com.example.projectalpha.Activity.ManagerActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectalpha.Adapter.WitelManagerAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Models.SubModels.WitelData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HistoryActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.WitelViews.GetRequest, ApplicationViews.StoViews.GetRequest, ApplicationViews.ReportViews.GetReportParam, ApplicationViews.ReportViews.GetRequestReport {

    private SimpleDateFormat dateFormatterShow, dateFormat;
    private ProgressDialog mDialog;

    private ApplicationPresenter applicationPresenter;

    private TextView dateInput;
    private Spinner spinnerWitel, spinnerShift;
    private Button btnLihatHistory;
    private ImageButton ibDatePicker;
    private RecyclerView rvSTO;

    private String DATE = null;
    private int SHIFT = 0, WITEL = 0;

    private List<STOData> dataSTO;
    private List<LaporanData> dataReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setVariable(){
        applicationPresenter = new ApplicationPresenter(HistoryActivity.this);

        dateFormatterShow = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        dateInput = findViewById(R.id.dateInput);
        spinnerWitel = findViewById(R.id.spinnerWitel);
        spinnerShift = findViewById(R.id.spinnerShift);
        btnLihatHistory = findViewById(R.id.btnLihatHistory);
        ibDatePicker = findViewById(R.id.datePicker);
        rvSTO = findViewById(R.id.rvSTO);
    }

    private void createView() {
        mDialog = new ProgressDialog(HistoryActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();

        ibDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        btnLihatHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog.show();
                rvSTO.setVisibility(View.INVISIBLE);
                if (DATE != null){
                    if (WITEL != 0 && SHIFT != 0){
                        applicationPresenter.getStoByWitel();
                    }else if (WITEL == 0){
                        mDialog.dismiss();
                        simpleToast("Witel belum dipilih");
                    } else {
                        mDialog.dismiss();
                        simpleToast("Shift belum dipilih");
                    }
                } else {
                    mDialog.dismiss();
                    simpleToast("Pilih tanggal");
                }
            }
        });

        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            mDialog.dismiss();
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        } else {
            applicationPresenter.getWitel();
        }
    }

    private void setBtnFooter() {
        backClick(MainManagerActivity.class);
        outClick();
    }

    private void showDatePicker(){
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                DATE = dateFormat.format(newDate.getTime());
                dateInput.setText(dateFormatterShow.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void showSTOList(){
        rvSTO.setVisibility(View.VISIBLE);
        rvSTO.setLayoutManager(new GridLayoutManager(HistoryActivity.this, 5));
        rvSTO.setAdapter(new WitelManagerAdapter(dataSTO, dataReport, DATE));
        mDialog.dismiss();
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
    public int getIndexWitel() {
        return WITEL;
    }

    @Override
    public int getIndexShift() {
        return SHIFT;
    }

    @Override
    public int getIndexSto() {
        return 0;
    }

    @Override
    public String getDateReport() {
        return DATE;
    }

    @Override
    public void SuccessRequestGetWitel(List<WitelData> data) {

        List<String> listItemShift = new ArrayList<>();
        listItemShift.add(0, "PILIH SHIFT");
        listItemShift.add(1, "PAGI");
        listItemShift.add(2, "MALAM");

        ArrayAdapter<String> arrayAdapterShift = new ArrayAdapter<>(HistoryActivity.this, R.layout.spinner_item, listItemShift);
        arrayAdapterShift.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerShift.setAdapter(arrayAdapterShift);
        spinnerShift.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SHIFT = (int) parent.getItemIdAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        List<String> listItemWitel = new ArrayList<>();
        listItemWitel.add(0, "PILIH WITEL");
        for (WitelData item:data){
            listItemWitel.add(item.getId(), item.getNama());
        }

        ArrayAdapter<String> arrayAdapterWitel = new ArrayAdapter<>(HistoryActivity.this, R.layout.spinner_item, listItemWitel);
        arrayAdapterWitel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        spinnerWitel.setAdapter(arrayAdapterWitel);
        spinnerWitel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WITEL = (int) parent.getItemIdAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mDialog.dismiss();
    }

    @Override
    public void SucessRequestGetSTO(List<STOData> data) {
        dataSTO = data;
        applicationPresenter.requestReportByDate();
    }

    @Override
    public void SuccessRequestGetReport(List<LaporanData> data) {
        dataReport = data;
        showSTOList();
    }
}
