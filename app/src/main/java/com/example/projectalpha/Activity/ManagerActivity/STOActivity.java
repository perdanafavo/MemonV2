package com.example.projectalpha.Activity.ManagerActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.projectalpha.Activity.AdminActivity.ViewVerifikasiLaporanActivity;
import com.example.projectalpha.Activity.UsersActivity.FormActivity.CatatanActivity;
import com.example.projectalpha.Activity.ValidatorActivity.MainValidatorActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.SaveImageHelper;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Helpers.Time;
import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.KondisiUmumData;
import com.example.projectalpha.Models.SubModels.OthersData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Models.TimeModels;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.Presenter.TimePresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.example.projectalpha.Views.TimeView;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class STOActivity extends CustomCompatActivity
        implements TimeView, ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetFuelRequest,
            ApplicationViews.ReportViews.GetGeneralRequest, ApplicationViews.ReportViews.GetPowerRequest, ApplicationViews.ReportViews.GetRoomRequest, ApplicationViews.ReportViews.GetOthersRequest, ApplicationViews.ReportViews.PutValidator, ApplicationViews.ReportViews.GetReportValidator, ApplicationViews.ReportViews.PutValidator.Notification {

    private static final int PERMISSION_REQUEST_CODE = 1000;
    private ProgressDialog mDialog;
    private Dialog myDialog;

    private ApplicationPresenter applicationPresenter;

    private int LAPORAN, STO, STATUS_APPROVED;

    private String Tanggal;
    private Boolean statusSuhu, statusFuel, statusPower, statusUmum;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView[] imageViews;
    private TextView [] tvKondisiUmum, tvCatuan, tvBBM, tvSentral, tvTransmisi, tvRectifier, tvBatere, tvAkses, tvGenset, tvOlo, tvBatereBasah;
    private TextView tvSTO, tvTanggal, txHome, tvClose, tvPeringatan, tvCatatanPetugas, tvCatatanValidator, tvCatatanManager;
    private Button btnContact, btnApprove, btnPIC, btnValidasi, btnCatatan;
    private PhotoView imagephoto;
    private SessionManager sessionManager;
    private ImageButton btnHome;

    private Boolean notificationStatus = false;
    private String notificationText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sto);

        setVariable();
        setBtnFooter();
        checkPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createView();
    }

    private void checkPermission(){
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)

            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, PERMISSION_REQUEST_CODE);
    }

    private void zoomImage(final ImageView img, final String data) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(STOActivity.this);
                View mView = View.inflate(STOActivity.this, R.layout.photo_layout, null);
                mBuilder.setView(mView);
                imagephoto = mView.findViewById(R.id.photo_zoom);
                Picasso.get().load(ENVIRONMENT.FOTO_URL + data).into(imagephoto);
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }

    private void downloadImage(final ImageView img, final String data) {
        img.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (ActivityCompat.checkSelfPermission(STOActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    simpleToast("You should grant permission");
                    ActivityCompat.requestPermissions(STOActivity.this, new String[]{}, PERMISSION_REQUEST_CODE);

                } else {
                    AlertDialog dialog = new SpotsDialog(STOActivity.this);
                    dialog.show();
                    dialog.setMessage("Downloading...");

                    String filename = tvSTO.toString() + "_" + tvTanggal.toString() + ".jpg";
                    Picasso.get()
                            .load(ENVIRONMENT.FOTO_URL + data)
                            .into(new SaveImageHelper(getBaseContext(), dialog, getApplicationContext().getContentResolver(), filename));
                }
                return true;
            }
        });
    }

    private void setVariable() {
        myDialog = new Dialog(STOActivity.this);
        myDialog.setContentView(R.layout.custom_popup_validasi);

        mDialog = new ProgressDialog(STOActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        sessionManager = new SessionManager(getApplicationContext());
        LAPORAN = getIntent().getIntExtra(ENVIRONMENT.ID_LAPORAN, 0);
        STO = getIntent().getIntExtra(ENVIRONMENT.ID_STO, 0);
        STATUS_APPROVED = getIntent().getIntExtra(ENVIRONMENT.STATUS_APPROVED, 2);
        Tanggal = getIntent().getStringExtra(ENVIRONMENT.TANGGAL_LAPORAN);
        applicationPresenter = new ApplicationPresenter(STOActivity.this);

        tvSTO           = findViewById(R.id.txtNamaSTO);
        tvTanggal       = findViewById(R.id.txtTanggal);
        btnContact      = findViewById(R.id.btnContact);
        btnApprove      = findViewById(R.id.btnApprove);
        btnCatatan      = findViewById(R.id.btnTambahCatatan);
        btnHome         = findViewById(R.id.btnHome);
        txHome          = findViewById(R.id.txHome);

        imageViews = new ImageView[]{
                findViewById(R.id.imgKondisi),
                findViewById(R.id.imgBBM),
                findViewById(R.id.imgSentral),
                findViewById(R.id.imgSuhuSentral),
                findViewById(R.id.imgTransmisi),
                findViewById(R.id.imgSuhuTransmisi),
                findViewById(R.id.imgRectifier),
                findViewById(R.id.imgSuhuRectifier),
                findViewById(R.id.imgBatere),
                findViewById(R.id.imgSuhuBatere),
                findViewById(R.id.imgAkses),
                findViewById(R.id.imgSuhuAkses),
                findViewById(R.id.imgGenset),
                findViewById(R.id.imgSuhuGenset),
                findViewById(R.id.imgOlo),
                findViewById(R.id.imgSuhuOlo),
                findViewById(R.id.imgBatereBasah),
                findViewById(R.id.imgSuhuBatereBasah)
        };

        tvKondisiUmum = new TextView[]{findViewById(R.id.txtCuaca), findViewById(R.id.txtPompa), findViewById(R.id.txtGenangan)};
        tvCatuan      = new TextView[]{findViewById(R.id.txtPLN), findViewById(R.id.txtGenset)};
        tvBBM         = new TextView[]{findViewById(R.id.txtKapasitas), findViewById(R.id.txtTankiHarian), findViewById(R.id.tvSisaBBM), findViewById(R.id.tvTankiWarning)};
        tvSentral     = new TextView[]{findViewById(R.id.txtSuhuSentral), findViewById(R.id.txtSentralBMT), findViewById(R.id.txtSentralSB)};
        tvTransmisi   = new TextView[]{findViewById(R.id.txtSuhuTransmisi), findViewById(R.id.txtTransmisiBMT), findViewById(R.id.txtTransmisiSB)};
        tvRectifier   = new TextView[]{findViewById(R.id.txtSuhuRectifier), findViewById(R.id.txtRectifierBMT), findViewById(R.id.txtRectifierSB)};
        tvBatere      = new TextView[]{findViewById(R.id.txtSuhuBatere), findViewById(R.id.txtBatereBMT), findViewById(R.id.txtBatereSB)};
        tvAkses       = new TextView[]{findViewById(R.id.txtSuhuAkses), findViewById(R.id.txtAksesBMT), findViewById(R.id.txtAksesSB)};
        tvGenset      = new TextView[]{findViewById(R.id.txtSuhuGenset), findViewById(R.id.txtGensetBMT), findViewById(R.id.txtGensetSB), findViewById(R.id.txtGensetCO)};
        tvOlo         = new TextView[]{findViewById(R.id.txtSuhuOlo), findViewById(R.id.txtOloBMT), findViewById(R.id.txtOloSB)};
        tvBatereBasah = new TextView[]{findViewById(R.id.txtSuhuBatereBasah), findViewById(R.id.txtBatereBasahBMT), findViewById(R.id.txtBatereBasahSB)};
        tvCatatanPetugas        = findViewById(R.id.txtCatatan);
        tvCatatanManager        = findViewById(R.id.txtCatatanManager);
        tvCatatanValidator      = findViewById(R.id.txtCatatanValidator);

        swipeRefreshLayout = findViewById(R.id.swlayout);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                createView();
            }
        });
    }

    private void createView() {
        statusFuel=true;
        statusPower=true;
        statusSuhu=true;
        statusUmum=true;
        mDialog.show();

        if (Tanggal != null){
            notificationText = "PERINGATAN STATUS\nSTO "+getIntent().getStringExtra(ENVIRONMENT.NAMA_STO)+"\n"+Time.tanggal(Tanggal);
            tvTanggal.setText(Time.tanggal(Tanggal));
            tvSTO.setText(getIntent().getStringExtra(ENVIRONMENT.NAMA_STO));
        } else {
            TimePresenter timePresenter = new TimePresenter(STOActivity.this);
            timePresenter.requestTimer();
        }

        for (ImageView item:imageViews){
            item.setImageResource(R.drawable.ic_no_image);
        }

        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            mDialog.dismiss();
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
            return ;
        } else {
            if (LAPORAN != 0){
                applicationPresenter.getGeneral();
                applicationPresenter.getPower();
                applicationPresenter.getFuel();
                applicationPresenter.getRoomReport();
                applicationPresenter.getOthersReport();
                btnCatatan.setVisibility(View.VISIBLE);
            }
            btnContact.setOnClickListener(moveToUsersContact);
            btnApprove.setOnClickListener(validasiLaporan);
            btnCatatan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(STOActivity.this, CatatanActivity.class)
                            .putExtra(ENVIRONMENT.ID_LAPORAN, LAPORAN));
                }
            });
        }
        mDialog.dismiss();
        if (sessionManager.getSpPrivileges()==1){
            btnCatatan.setVisibility(View.GONE);
        }
        else if (sessionManager.getSpPrivileges()==2){
            btnApprove.setVisibility(View.GONE);
        }
        else{
            btnContact.setVisibility(View.GONE);
            if (STATUS_APPROVED==1) {
                btnApprove.setVisibility(View.GONE);
            }
        }
    }

    private void showPopup(String message){
        tvPeringatan = myDialog.findViewById(R.id.tvPeringatan);
        tvClose = myDialog.findViewById(R.id.tvClose);
        btnPIC = myDialog.findViewById(R.id.btnPIC);
        btnValidasi = myDialog.findViewById(R.id.btnValidasi);

        myDialog.setCanceledOnTouchOutside(false);
        tvPeringatan.setText(message);
        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        btnPIC.setOnClickListener(moveToUsersContact);
        btnValidasi.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (notificationStatus)applicationPresenter.Notification();
                prosesValidasi();
            }
        });
        if (myDialog.getWindow() != null) {
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        myDialog.show();
    }

    private void prosesValidasi(){
        applicationPresenter.updateValidator();
        simpleToast("Validasi berhasil");

        if (sessionManager.getSpPrivileges()==4) {
            simpleIntent(MainValidatorActivity.class);
        }
        else {
            simpleIntent(ViewVerifikasiLaporanActivity.class);
        }
    }

    View.OnClickListener validasiLaporan= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //showPopup();
            if (!statusUmum){
                showPopup(ENVIRONMENT.ON_BAD_NULL_VALIDASI);
            }
            else if(statusFuel&&statusPower&&statusSuhu){
                prosesValidasi();
            }
            else {
                showPopup(ENVIRONMENT.ON_BAD_REQUEST_VALIDASI);
            }
        }
    };

    View.OnClickListener moveToUsersContact= new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(STOActivity.this, ShowEmployeeActivity.class);
            intent.putExtra(ENVIRONMENT.ID_LAPORAN, LAPORAN);
            intent.putExtra(ENVIRONMENT.ID_STO, STO);
            intent.putExtra(ENVIRONMENT.TANGGAL_LAPORAN, Tanggal);
            startActivity(intent);
            myDialog.dismiss();
        }
    };

    private void setBtnFooter() {
        backClick();
        if (sessionManager.getSpPrivileges()==2){
            homeClick(MainManagerActivity.class);
        }
        else if (sessionManager.getSpPrivileges()==4){
            homeClick(MainValidatorActivity.class);
        }
        else{
            txHome.setVisibility(View.GONE);
            btnHome.setVisibility(View.GONE);
        }
        outClick();
    }

    @Override
    public void requestFailled(String message) {
        simpleToast(message);
        mDialog.dismiss();
    }

    @Override
    public int getIndexReport() {
        return LAPORAN;
    }

    @Override
    public void SuccessRequestGeneral(KondisiUmumData data) {
        if (data.getCuaca() != null)tvKondisiUmum[0].setText(data.getCuaca()); else statusUmum = false;
        if (data.getPompa_air() != null)tvKondisiUmum[1].setText(data.getPompa_air()); else statusUmum = false;
        if (data.getGenangan_air() != null)tvKondisiUmum[2].setText(data.getGenangan_air()); else statusUmum = false;
        if (data.getFoto() != null){
            Picasso.get().load(ENVIRONMENT.FOTO_URL+data.getFoto()).into(imageViews[0]);
            zoomImage(imageViews[0], data.getFoto());
            downloadImage(imageViews[0], data.getFoto());} else statusUmum = false;
    }

    @Override
    public void SuccessRequestPower(PowerData data) {
        if (data.getPln() != null) tvCatuan[0].setText(data.getPln()); else statusUmum = false;
        if (data.getGenset() != null)tvCatuan[1].setText(data.getGenset()); else statusUmum = false;

        if (data.getPln() != null && data.getPln().equals("ON")
                && data.getGenset() != null && data.getGenset().equals("ON")) {
            notificationStatus = true;
            notificationText += "\n\nCatuan\nPLN : "+data.getPln()+"\nGENSET : "+data.getGenset();
            statusPower = false;
        } else if (data.getPln() != null && data.getPln().equals("OFF")
                && data.getGenset() != null && data.getGenset().equals("OFF")) {
            notificationStatus = true;
            notificationText += "\n\nCatuan\nPLN : "+data.getPln()+"\nGENSET : "+data.getGenset();
            statusPower = false;
        }
    }

    @Override
    public void SuccessRequestFuel(FuelData data) {
        String kapasitas = data.getKapasitas()+" Liter", tanki = data.getTanki_bulanan()+" %",
                sisa = Math.floor(data.getKapasitas()*data.getTanki_bulanan()/1d/100)+" Liter", peringatan = Math.floor(data.getKapasitas()*data.getKapasitas_rendah()/1d/100)+" Liter";
        tvBBM[0].setText(kapasitas);
        tvBBM[1].setText(tanki);
        tvBBM[2].setText(sisa);
        tvBBM[3].setText(peringatan);
        if (data.getFoto() != null){
            Picasso.get().load(ENVIRONMENT.FOTO_URL+data.getFoto()).into(imageViews[1]);
            zoomImage(imageViews[1], data.getFoto());
            downloadImage(imageViews[1], data.getFoto());} else statusUmum = false;
        if (data.getTanki_bulanan() <= data.getKapasitas_rendah()){
            notificationStatus = true;
            notificationText += "\n\nTanki BBM\nTanki Bulanan : "+data.getTanki_bulanan()+" Liter\nBatas Terendah : "+data.getKapasitas_rendah()+" Liter";
            statusFuel=false;
        }
    }

    @Override
    public void SuccessRequestRoom(List<BIRData> data) {
        for (int i=0; i<data.size(); i++){
            switch (data.get(i).getRuangan()){
                case 1:
                    String suhuSentral = data.get(i).getSuhu()+" °C";
                    tvSentral[0].setText(suhuSentral);
                    if (data.get(i).getBenda_terbakar() != null) tvSentral[1].setText(data.get(i).getBenda_terbakar()); else statusUmum = false;
                    if (data.get(i).getSteker_bertumpuk() != null) tvSentral[2].setText(data.get(i).getSteker_bertumpuk()); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_ruangan()).into(imageViews[2]); else statusUmum = false;
                    if (data.get(i).getFoto_suhu() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_suhu()).into(imageViews[3]); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null && data.get(i).getFoto_suhu() != null){
                        zoomImage(imageViews[2], data.get(i).getFoto_ruangan());
                        zoomImage(imageViews[3], data.get(i).getFoto_suhu());
                        downloadImage(imageViews[2], data.get(i).getFoto_ruangan());
                        downloadImage(imageViews[3], data.get(i).getFoto_suhu());}
                    if (data.get(i).getSuhu() > 23){
                        notificationStatus = true;
                        if (statusPower) notificationText += "\n\nSuhu Ruangan";
                        notificationText += "\nRuang Sentra : "+data.get(i).getSuhu()+" °C";
                        statusPower = false;
                    }
                    break;
                case 2:
                    String suhuTransmisi = data.get(i).getSuhu()+" °C";
                    tvTransmisi[0].setText(suhuTransmisi);
                    if (data.get(i).getBenda_terbakar() != null) tvTransmisi[1].setText(data.get(i).getBenda_terbakar()); else statusUmum = false;
                    if (data.get(i).getSteker_bertumpuk() != null) tvTransmisi[2].setText(data.get(i).getSteker_bertumpuk()); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_ruangan()).into(imageViews[4]); else statusUmum = false;
                    if (data.get(i).getFoto_suhu() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_suhu()).into(imageViews[5]); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null && data.get(i).getFoto_suhu() != null){
                        zoomImage(imageViews[4], data.get(i).getFoto_ruangan());
                        zoomImage(imageViews[5], data.get(i).getFoto_suhu());
                        downloadImage(imageViews[4], data.get(i).getFoto_ruangan());
                        downloadImage(imageViews[5], data.get(i).getFoto_suhu());}
                    if (data.get(i).getSuhu() > 23){
                        notificationStatus = true;
                        if (statusPower) notificationText += "\n\nSuhu Ruangan";
                        notificationText += "\nRuang Transmisi : "+data.get(i).getSuhu()+" °C";
                        statusSuhu = false;
                    }
                    break;
                case 3:
                    String suhuRectifier = data.get(i).getSuhu()+" °C";
                    tvRectifier[0].setText(suhuRectifier);
                    if (data.get(i).getBenda_terbakar() != null) tvRectifier[1].setText(data.get(i).getBenda_terbakar()); else statusUmum = false;
                    if (data.get(i).getSteker_bertumpuk() != null) tvRectifier[2].setText(data.get(i).getSteker_bertumpuk()); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_ruangan()).into(imageViews[6]); else statusUmum = false;
                    if (data.get(i).getFoto_suhu() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_suhu()).into(imageViews[7]); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null && data.get(i).getFoto_suhu() != null){
                        zoomImage(imageViews[6], data.get(i).getFoto_ruangan());
                        zoomImage(imageViews[7], data.get(i).getFoto_suhu());
                        downloadImage(imageViews[6], data.get(i).getFoto_ruangan());
                        downloadImage(imageViews[7], data.get(i).getFoto_suhu());}
                    if (data.get(i).getSuhu() > 23){
                        notificationStatus = true;
                        if (statusPower) notificationText += "\n\nSuhu Ruangan";
                        notificationText += "\nRuang Rectifier : "+data.get(i).getSuhu()+" °C";
                        statusSuhu = false;
                    }
                    break;
                case 4:
                    String suhuBatere = data.get(i).getSuhu()+" °C";
                    tvBatere[0].setText(suhuBatere);
                    if (data.get(i).getBenda_terbakar() != null) tvBatere[1].setText(data.get(i).getBenda_terbakar()); else statusUmum = false;
                    if (data.get(i).getSteker_bertumpuk() != null) tvBatere[2].setText(data.get(i).getSteker_bertumpuk()); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_ruangan()).into(imageViews[8]); else statusUmum = false;
                    if (data.get(i).getFoto_suhu() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_suhu()).into(imageViews[9]); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null && data.get(i).getFoto_suhu() != null){
                        zoomImage(imageViews[8], data.get(i).getFoto_ruangan());
                        zoomImage(imageViews[9], data.get(i).getFoto_suhu());
                        downloadImage(imageViews[8], data.get(i).getFoto_ruangan());
                        downloadImage(imageViews[9], data.get(i).getFoto_suhu());}
                    if (data.get(i).getSuhu() > 23){
                        notificationStatus = true;
                        if (statusPower) notificationText += "\n\nSuhu Ruangan";
                        notificationText += "\nRuang Batere : "+data.get(i).getSuhu()+" °C";
                        statusSuhu = false;
                    }
                    break;
                case 5:
                    String suhuAkses = data.get(i).getSuhu()+" °C";
                    tvAkses[0].setText(suhuAkses);
                    if (data.get(i).getBenda_terbakar() != null) tvAkses[1].setText(data.get(i).getBenda_terbakar()); else statusUmum = false;
                    if (data.get(i).getSteker_bertumpuk() != null) tvAkses[2].setText(data.get(i).getSteker_bertumpuk()); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_ruangan()).into(imageViews[10]); else statusUmum = false;
                    if (data.get(i).getFoto_suhu() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_suhu()).into(imageViews[11]); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null && data.get(i).getFoto_suhu() != null){
                        zoomImage(imageViews[10], data.get(i).getFoto_ruangan());
                        zoomImage(imageViews[11], data.get(i).getFoto_suhu());
                        downloadImage(imageViews[10], data.get(i).getFoto_ruangan());
                        downloadImage(imageViews[11], data.get(i).getFoto_suhu());}
                    if (data.get(i).getSuhu() > 23){
                        notificationStatus = true;
                        if (statusPower) notificationText += "\n\nSuhu Ruangan";
                        notificationText += "\nRuang Akses : "+data.get(i).getSuhu()+" °C";
                        statusSuhu = false;
                    }
                    break;
                case 6:
                    String suhuGenset = data.get(i).getSuhu()+" °C";
                    tvGenset[0].setText(suhuGenset);
                    if (data.get(i).getBenda_terbakar() != null) tvGenset[1].setText(data.get(i).getBenda_terbakar()); else statusUmum = false;
                    if (data.get(i).getSteker_bertumpuk() != null) tvGenset[2].setText(data.get(i).getSteker_bertumpuk()); else statusUmum = false;
                    if (data.get(i).getCeceran_oli() != null) tvGenset[3].setText(data.get(i).getCeceran_oli()); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_ruangan()).into(imageViews[12]); else statusUmum = false;
                    if (data.get(i).getFoto_suhu() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_suhu()).into(imageViews[13]); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null && data.get(i).getFoto_suhu() != null){
                        zoomImage(imageViews[12], data.get(i).getFoto_ruangan());
                        zoomImage(imageViews[13], data.get(i).getFoto_suhu());
                        downloadImage(imageViews[12], data.get(i).getFoto_ruangan());
                        downloadImage(imageViews[13], data.get(i).getFoto_suhu());}
                    break;
                case 7:
                    String suhuOlo = data.get(i).getSuhu()+" °C";
                    tvOlo[0].setText(suhuOlo);
                    if (data.get(i).getBenda_terbakar() != null) tvOlo[1].setText(data.get(i).getBenda_terbakar()); else statusUmum = false;
                    if (data.get(i).getSteker_bertumpuk() != null) tvOlo[2].setText(data.get(i).getSteker_bertumpuk()); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_ruangan()).into(imageViews[14]); else statusUmum = false;
                    if (data.get(i).getFoto_suhu() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_suhu()).into(imageViews[15]); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null && data.get(i).getFoto_suhu() != null){
                        zoomImage(imageViews[14], data.get(i).getFoto_ruangan());
                        zoomImage(imageViews[15], data.get(i).getFoto_suhu());
                        downloadImage(imageViews[14], data.get(i).getFoto_ruangan());
                        downloadImage(imageViews[15], data.get(i).getFoto_suhu());}
                    if (data.get(i).getSuhu() > 23){
                        notificationStatus = true;
                        if (statusPower) notificationText += "\n\nSuhu Ruangan";
                        notificationText += "\nRuang Olo : "+data.get(i).getSuhu()+" °C";
                        statusSuhu = false;
                    }
                    break;
                case 8:
                    String suhuBatereBasah = data.get(i).getSuhu()+" °C";
                    tvBatereBasah[0].setText(suhuBatereBasah);
                    if (data.get(i).getBenda_terbakar() != null) tvBatereBasah[1].setText(data.get(i).getBenda_terbakar()); else statusUmum = false;
                    if (data.get(i).getSteker_bertumpuk() != null) tvBatereBasah[2].setText(data.get(i).getSteker_bertumpuk()); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_ruangan()).into(imageViews[16]); else statusUmum = false;
                    if (data.get(i).getFoto_suhu() != null) Picasso.get().load(ENVIRONMENT.FOTO_URL+data.get(i).getFoto_suhu()).into(imageViews[17]); else statusUmum = false;
                    if (data.get(i).getFoto_ruangan() != null && data.get(i).getFoto_suhu() != null){
                        zoomImage(imageViews[16], data.get(i).getFoto_ruangan());
                        zoomImage(imageViews[17], data.get(i).getFoto_suhu());
                        downloadImage(imageViews[16], data.get(i).getFoto_ruangan());
                        downloadImage(imageViews[17], data.get(i).getFoto_suhu());}
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                simpleToast("Permission Granted");
            else
                simpleToast("Permission Denied");
        }
    }

    @Override
    public void SuccessRequestOthers(OthersData data) {
        if (data.getCatatan() != null) tvCatatanPetugas.setText(data.getCatatan());
        if (data.getCatatan_validator() != null) tvCatatanValidator.setText(data.getCatatan_validator());
        if (data.getCatatan_manager() != null) tvCatatanManager.setText(data.getCatatan_manager());
    }

    @Override
    public void successRequest() {

    }
    @Override
    public int getIndexRoom() {
        return 0;
    }

    @Override
    public void successRequestTimer(TimeModels dataResponse) {
        String txtWaktu, txtHari, txtTanggal;
        txtHari = dataResponse.getHari();
        txtTanggal = dataResponse.getBulan();
        txtWaktu = txtHari+", "+ txtTanggal;

        tvTanggal.setText(txtWaktu);
        tvSTO.setText(getIntent().getStringExtra(ENVIRONMENT.NAMA_STO));
    }

    @Override
    public void failedRequest(String message) {

    }

    @Override
    public int getIndexId() {
        return LAPORAN;
    }

    @Override
    public int getValidator() {
        return 1;
    }

    @Override
    public int getIndexSto() {
        return sessionManager.getSpSTO();
    }

    @Override
    public Map<String, String> Notification() {
        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("pesan", notificationText);
        return requestBodyMap;
    }
}
