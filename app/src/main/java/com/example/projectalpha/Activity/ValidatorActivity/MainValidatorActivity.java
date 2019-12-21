package com.example.projectalpha.Activity.ValidatorActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.example.projectalpha.Adapter.PagerFragmentAdapter;
import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Fragment.BelumValidasiFragment;
import com.example.projectalpha.Fragment.SudahValidasiFragment;
import com.example.projectalpha.Helpers.CekKoneksi;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.example.projectalpha.Views.ValidatorViews;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainValidatorActivity extends CustomCompatActivity implements ApplicationViews, ApplicationViews.ReportViews, ApplicationViews.ReportViews.GetReportValidator, ApplicationViews.ReportViews.GetRequestValidator, ValidatorViews {
    private SessionManager sessionManager;
    private ApplicationPresenter applicationPresenter;
    private ViewPager viewPager;
    private ProgressDialog mDialog = null;
    private ImageView ivUser;
    private TextView tvNama, tvHandphone, tvSTO;
    private int pageIndex =0;
    private TabLayout tabLayout;
    private List<LaporanData> mapLaporanValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_validator);

        setVariable();
        createView();
        setBtnFooter();
    }

    private void setBtnFooter() {
        super.outClick();
    }

    private void setVariable(){
        mDialog = new ProgressDialog(MainValidatorActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);

        sessionManager = new SessionManager(getApplicationContext());
        viewPager = findViewById(R.id.viewPagerValidator);
        applicationPresenter = new ApplicationPresenter(MainValidatorActivity.this);

        tvNama = findViewById(R.id.txtNama);
        tvHandphone = findViewById(R.id.txtHandphone);
        tvSTO = findViewById(R.id.txtSTO);
        ivUser = findViewById(R.id.ivUser);
        tabLayout = findViewById(R.id.tabValidator);

        Picasso.get().setLoggingEnabled(false);
        Picasso.get().setIndicatorsEnabled(false);
    }

    private void createView(){
        String Handphone = "0" + sessionManager.getSpHandphone();
        tvNama.setText(sessionManager.getSpFullname());
        tvHandphone.setText(Handphone);
        tvSTO.setText(sessionManager.getSpNamaSTO());

        Picasso.get().load(ENVIRONMENT.PROFILE_IMAGE + sessionManager.getSpFoto())
                .resize(380, 510)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(ivUser);

        mDialog.show();
        if (!CekKoneksi.isConnectedToInternet(getBaseContext())) {
            mDialog.dismiss();
            simpleToast(ENVIRONMENT.NO_INTERNET_CONNECTION, Toast.LENGTH_LONG);
        } else {
            getRequest();
        }
    }

    private void configViewPager(@NonNull ViewPager viewPager){
        PagerFragmentAdapter pagerFragmentAdapter = new PagerFragmentAdapter(getSupportFragmentManager());
        pagerFragmentAdapter.addFragment(new BelumValidasiFragment(), getString(R.string.belum_validasi));
        pagerFragmentAdapter.addFragment(new SudahValidasiFragment(), getString(R.string.sudah_validasi));
        viewPager.setAdapter(pagerFragmentAdapter);
    }

    private void getRequest(){
        applicationPresenter.requestLaporanBySTO();
    }

    @Override
    protected void onResume() {
        super.onResume();
        createView();
        pageIndex = tabLayout.getSelectedTabPosition();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void requestFailled(String message) {
        simpleToast(message);
        mDialog.dismiss();
    }

    @Override
    public void successRequest() {

    }

    @Override
    public int getIndexReport() {
        return 0;
    }

    @Override
    public int getIndexSto() {
        return sessionManager.getSpSTO();
    }

    @Override
    public void SuccessRequestGetValidator(List<LaporanData> dataValidator) {
        mapLaporanValidator = dataValidator;
        configViewPager(viewPager);
        viewPager.setCurrentItem(pageIndex);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setScrollPosition(pageIndex, 0f, true);
        mDialog.dismiss();
    }

    @Override
    public List<LaporanData> getValidasiStatus() {
        return this.mapLaporanValidator;
    }

    @Override
    public String getNamaSTO() {
        return sessionManager.getSpNamaSTO();
    }

    @Override
    public void refreshValidator() {
        mDialog.show();
        onResume();
    }
}
