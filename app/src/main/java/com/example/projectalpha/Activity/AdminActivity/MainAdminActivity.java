package com.example.projectalpha.Activity.AdminActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.R;

public class MainAdminActivity extends CustomCompatActivity {

    private Button btnCreateUser, btnUpdateUser, btnResetUser, btnUpdateContact;

    private static final int TIME_INTERVAL = 2000;
    private long mBackPressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setVariable(){
        btnCreateUser = findViewById(R.id.btnCreateUser);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);
        btnResetUser = findViewById(R.id.btnResetUser);
        btnUpdateContact = findViewById(R.id.btnUpdateContact);
    }

    private void createView(){
        btnCreateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleIntent(CreateUserActivity.class);
            }
        });

        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleIntent(UpdateMenuUserActivity.class);
            }
        });

        btnResetUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleIntent(ResetMenuUserActivity.class);
            }
        });

        btnUpdateContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleIntent(UpdateMenuContactActivity.class);
            }
        });
    }

    private void setBtnFooter(){
        outClick();
    }

    @Override
    public void onBackPressed() {
        //Klik 2x untuk keluar
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
