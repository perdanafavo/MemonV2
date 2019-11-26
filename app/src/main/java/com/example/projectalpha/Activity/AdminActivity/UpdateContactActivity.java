package com.example.projectalpha.Activity.AdminActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Models.SubModels.WitelData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UpdateContactActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.WitelViews.GetRequest, ApplicationViews.StoViews.GetRequest, ApplicationViews.UsersViews, ApplicationViews.UsersViews.UpdateRequest {

    private String IDPETUGAS;
    private String [] DATAPETUGAS;

    private ProgressDialog mDialog;
    private EditText editNamaKontak,editNoHP;
    private Spinner spinnerWitel;
    private Button btnUpdate;

    private ApplicationPresenter applicationPresenter;


    private List<WitelData> dataWitel;
    private List<STOData> dataSto;
    private Map<String, String> requestBodyMap = new HashMap<>();

    private int STO = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_contact);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setVariable() {
        mDialog = new ProgressDialog(UpdateContactActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        applicationPresenter = new ApplicationPresenter(UpdateContactActivity.this);

        editNamaKontak = findViewById(R.id.editNamaKontak);
        editNoHP = findViewById(R.id.editNoHP);
        spinnerWitel = findViewById(R.id.spinnerWitel);
        btnUpdate = findViewById(R.id.btnUpdate);

        IDPETUGAS   = getIntent().getStringExtra(ENVIRONMENT.ID_PETUGAS);
        DATAPETUGAS = getIntent().getStringArrayExtra(ENVIRONMENT.DATA_PETUGAS);
    }

    private void createView() {
        applicationPresenter.getWitel();
        editNamaKontak.setText(DATAPETUGAS[0]);
        editNoHP.setText(DATAPETUGAS[1]);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateContact();
            }
        });
    }

    private void updateContact() {
        requestBodyMap.clear();
        if (!editNamaKontak.getText().toString().matches("") || !editNoHP.getText().toString().matches("")){
            if (!editNamaKontak.getText().toString().equals(DATAPETUGAS[0]))requestBodyMap.put("nama", editNamaKontak.getText().toString());
            if (!editNoHP.getText().toString().equals(DATAPETUGAS[1]))requestBodyMap.put("handphone", editNoHP.getText().toString());
            if (STO!=0) requestBodyMap.put("sto", String.valueOf(STO));
            applicationPresenter.updateUsers();
        } else {
            simpleToast("Data masih kosong");
        }
    }

    private void setSpinnerWitel(){
        List<String> itemWitel = new ArrayList<>();
        itemWitel.add(0, "PILIH WITEL");
        if (dataWitel != null){
            for (WitelData data:dataWitel){
                itemWitel.add(data.getId(), data.getNama());
            }
        }

        ArrayAdapter<String> arrayAdapterWitel = new ArrayAdapter<>(this, R.layout.spinner_item, itemWitel);
        arrayAdapterWitel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerWitel.setAdapter(arrayAdapterWitel);
        spinnerWitel.setSelection(Integer.parseInt(DATAPETUGAS[2]));
        spinnerWitel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (STOData data:dataSto){
                    if (data.getWitel() == parent.getItemIdAtPosition(position)){
                        STO = data.getId();
                        break;
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setBtnFooter() {
        backClick();
        homeClick(MainAdminActivity.class);
        outClick();
    }

    @Override
    public void requestFailled(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    @Override
    public void successRequest() {
        mDialog.dismiss();
        new AlertDialog.Builder(UpdateContactActivity.this)
                .setMessage("Berhasil merubah data")
                .setTitle("Informasi ubah")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        simpleIntent(UpdateMenuContactActivity.class);
                    }
                }).create().show();
    }

    @Override
    public void SuccessRequestGetWitel(List<WitelData> data) {
        dataWitel = data;
        applicationPresenter.getAllSto();
    }

    @Override
    public void SucessRequestGetSTO(List<STOData> data) {
        dataSto = data;
        setSpinnerWitel();
        mDialog.dismiss();
    }

    @Override
    public int getStoArea() {
        return 0;
    }

    @Override
    public String getEmployee() {
        return IDPETUGAS;
    }

    @Override
    public Map<String, String> getRequestMapBody() {
        return requestBodyMap;
    }
}
