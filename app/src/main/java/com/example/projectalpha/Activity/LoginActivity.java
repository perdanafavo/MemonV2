package com.example.projectalpha.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projectalpha.Activity.AdminActivity.MainAdminActivity;
import com.example.projectalpha.Activity.ManagerActivity.MainManagerActivity;
import com.example.projectalpha.Activity.UsersActivity.MainUserActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.LoginDialog;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.Presenter.LoginPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.LoginViews;

import java.util.List;

public class LoginActivity extends AppCompatActivity implements LoginViews {

    private Button btnLogin;
    private ProgressDialog mDialog;
    private String Username, Password;
    private EditText editUsername, editPassword;

    private LoginPresenter loginPresenter;
    private SessionManager sessionManager;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setVariable();
        createView();
        cekLogin();
    }

    private void createView() {
        mDialog = new ProgressDialog(LoginActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
    }

    private void setVariable() {
        sessionManager = new SessionManager(getApplicationContext());
        loginPresenter = new LoginPresenter(LoginActivity.this);

        btnLogin = findViewById(R.id.btnLogin);
        editUsername = findViewById(R.id.editUsername);
        editPassword = findViewById(R.id.editPassword);
    }

    private void cekLogin(){
        if (sessionManager.getSpAlreadyLoginManager()) {
            startActivity(new Intent(getApplicationContext(), MainManagerActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (sessionManager.getSpAlreadyLoginPetugas()) {
            startActivity(new Intent(getApplicationContext(), MainUserActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (sessionManager.getSpAlreadyLoginAdmin()) {
            startActivity(new Intent(getApplicationContext(), MainAdminActivity.class)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
        }  else {
            Login();
        }
    }

    private void Login() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CekKoneksi.isConnectedToInternet(getBaseContext())) {
                    mDialog.show();

                    Username = editUsername.getText().toString();
                    Password = editPassword.getText().toString();

                    if (Username.matches("")) {
                        LoginDialog.paketLogin(1);
                        openDialog();
                        mDialog.dismiss();
                    } else if (Password.matches("")) {
                        LoginDialog.paketLogin(2);
                        openDialog();
                        mDialog.dismiss();
                    } else {
                        loginPresenter.getUsersLogin();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openDialog() {
        LoginDialog dialogue = new LoginDialog();
        dialogue.show(getSupportFragmentManager(), "Login Dialog");
    }

    @Override
    public String getUsername() {
        return this.Username;
    }

    @Override
    public String getPassword() {
        return this.Password;
    }

    @Override
    public void successLogin(List<UsersData> serverResponse) {
        UsersData getResponse = serverResponse.get(0);
        mDialog.dismiss();
        switch (getResponse.getPrivileges()){
            case 1:
                sessionManager.saveSPString(SessionManager.SP_IDUSER, getResponse.getId());
                sessionManager.saveSPInt(SessionManager.SP_PRIVILEGES, getResponse.getPrivileges());
                sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGINADMIN, true);
                sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGINMANAGER, false);
                sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGINPETUGAS, false);
                startActivity(new Intent(getApplicationContext(), MainAdminActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case 2:
                sessionManager.saveSPString(SessionManager.SP_IDUSER, getResponse.getId());
                sessionManager.saveSPInt(SessionManager.SP_PRIVILEGES, getResponse.getPrivileges());
                sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGINADMIN, false);
                sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGINMANAGER, true);
                sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGINPETUGAS, false);
                startActivity(new Intent(getApplicationContext(), MainManagerActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            case 3:
                sessionManager.saveSPString(SessionManager.SP_IDUSER, getResponse.getId());
                sessionManager.saveSPString(SessionManager.SP_FULLNAME, getResponse.getNama());
                sessionManager.saveSPInt(SessionManager.SP_WITEL, getResponse.getWitel());
                sessionManager.saveSPInt(SessionManager.SP_STO, getResponse.getSto());
                sessionManager.saveSPString(SessionManager.SP_NAMASTO, getResponse.getNama_sto());
                sessionManager.saveSPString(SessionManager.SP_ALAMAT, getResponse.getAlamat());
                sessionManager.saveSPLong(SessionManager.SP_HANDPHONE, getResponse.getHandphone());
                sessionManager.saveSPString(SessionManager.SP_FOTO, getResponse.getFoto());
                sessionManager.saveSPInt(SessionManager.SP_PRIVILEGES, getResponse.getPrivileges());
                sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGINADMIN, false);
                sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGINMANAGER, false);
                sessionManager.saveSPBoolean(SessionManager.SP_ALREADY_LOGINPETUGAS, true);
                startActivity(new Intent(getApplicationContext(), MainUserActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                break;
            default:
                Toast.makeText(LoginActivity.this, "Akses tidak diketahui", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void failedLogin(String message) {
        mDialog.dismiss();
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        //Klik 2x untuk keluar
        if (mBackPressed + TIME_INTERVAL > System.currentTimeMillis())
        {
            finishAffinity();
        }
        else {
            Toast.makeText(getBaseContext(), ENVIRONMENT.BACKPRESSED_MESSAGE, Toast.LENGTH_SHORT).show();
        }
        mBackPressed = System.currentTimeMillis();
    }
}
