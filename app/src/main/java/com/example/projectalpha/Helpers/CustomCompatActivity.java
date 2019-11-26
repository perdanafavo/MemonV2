package com.example.projectalpha.Helpers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.projectalpha.Activity.LoginActivity;
import com.example.projectalpha.Activity.ManagerActivity.HistoryActivity;
import com.example.projectalpha.R;

public class CustomCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void backClick(){
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    protected void backClick(final Class destination){
        findViewById(R.id.btnBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleIntent(destination);
            }
        });
    }

    protected void homeClick(final Class destination){
        findViewById(R.id.btnHome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleIntent(destination);
            }
        });
    }

    protected void outClick(){
        findViewById(R.id.btnOut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outDialogMessage();
            }
        });
    }

    protected void historyClick(){
        findViewById(R.id.btnHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleIntent(HistoryActivity.class);
            }
        });
    }

    protected void outDialogMessage(){
        new AlertDialog.Builder(getWindow().getContext())
                .setMessage("Apa anda yakin ingin keluar?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new SessionManager(getApplicationContext()).clearSP();
                        simpleIntent(LoginActivity.class);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }

    protected void simpleIntent(Class destination){
        startActivity(new Intent(getApplicationContext(), destination)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
    }

    protected void simpleIntent(){
        finish();
    }

    protected void simpleToast(String message){
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    protected void simpleToast(String message, int longTime){
        Toast.makeText(getApplicationContext(), message, longTime).show();
    }
}
