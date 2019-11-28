package com.example.projectalpha.Activity.UsersActivity.FormActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.projectalpha.Activity.UsersActivity.MainUserActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.ImageHandle;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.KondisiUmumData;
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

public class KondisiUmumActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetGeneralRequest, ApplicationViews.UploadWithImage, ApplicationViews.ReportViews.PostRequest {

    private int IDLaporan;

    private RadioGroup radioCuaca, radioPompa, radioGenangan;
    private Button btnSimpan;
    private ImageButton btnFoto;

    private ApplicationPresenter applicationPresenter;
    private SessionManager sessionManager;

    private KondisiUmumData dataReport;
    private ProgressDialog mDialog;
    private ImageHandle imageHandle;

    private File file = null;
    private Map<String, String> mapRequest = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kondisi_umum);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENVIRONMENT.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap compress = imageHandle.bipmapCompress(imageBitmap);
                file = imageHandle.fileCreate(compress);
                btnFoto.setImageBitmap(compress);
                btnFoto.setBackgroundResource(R.color.colorGreyLight);
            }
        }
    }

    private void setVariable(){
        imageHandle = new ImageHandle(KondisiUmumActivity.this);
        IDLaporan = getIntent().getIntExtra(ENVIRONMENT.ID_LAPORAN, 0);
        applicationPresenter = new ApplicationPresenter(KondisiUmumActivity.this);
        sessionManager = new SessionManager(KondisiUmumActivity.this);

        radioCuaca = findViewById(R.id.radioCuaca);
        radioPompa = findViewById(R.id.radioPompa);
        radioGenangan = findViewById(R.id.radioGenangan);
        btnSimpan = findViewById(R.id.btnSimpanKondisi);
        btnFoto = findViewById(R.id.btnFotoKondisi);

        mDialog = new ProgressDialog(KondisiUmumActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
    }

    private void createView(){
        mDialog.show();
        applicationPresenter.getGeneral();

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

    private void postReport(){
        mDialog.show();
        mapRequest.clear();
        mapRequest.put("laporan", String.valueOf(IDLaporan));

        RadioButton btnCuaca = findViewById(radioCuaca.getCheckedRadioButtonId());
        String cuaca = btnCuaca.getText().toString();
        if (dataReport.getCuaca() == null || !dataReport.getCuaca().equals(cuaca))mapRequest.put("cuaca", cuaca);

        RadioButton btnGenangan = findViewById(radioGenangan.getCheckedRadioButtonId());
        String genangan = btnGenangan.getText().toString();
        if (dataReport.getGenangan_air() == null || !dataReport.getGenangan_air().equals(genangan)) mapRequest.put("genangan_air", genangan);

        RadioButton btnPompa = findViewById(radioPompa.getCheckedRadioButtonId());
        String pompa = btnPompa.getText().toString();
        if (dataReport.getPompa_air()==null || !dataReport.getPompa_air().equals(pompa))mapRequest.put("pompa_air", pompa);

        if (file != null){
            applicationPresenter.updateImage(false);
        } else {
            if (dataReport.getFoto() != null){
                applicationPresenter.putGeneral();
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
    public void requestFailled(String message) {
        mDialog.dismiss();
        simpleToast(message);
    }

    @Override
    public void successRequest() {
        mDialog.dismiss();
        new AlertDialog.Builder(KondisiUmumActivity.this)
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

    @Override
    public int getIndexReport() {
        return IDLaporan;
    }

    @Override
    public void SuccessRequestGeneral(KondisiUmumData data) {
        dataReport = data;

        String cuaca, pompa, genangan;

        if (data.getCuaca()==null) cuaca="Laporan Kosong";
        else cuaca=data.getCuaca();

        if (data.getPompa_air()==null) pompa="Laporan Kosong";
        else pompa=data.getPompa_air();

        if (data.getGenangan_air()==null) genangan="Laporan Kosong";
        else genangan=data.getGenangan_air();

        if (data.getFoto() != null) {
            Picasso.get().load(ENVIRONMENT.FOTO_URL+data.getFoto())
                    .error(R.drawable.ic_take_picture)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(btnFoto);
            btnFoto.setBackgroundResource(R.color.colorGreyLight);
        }

        switch (cuaca){
            case "Cerah":
                radioCuaca.check(R.id.radioCerah);
                break;
            case "Mendung":
                radioCuaca.check(R.id.radioMendung);
                break;
            case "Hujan":
                radioCuaca.check(R.id.radioHujan);
                break;
            default:
                radioCuaca.check(R.id.radioCerah);
                break;
        }

        switch (pompa){
            case "Baik":
                radioPompa.check(R.id.radioPompaBaik);
                break;
            case "Tidak Baik":
                radioPompa.check(R.id.radioPompaTidakBaik);
                break;
            case "Tidak Ada":
                radioPompa.check(R.id.radioPompaTidakAda);
                break;
            default:
                radioPompa.check(R.id.radioPompaBaik);
                break;
        }

        switch (genangan){
            case "Ada":
                radioGenangan.check(R.id.radioGenanganAda);
                break;
            case "Tidak Ada":
                radioGenangan.check(R.id.radioGenanganTidak);
                break;
            default:
                radioGenangan.check(R.id.radioGenanganAda);
                break;
        }
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
    public void successPostImage(String nama, boolean index) {
        mapRequest.put("foto", nama);
        applicationPresenter.putGeneral();
    }

    @Override
    public Map<String, RequestBody> GetRequestBody() {
        Map<String, RequestBody> MapRequestBody = new HashMap<>();
        MapRequestBody.put("id", RequestBody.create(okhttp3.MultipartBody.FORM, sessionManager.getSpIduser()));
        MapRequestBody.put("sto", RequestBody.create(okhttp3.MultipartBody.FORM, sessionManager.getSpNamaSTO()));
        return MapRequestBody;
    }

    @Override
    public MultipartBody.Part GetMultiPart(boolean index) {
        return imageHandle.convertMultiparFile(file);
    }
}
