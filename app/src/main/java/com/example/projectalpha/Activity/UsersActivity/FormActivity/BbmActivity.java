package com.example.projectalpha.Activity.UsersActivity.FormActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.projectalpha.Activity.UsersActivity.MainUserActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.ImageHandle;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class BbmActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetFuelRequest, ApplicationViews.UploadWithImage, ApplicationViews.ReportViews.PostRequest {

    private int IDLaporan;

    private EditText tvTankiBulanan;
    private TextView tvKapasitas;
    private ImageButton btnFoto;
    private Button btnSimpan;

    private ApplicationPresenter applicationPresenter;
    private SessionManager sessionManager;

    private FuelData dataReport;
    private ProgressDialog mDialog;
    private ImageHandle imageHandle;

    private File file = null;
    private Map<String, String> mapRequest = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbm);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENVIRONMENT.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null){
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap compress = imageHandle.bipmapCompress(imageBitmap);
                file = imageHandle.fileCreate(compress);
                btnFoto.setImageBitmap(imageBitmap);
                btnFoto.setBackgroundResource(R.color.colorGreyLight);
            }
        }
    }

    private void setVariable(){
        imageHandle = new ImageHandle(BbmActivity.this);
        IDLaporan = getIntent().getIntExtra(ENVIRONMENT.ID_LAPORAN, 0);

        applicationPresenter = new ApplicationPresenter(BbmActivity.this);
        sessionManager = new SessionManager(BbmActivity.this);

        tvKapasitas = findViewById(R.id.tvKapasitas);
        tvTankiBulanan = findViewById(R.id.editTankiBulanan);

        btnFoto = findViewById(R.id.btnFoto);
        btnSimpan = findViewById(R.id.btnSimpan);

        mDialog = new ProgressDialog(BbmActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
    }

    private void createView() {
        mDialog.show();
        applicationPresenter.getFuel();

        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHandle.takeImage();
            }
        });
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

        String tanki = "0";
        if (!tvTankiBulanan.getText().toString().matches("")) tanki = tvTankiBulanan.getText().toString();
        if (dataReport.getTanki_bulanan() != Integer.parseInt(tanki)) mapRequest.put("tanki_bulanan", tanki);

        if (file != null){
            applicationPresenter.updateImage(false);
        } else {
            if (dataReport.getFoto() != null){
                applicationPresenter.putFuel();
            }else {
                mDialog.dismiss();
                simpleToast("Foto belum ditambahkan");
            }
        }
    }

    private void setBtnFooter() {
        super.homeClick(MainUserActivity.class);
        super.backClick();
    }

    @Override
    public int getIndexReport() {
        return IDLaporan;
    }

    @Override
    public void SuccessRequestFuel(FuelData data) {
        dataReport = data;

        if (data.getFoto() != null) {
            Picasso.get().load(ENVIRONMENT.FOTO_URL+data.getFoto())
                    .error(R.drawable.ic_take_picture)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(btnFoto);
            btnFoto.setBackgroundResource(R.color.colorGreyLight);
        }

        tvKapasitas.setText(String.valueOf(data.getKapasitas()));
        tvTankiBulanan.setText(String.valueOf(data.getTanki_bulanan()));

        mDialog.dismiss();
    }

    @Override
    public Map<String, String> getRequestMapBody() {
        return mapRequest;
    }

    @Override
    public void SuccessPostReport(List<LaporanData> data) {

    }

    @Override
    public Map<String, RequestBody> GetRequestBody() {
        Map<String, RequestBody> MapRequestBody = new HashMap<>();
        MapRequestBody.put("id", RequestBody.create(MultipartBody.FORM, sessionManager.getSpIduser()));
        MapRequestBody.put("sto", RequestBody.create(MultipartBody.FORM, sessionManager.getSpNamaSTO()));
        return MapRequestBody;
    }

    @Override
    public MultipartBody.Part GetMultiPart(boolean index) {
        return imageHandle.convertMultiparFile(file);
    }

    @Override
    public void successPostImage(String nama, boolean index) {
        mapRequest.put("foto", nama);
        applicationPresenter.putFuel();
    }

    @Override
    public void requestFailled(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    @Override
    public void successRequest() {
        mDialog.dismiss();
        new AlertDialog.Builder(BbmActivity.this)
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
