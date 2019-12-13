package com.example.projectalpha.Activity.AdminActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.projectalpha.Activity.UsersActivity.MainUserActivity;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;

import java.util.HashMap;
import java.util.Map;

public class ResetUserActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.UsersViews, ApplicationViews.UsersViews.UpdateRequest {

    private ProgressDialog mDialog;
    private String IDPETUGAS, Username;
    private TextView txtUserName;
    private EditText editNewPassword,editKonfirm;
    private Button btnReset;

    private ApplicationPresenter applicationPresenter;
    private SessionManager sessionManager;

    private Map<String, String> requestBodyMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_user);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setVariable() {
        mDialog = new ProgressDialog(ResetUserActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        applicationPresenter = new ApplicationPresenter(ResetUserActivity.this);
        sessionManager = new SessionManager(getApplicationContext());

        txtUserName = findViewById(R.id.txtUsernameReset);
        editNewPassword = findViewById(R.id.editNewPassword);
        editKonfirm = findViewById(R.id.editKonfirm);
        btnReset = findViewById(R.id.btnReset);

        Username = getIntent().getStringExtra(ENVIRONMENT.USERNAME_PETUGAS);
        IDPETUGAS = getIntent().getStringExtra(ENVIRONMENT.ID_PETUGAS);
    }

    private void createView() {
        if (sessionManager.getSpPrivileges()==3){
            txtUserName.setText(sessionManager.getSpUsername());
        } else {
            txtUserName.setText(Username);
        }

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword(){
        requestBodyMap.clear();
        if (!editNewPassword.getText().toString().matches("")){
            if (editNewPassword.getText().toString().equals(editKonfirm.getText().toString())){
                mDialog.show();
                requestBodyMap.put("password", editNewPassword.getText().toString());
                applicationPresenter.updateUsers();
            } else {
                simpleToast("Konfirmasi tidak sama");
            }
        } else {
            simpleToast("Password masih kosong");
        }
    }

    private void setBtnFooter(){
        backClick();
        if (sessionManager.getSpPrivileges()==1)
        {
            homeClick(MainAdminActivity.class);
        }
        else if (sessionManager.getSpPrivileges()==3)
        {
            homeClick(MainUserActivity.class);
        }
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
        new AlertDialog.Builder(ResetUserActivity.this)
                .setMessage("Berhasil mereset password")
                .setTitle("Informasi ubah")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        simpleIntent();
                    }
                }).create().show();
    }

    @Override
    public int getStoArea() {
        return 0;
    }

    @Override
    public String getEmployee() {
        if(sessionManager.getSpPrivileges()==3){
            return sessionManager.getSpIduser();
        }else {
            return IDPETUGAS;
        }
    }

    @Override
    public Map<String, String> getRequestMapBody() {
        return requestBodyMap;
    }
}
