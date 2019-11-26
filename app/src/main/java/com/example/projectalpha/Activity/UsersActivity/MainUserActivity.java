package com.example.projectalpha.Activity.UsersActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.TimeModels;
import com.example.projectalpha.Presenter.TimePresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.TimeView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class MainUserActivity extends CustomCompatActivity implements TimeView {

    private TimePresenter timePresenter;
    private SessionManager sessionManager;
    private ProgressDialog mDialog;

    private ImageButton btnPagi, btnMalam;
    private TextView tvNama, tvHandphone, tvSTO, tvAlamat;
    private ImageView ivProfile;

    private int Jam;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_user);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        timePresenter.requestTimer();
    }

    private void setBtnFooter() {
        super.outClick();
    }

    public void selectShift(){
        btnPagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Jam>=8 && Jam<20){
                    startActivity(new Intent(MainUserActivity.this, SelectFormActivity.class)
                            .putExtra("SHIFT", 1)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    new AlertDialog.Builder(MainUserActivity.this)
                            .setMessage(ENVIRONMENT.SHIFT_PAGI_MESSAGE)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }
            }
        });

        btnMalam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Jam>=20 || Jam<8){
                    startActivity(new Intent(MainUserActivity.this, SelectFormActivity.class)
                            .putExtra("SHIFT", 2)
                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    new AlertDialog.Builder(MainUserActivity.this)
                            .setMessage(ENVIRONMENT.SHIFT_MALAM_MESSAGE)
                            .setCancelable(false)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }
            }
        });
    }

    private void setVariable(){
        sessionManager = new SessionManager(getApplicationContext());
        timePresenter = new TimePresenter(MainUserActivity.this);

        btnPagi = findViewById(R.id.btnPagi);
        btnMalam = findViewById(R.id.btnMalam);

        tvNama      = findViewById(R.id.txtNama);
        tvHandphone = findViewById(R.id.txtHandphone);
        tvSTO       = findViewById(R.id.txtSTO);
        tvAlamat    = findViewById(R.id.txtAlamat);
        ivProfile   = findViewById(R.id.imgUser);

        Picasso.get().setLoggingEnabled(false);
        Picasso.get().setIndicatorsEnabled(false);

    }

    private void createView(){
        String Handphone = "0" + sessionManager.getSpHandphone();
        tvNama.setText(sessionManager.getSpFullname());
        tvHandphone.setText(Handphone);
        tvSTO.setText(sessionManager.getSpNamaSTO());
        tvAlamat.setText(sessionManager.getSpAlamat());

        //inisialisasi widget loading pada layout
        mDialog = new ProgressDialog(MainUserActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();

        Picasso.get().load(ENVIRONMENT.PROFILE_IMAGE+sessionManager.getSpFoto())
                .resize(340, 510)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(ivProfile);

        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            mDialog.dismiss();
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        }
    }

    @Override
    public void successRequestTimer(TimeModels dataResponse) {
        Jam = Integer.parseInt(dataResponse.getJam());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                selectShift();
            }
        }, 1000);
        mDialog.dismiss();
    }

    @Override
    public void failedRequest(String message) {
        simpleToast(message);
        mDialog.dismiss();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            finishAffinity();
        }
        else {
            simpleToast(ENVIRONMENT.BACKPRESSED_MESSAGE);
        }
        mBackPressed = System.currentTimeMillis();
    }
}
