package com.example.projectalpha.Activity.AdminActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.R;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class InfoUserActivity extends CustomCompatActivity {

    private String[] PETUGAS;

    private TextView txtNama, txtNoHp, txtWitel, txtSTO, txtUsername, txtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_user);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setVariable() {
        txtNama = findViewById(R.id.txtNama);
        txtNoHp = findViewById(R.id.txtNoHp);
        txtWitel = findViewById(R.id.txtWitel);
        txtSTO = findViewById(R.id.txtSTO);
        txtUsername = findViewById(R.id.txtUsername);
        txtPassword = findViewById(R.id.txtPassword2);
        PETUGAS = getIntent().getStringArrayExtra(ENVIRONMENT.DATA_PETUGAS);

        Picasso.get().setLoggingEnabled(false);
        Picasso.get().setIndicatorsEnabled(false);
    }

    private void createView() {
        ImageView ivProfile = findViewById(R.id.ivProfile);
        txtNama.setText(PETUGAS[0]);
        txtNoHp.setText(PETUGAS[1]);
        txtWitel.setText(PETUGAS[2]);
        txtSTO.setText(PETUGAS[3]);
        txtUsername.setText(PETUGAS[4]);
        txtPassword.setText(PETUGAS[5]);
        Picasso.get().load(ENVIRONMENT.PROFILE_IMAGE+PETUGAS[6])
                .error(R.drawable.ic_take_picture)
                .resize(ENVIRONMENT.IMAGE_SCALE_WIDTH, ENVIRONMENT.IMAGE_SCALE_HEIGHT)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(ivProfile);
    }

    private void setBtnFooter() {
        backClick(UpdateMenuUserActivity.class);
        outClick();
        homeClick(MainAdminActivity.class);
    }
}