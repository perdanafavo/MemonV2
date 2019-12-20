package com.example.projectalpha.Activity.UsersActivity.FormActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.projectalpha.Activity.UsersActivity.MainUserActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.ImageHandle;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.BIRData;
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

public class BIRActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetRoomRequest, ApplicationViews.UploadWithImage, ApplicationViews.ReportViews.PostRequest {

    private int IDLaporan, RUANGAN;

    private RadioGroup radioBakar, radioSteker, radioOli;
    private EditText etSuhu;
    private Button btnSimpan;
    private TextView tvTitleBIr;
    private ImageButton btnRuangan, btnSuhu;
    private LinearLayout linearLayout;

    private ApplicationPresenter applicationPresenter;
    private SessionManager sessionManager;

    private BIRData dataReport;
    private ProgressDialog mDialog;
    private ImageHandle imageHandle;

    private File file = null, file2 = null;
    private Map<String, String> mapRequest = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bir);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ENVIRONMENT.REQUEST_TAKE_PHOTO_RUANGAN && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null){
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap compress = imageHandle.bipmapCompress(imageBitmap);
                file = imageHandle.fileCreate(compress, ENVIRONMENT.RUANGAN);
                btnRuangan.setImageBitmap(compress);
                btnRuangan.setBackgroundResource(R.color.colorGreyLight);
            }
        } else if (requestCode == ENVIRONMENT.REQUEST_TAKE_PHOTO_SUHU && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            if (extras != null){
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                Bitmap compress = imageHandle.bipmapCompress(imageBitmap);
                file2 = imageHandle.fileCreate(compress, ENVIRONMENT.SUHU);
                btnSuhu.setImageBitmap(compress);
                btnSuhu.setBackgroundResource(R.color.colorGreyLight);
            }
        }
    }

    private void setVariable() {
        imageHandle = new ImageHandle(BIRActivity.this);
        IDLaporan = getIntent().getIntExtra(ENVIRONMENT.ID_LAPORAN, 0);
        RUANGAN = getIntent().getIntExtra(ENVIRONMENT.RUANGAN, 1);

        applicationPresenter = new ApplicationPresenter(BIRActivity.this);
        sessionManager = new SessionManager(BIRActivity.this);

        radioBakar  = findViewById(R.id.radioBMTerbakar);
        radioOli    = findViewById(R.id.radioCeceranOli);
        radioSteker = findViewById(R.id.radioSteker);
        etSuhu      = findViewById(R.id.editSuhu);
        btnSimpan   = findViewById(R.id.btnSimpan);
        btnRuangan  = findViewById(R.id.btnRuangan);
        btnSuhu     = findViewById(R.id.btnSuhu);
        linearLayout = findViewById(R.id.ceceranWrap);
        tvTitleBIr = findViewById(R.id.tvTitleBIR);

        mDialog = new ProgressDialog(BIRActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
    }

    private void setBtnFooter() {
        super.homeClick(MainUserActivity.class);
        super.backClick();
    }

    private void createView() {
        mDialog.show();

        String title;

        switch (RUANGAN){
            case 2:
                title = "Ruang Transmisi";
                break;
            case 3:
                title = "Ruang Rectifier";
                break;
            case 4:
                title = "Ruang Batere Kering";
                break;
            case 5:
                title = "Ruang Akses / GPON";
                break;
            case 6:
                title = "Ruang Genset";
                break;
            case 7:
                title = "Ruang OLO";
                break;
            case 8:
                title = "Ruang Batere Basah";
                break;
            default:
                title = "Ruang Sentral";
                break;
        }

        tvTitleBIr.setText(title);

        if (RUANGAN == 6) linearLayout.setVisibility(View.VISIBLE);
        else linearLayout.setVisibility(View.GONE);

        applicationPresenter.getRoomReportByRuangan();

        btnRuangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHandle.takeImage(ENVIRONMENT.REQUEST_TAKE_PHOTO_RUANGAN);
            }
        });

        btnSuhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHandle.takeImage(ENVIRONMENT.REQUEST_TAKE_PHOTO_SUHU);
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
        mapRequest.put("ruangan", String.valueOf(RUANGAN));

        RadioButton btnTerbakar = findViewById(radioBakar.getCheckedRadioButtonId());
        String terbakar = btnTerbakar.getText().toString();
        if (dataReport.getBenda_terbakar() == null || !dataReport.getBenda_terbakar().equals(terbakar))mapRequest.put("benda_terbakar", terbakar);

        RadioButton btnSteker = findViewById(radioSteker.getCheckedRadioButtonId());
        String steker = btnSteker.getText().toString();
        if (dataReport.getSteker_bertumpuk() == null || !dataReport.getSteker_bertumpuk().equals(steker)) mapRequest.put("steker_bertumpuk", steker);

        RadioButton btnOli = findViewById(radioOli.getCheckedRadioButtonId());
        String oli = btnOli.getText().toString();
        if (dataReport.getCeceran_oli()==null || !dataReport.getCeceran_oli().equals(oli))mapRequest.put("ceceran_oli", oli);

        String suhu = "0";
        if (!etSuhu.getText().toString().matches("")) suhu = etSuhu.getText().toString();
        if (dataReport.getSuhu() != Integer.parseInt(suhu))mapRequest.put("suhu", suhu);

        if (file != null){
            if (file2 != null || dataReport.getFoto_suhu() != null){
               applicationPresenter.updateImage(true);
            } else {
                mDialog.dismiss();
                simpleToast("Foto belum ditambahkan");
            }
        } else if (file2 != null){
            if (dataReport.getFoto_ruangan() != null){
                applicationPresenter.updateImage(false);
            } else {
                mDialog.dismiss();
                simpleToast("Foto belum ditambahkan");
            }
        } else {
            if (dataReport.getFoto_ruangan() != null && dataReport.getFoto_suhu() != null){
                applicationPresenter.putRoom();
            }else {
                mDialog.dismiss();
                simpleToast("Foto belum ditambahkan");
            }
        }

    }


    @Override
    public void requestFailled(String message) {
        simpleToast(message);
        mDialog.dismiss();
    }

    @Override
    public void successRequest() {
        mDialog.dismiss();
        new AlertDialog.Builder(BIRActivity.this)
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
    public int getIndexRoom() {
        return RUANGAN;
    }

    @Override
    public void SuccessRequestRoom(List<BIRData> data) {
        dataReport = data.get(0);

        String terbakar, steker, oli;

        if (dataReport.getBenda_terbakar()==null) terbakar="Laporan Kosong";
        else terbakar=dataReport.getBenda_terbakar();

        if (dataReport.getSteker_bertumpuk()==null) steker="Laporan Kosong";
        else steker=dataReport.getSteker_bertumpuk();

        if (dataReport.getCeceran_oli()==null) oli="Laporan Kosong";
        else oli=dataReport.getCeceran_oli();

        etSuhu.setText(String.valueOf(dataReport.getSuhu()));

        if (dataReport.getFoto_suhu() != null){
            Picasso.get().load(ENVIRONMENT.FOTO_URL+dataReport.getFoto_suhu())
                    .error(R.drawable.ic_take_picture)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(btnSuhu);
            btnSuhu.setBackgroundResource(R.color.colorGreyLight);
        }

        if (dataReport.getFoto_ruangan() != null) {
            Picasso.get().load(ENVIRONMENT.FOTO_URL+dataReport.getFoto_ruangan())
                    .error(R.drawable.ic_take_picture)
                    .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                    .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                    .into(btnRuangan);
            btnRuangan.setBackgroundResource(R.color.colorGreyLight);
        }

        if ("Tidak Ada".equals(terbakar)) {
            radioBakar.check(R.id.radioBMTidak);
        } else {
            radioBakar.check(R.id.radioBMAda);
        }

        if ("Tidak Ada".equals(steker)) {
            radioSteker.check(R.id.radioStekerTidak);
        } else {
            radioSteker.check(R.id.radioStekerAda);
        }

        if ("Tidak Ada".equals(oli)) {
            radioOli.check(R.id.radioCeceranTidak);
        } else {
            radioOli.check(R.id.radioCeceranAda);
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
        if (index) {
            mapRequest.put("foto_ruangan", nama);
            if (file2 != null) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        applicationPresenter.updateImage(false);
                    }
                }, 1000);
            } else {
                applicationPresenter.putRoom();
            }
        } else {
            mapRequest.put("foto_suhu", nama);
            applicationPresenter.putRoom();
        }
    }

    @Override
    public Map<String, RequestBody> GetRequestBody() {
        Map<String, RequestBody> MapRequestBody = new HashMap<>();
        MapRequestBody.put("id", RequestBody.create(sessionManager.getSpIduser(),okhttp3.MultipartBody.FORM));
        MapRequestBody.put("sto", RequestBody.create(sessionManager.getSpNamaSTO(),okhttp3.MultipartBody.FORM));
        return MapRequestBody;
    }

    @Override
    public MultipartBody.Part GetMultiPart(boolean index) {
        if (index) return imageHandle.convertMultiparFile(file);
        else return imageHandle.convertMultiparFile(file2);
    }
}
