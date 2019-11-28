package com.example.projectalpha.Activity.AdminActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Fragment.CreateUser1Fragment;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.ImageHandle;
import com.example.projectalpha.Helpers.SessionManager;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Models.SubModels.WitelData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.example.projectalpha.Views.CreateUserViews;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class CreateUserActivity extends CustomCompatActivity
        implements CreateUserViews ,ApplicationViews ,ApplicationViews.WitelViews.GetRequest, ApplicationViews.StoViews.GetRequest, ApplicationViews.UploadWithImage {

    private String PRIVILEGES_SELECT;

    private FragmentManager fragmentManager;
    private ProgressDialog mDialog;

    private SessionManager sessionManager;
    private ImageHandle imageHandle;

    private ApplicationPresenter applicationPresenter;
    private List<WitelData> dataWitel;
    private List<STOData> dataSto;

    private Map<String, RequestBody> param;
    Bitmap compress = null;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);
        if (savedInstanceState != null) return;

        setVariable();
        requestData();
        setBtnFooter();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fragmentManager.getFragments().size() == 0){
            finish();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ENVIRONMENT.REQUEST_LOAD_IMG){
            try{
                if (data.getData() != null){
                    if (compress != null)compress.recycle();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    compress = Bitmap.createScaledBitmap(bitmap, 1200, 1800, true);
                    file = imageHandle.fileCreate(compress);
                    bitmap.recycle();
                } else {
                    simpleToast("Terjadi Kesalahan");
                }
            }catch (IOException e) {
                simpleToast("Terjadi Kesalahan");
                e.printStackTrace();
            }
        }
    }

    private void setVariable() {
        mDialog = new ProgressDialog(CreateUserActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();

        PRIVILEGES_SELECT = String.valueOf(getIntent().getIntExtra(ENVIRONMENT.PREVILAGE_SELECT, 3));

        param = new HashMap<>();

        fragmentManager = getSupportFragmentManager();
        imageHandle = new ImageHandle(CreateUserActivity.this);

        applicationPresenter = new ApplicationPresenter(CreateUserActivity.this);
        sessionManager = new SessionManager(CreateUserActivity.this);
    }

    private void requestData() {
        applicationPresenter.getWitel();
    }

    private void createView() {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentWrap, new CreateUser1Fragment(), ENVIRONMENT.CREATE_USER_FORM_PERTAMA);
        fragmentTransaction.addToBackStack(ENVIRONMENT.CREATE_USER_FORM_PERTAMA);
        fragmentTransaction.commit();
        mDialog.dismiss();
    }
    private void setBtnFooter() {
        outClick();
        homeClick(MainAdminActivity.class);
        backClick();
    }

    @Override
    @NotNull
    public RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(okhttp3.MultipartBody.FORM, descriptionString);
    }

    @Override
    public Map<String, RequestBody> getUsersData() {
        return param;
    }

    @Override
    public Bitmap getBitmap() {
        return compress;
    }

    @Override
    public List<WitelData> getDataWitel() {
        return dataWitel;
    }

    @Override
    public List<STOData> getDataSTO() {
        return dataSto;
    }

    @Override
    public void actionAddUsers(Map<String, RequestBody> param, boolean action) {
        this.param = param;
        if (action){
            mDialog.show();
            this.param.put("admin", createPartFromString(sessionManager.getSpIduser()));
            this.param.put("privileges", createPartFromString(PRIVILEGES_SELECT));
            applicationPresenter.postUser();
        }
    }

    @Override
    public void SuccessRequestGetWitel(List<WitelData> data) {
        dataWitel = data;
        applicationPresenter.getAllSto();
    }

    @Override
    public void SucessRequestGetSTO(List<STOData> data) {
        dataSto = data;
        createView();
    }

    @Override
    public void requestFailled(String message) {
        simpleToast(message);
        mDialog.dismiss();
    }

    @Override
    public void successRequest() {
        mDialog.dismiss();
        new AlertDialog.Builder(CreateUserActivity.this)
                .setMessage(ENVIRONMENT.DIALOG_ADD_USERS_MESSAGE)
                .setTitle(ENVIRONMENT.DIALOG_ADD_USERS_TITLE)
                .setCancelable(false)
                .setPositiveButton(ENVIRONMENT.DIALOG_BUTTON_SELESAI, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).create().show();
    }

    @Override
    public void successPostImage(String nama, boolean index) {

    }

    @Override
    public Map<String, RequestBody> GetRequestBody() {
        return param;
    }

    @Override
    public MultipartBody.Part GetMultiPart(boolean index) {
        return imageHandle.convertMultiparFile(file);
    }
}
