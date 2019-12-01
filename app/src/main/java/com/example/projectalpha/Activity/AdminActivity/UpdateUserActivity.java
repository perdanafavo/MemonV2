package com.example.projectalpha.Activity.AdminActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.CustomCompatActivity;
import com.example.projectalpha.Helpers.ImageHandle;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.Models.SubModels.WitelData;
import com.example.projectalpha.Presenter.ApplicationPresenter;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.ApplicationViews;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UpdateUserActivity extends CustomCompatActivity
        implements ApplicationViews, ApplicationViews.UsersViews, ApplicationViews.UsersViews.GetRequest, ApplicationViews.UsersViews.UpdateRequest, ApplicationViews.UploadWithImage
            , ApplicationViews.WitelViews.GetRequest, ApplicationViews.StoViews.GetRequest{

    private EditText editNama, editNoHP, editAlamat;
    private Spinner spinnerWitel, spinnerSTO;
    private ProgressDialog mDialog;
    private Button btnUpdate;
    private ImageButton btnFoto;

    private ApplicationPresenter applicationPresenter;

    private UsersData usersData;
    private List<WitelData> dataWitel;
    private List<STOData> dataSTO;
    private ImageHandle imageHandle;
    private Map<String, String> requestBodyMap = new HashMap<>();
    Bitmap compress;
    private File file;
    private int IDWITEL, IDSTO;
    private String IDPETUGAS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        setVariable();
        createView();
        setBtnFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == ENVIRONMENT.REQUEST_LOAD_IMG){
            try{
                if (data.getData() != null){
                    if(compress != null) compress.recycle();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), data.getData());
                    compress = Bitmap.createScaledBitmap(bitmap, 1200, 1800, true);
                    file = imageHandle.fileCreate(compress);
                    bitmap.recycle();
                } else {
                    simpleToast("Terjadi Kesalahan");
                }
            }catch (IOException e) {
                simpleToast("Terjadi kesalahan");
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        selectedImage();
    }

    private void setVariable(){
        imageHandle = new ImageHandle(UpdateUserActivity.this);
        applicationPresenter = new ApplicationPresenter(UpdateUserActivity.this);

        editNama = findViewById(R.id.editNama);
        editNoHP = findViewById(R.id.editNoHP);
        editAlamat = findViewById(R.id.editAlamat);
        spinnerWitel = findViewById(R.id.spinnerWitel);
        spinnerSTO = findViewById(R.id.spinnerSTO);
        btnFoto = findViewById(R.id.btnFoto);
        btnUpdate = findViewById(R.id.btnUpdate);

        IDPETUGAS = getIntent().getStringExtra(ENVIRONMENT.ID_PETUGAS);
        Picasso.get().setLoggingEnabled(false);
        Picasso.get().setIndicatorsEnabled(false);
    }
    private void createView(){
        mDialog = new ProgressDialog(UpdateUserActivity.this);
        mDialog.setMessage(ENVIRONMENT.NO_WAITING_MESSAGE);
        mDialog.setCancelable(false);
        mDialog.setIndeterminate(true);
        mDialog.show();

        applicationPresenter.getUsersByID();

        setBtnUpdate();
    }
    private void setSpinnerWitel(){List<String> itemWitel = new ArrayList<>();
        itemWitel.add(0, "PILIH WITEL");
        for (WitelData data:dataWitel){
            itemWitel.add(data.getId(), data.getNama());
        }

        ArrayAdapter<String> arrayAdapterWitel = new ArrayAdapter<>(UpdateUserActivity.this, R.layout.spinner_item, itemWitel);
        arrayAdapterWitel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerWitel.setAdapter(arrayAdapterWitel);
        spinnerWitel.setSelection(usersData.getWitel());
        spinnerWitel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IDWITEL = (int) parent.getItemIdAtPosition(position);
                setSpinnerSTO();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void setSpinnerSTO(){
        List<String> itemSTO = new ArrayList<>();
        final List<Integer> idSTO = new ArrayList<>();
        itemSTO.add(0, "PILIH STO");
        idSTO.add(0, 0);
        for (STOData data:dataSTO){
            if (data.getWitel() == IDWITEL){
                idSTO.add(data.getId());
                itemSTO.add(data.getNama());
            }
        }
        ArrayAdapter<String> arrayAdapterSto = new ArrayAdapter<>(UpdateUserActivity.this, R.layout.spinner_item, itemSTO);
        arrayAdapterSto.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSTO.setAdapter(arrayAdapterSto);
        spinnerSTO.setSelection(arrayAdapterSto.getPosition(usersData.getNama_sto()));
        spinnerSTO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IDSTO = idSTO.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mDialog.dismiss();
    }
    private void selectedImage(){
        if (compress != null){
            Bitmap bitmap = Bitmap.createScaledBitmap(compress, 400, 600, true);
            btnFoto.setImageBitmap(Bitmap.createScaledBitmap(bitmap, 120, 180, false));
            btnFoto.setBackgroundResource(R.color.colorGreyLight);
        }
        btnFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHandle.directoryImage();
            }
        });
    }
    private void setBtnUpdate(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editNama.getText().toString().matches("")||editNoHP.getText().toString().matches("")){
                    Toast.makeText(getApplicationContext(), "Data masih ada yang kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    new AlertDialog.Builder(UpdateUserActivity.this)
                        .setMessage("Apa anda yakin ingin mengupdate user?")
                            .setTitle("Konfirmasi Update")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    updateUsers();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            }).create().show();
                }
            }
        });
    }
    private void updateUsers(){
        mDialog.show();
        requestBodyMap.clear();
        String handphone = "0"+usersData.getHandphone();
        if (!editNama.getText().toString().equals(usersData.getNama()))requestBodyMap.put("nama", editNama.getText().toString());
        if (!editNoHP.getText().toString().equals(handphone))requestBodyMap.put("handphone", editNoHP.getText().toString());
        if (!editAlamat.getText().toString().equals(usersData.getAlamat()))requestBodyMap.put("alamat", editAlamat.getText().toString());
        if (IDSTO != usersData.getSto())requestBodyMap.put("sto", String.valueOf(IDSTO));

        if (!requestBodyMap.isEmpty()){
            if (compress == null) applicationPresenter.updateUsers();
            else applicationPresenter.updateImage(false);
        } else {
            if (compress != null) {
                applicationPresenter.updateImage(false);
            } else {
                mDialog.dismiss();
                simpleToast("Tidak ada data yang diubah");
            }
        }
    }
    private void setBtnFooter() {
        backClick(UpdateMenuUserActivity.class);
        outClick();
        homeClick(MainAdminActivity.class);
    }

    @Override
    public void requestFailled(String message) {
        mDialog.dismiss();
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void successRequest() {
        mDialog.dismiss();
        new AlertDialog.Builder(UpdateUserActivity.this)
                .setMessage("Berhasil merubah users")
                .setTitle("Informasi ubah")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(
                                new Intent(UpdateUserActivity.this, UpdateMenuUserActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK)
                        );
                    }
                }).create().show();
    }

    @Override
    public int getStoArea() {
        return 0;
    }

    @Override
    public String getEmployee() {
        return IDPETUGAS;
    }

    @Override
    public void SuccessRequestGetUsers(ArrayList<UsersData> data) {
        usersData = data.get(0);
        String Handphone = "0"+usersData.getHandphone();
        applicationPresenter.getWitel();
        editNama.setText(usersData.getNama());
        editNoHP.setText(Handphone);
        editAlamat.setText(usersData.getAlamat());
        if(compress == null)Picasso.get().load(ENVIRONMENT.PROFILE_IMAGE+usersData.getFoto())
                .error(R.drawable.ic_take_picture)
                .resize(ENVIRONMENT.IMAGE_SCALE_WIDTH, ENVIRONMENT.IMAGE_SCALE_HEIGHT)
                .networkPolicy(NetworkPolicy.NO_CACHE, NetworkPolicy.NO_STORE)
                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
                .into(btnFoto);
    }

    @Override
    public Map<String, String> getRequestMapBody() {
        return requestBodyMap;
    }

    @Override
    public void successPostImage(String nama, boolean index) {
        if (!requestBodyMap.isEmpty()){
            applicationPresenter.updateUsers();
        } else {
            mDialog.dismiss();
            new AlertDialog.Builder(UpdateUserActivity.this)
                    .setMessage("Berhasil merubah users")
                    .setTitle("Informasi ubah")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(
                                    new Intent(UpdateUserActivity.this, UpdateMenuUserActivity.class)
                                            .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK)
                            );
                        }
                    }).create().show();
        }
    }

    @Override
    public Map<String, RequestBody> GetRequestBody() {
        Map<String, RequestBody> MapRequstBody = new HashMap<>();
        MapRequstBody.put("id", RequestBody.create(IDPETUGAS, okhttp3.MultipartBody.FORM));
        return MapRequstBody;
    }

    @Override
    public MultipartBody.Part GetMultiPart(boolean index) {
        return imageHandle.convertMultiparFile(file);
    }

    @Override
    public void SuccessRequestGetWitel(List<WitelData> data) {
        dataWitel = data;
        applicationPresenter.getAllSto();
    }

    @Override
    public void SucessRequestGetSTO(List<STOData> data) {
        dataSTO = data;
        setSpinnerWitel();
    }
}
