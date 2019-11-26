package com.example.projectalpha.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.projectalpha.Activity.AdminActivity.MainAdminActivity;
import com.example.projectalpha.Activity.ManagerActivity.MainManagerActivity;
import com.example.projectalpha.Activity.UsersActivity.MainUserActivity;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.R;

public class Splashscreen extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);

        sessionManager = new SessionManager(getApplicationContext());
        handler();
    }

    private void handler(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sessionManager.getSpAlreadyLoginManager()) {
                    startActivity(new Intent(getApplicationContext(), MainManagerActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (sessionManager.getSpAlreadyLoginPetugas()) {
                    startActivity(new Intent(getApplicationContext(), MainUserActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else if (sessionManager.getSpAlreadyLoginAdmin()) {
                    startActivity(new Intent(getApplicationContext(), MainAdminActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                }
            }
        }, 3000);
    }
}
