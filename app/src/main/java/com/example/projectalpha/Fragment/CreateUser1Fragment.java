package com.example.projectalpha.Fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Helpers.ImageHandle;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Models.SubModels.WitelData;
import com.example.projectalpha.R;
import com.example.projectalpha.Views.CreateUserViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;

public class CreateUser1Fragment extends Fragment{

    private Button btnSelanjutnya;
    private ImageButton btnImage;
    private Spinner spinnerWitel, spinnerSTO;
    private EditText editNama, editNoHP, editAlamat;
    private ImageHandle imageHandle;

    private CreateUserViews createUserViews;

    private List<WitelData> dataWitel;
    private List<STOData> dataSTO;

    private Map<String, RequestBody> param;
    private int IDWITEL = 0, IDSTO = 0;

    public CreateUser1Fragment() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        createUserViews = (CreateUserViews) context;
        dataWitel = createUserViews.getDataWitel();
        dataSTO = createUserViews.getDataSTO();
        imageHandle = new ImageHandle(getActivity());
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_user1, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSelanjutnya = view.findViewById(R.id.btnSelanjutnya);
        spinnerWitel  = view.findViewById(R.id.spinnerWitel);
        spinnerSTO  = view.findViewById(R.id.spinnerSTO);
        editNama = view.findViewById(R.id.inputNama);
        editNoHP = view.findViewById(R.id.inputHandphone);
        editAlamat = view.findViewById(R.id.inputAlamat);
        btnImage = view.findViewById(R.id.btnFoto);
        setSpinnerWitel();
        setSpinnerSTO();
        next();
    }

    @Override
    public void onResume() {
        super.onResume();
        param = createUserViews.getUsersData();
        getActivity().findViewById(R.id.backWrap).setVisibility(View.GONE);
        selectedImage();
    }

    private void next() {
        btnSelanjutnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editNama.getText().toString().matches("")||editNoHP.getText().toString().matches("")||editAlamat.getText().toString().matches("")){
                    Toast.makeText(getActivity().getApplicationContext(), "Data masih ada yang kosong", Toast.LENGTH_SHORT).show();
                } else {
                    if (IDSTO == 0){
                        Toast.makeText(getActivity().getApplicationContext(), "Data masih ada yang kosong", Toast.LENGTH_SHORT).show();
                    }else {
                        if (createUserViews.getBitmap() == null){
                            Toast.makeText(getActivity().getApplicationContext(), "Data masih ada yang kosong", Toast.LENGTH_SHORT).show();
                        } else {
                            param.put("sto", createUserViews.createPartFromString(String.valueOf(IDSTO)));
                            param.put("nama", createUserViews.createPartFromString(editNama.getText().toString()));
                            param.put("alamat", createUserViews.createPartFromString(editAlamat.getText().toString()));
                            param.put("handphone", createUserViews.createPartFromString(editNoHP.getText().toString()));
                            createUserViews.actionAddUsers(param, false);
                            getFragmentManager().beginTransaction()
                                    .replace(R.id.fragmentWrap, new CreateUser2Fragment(), ENVIRONMENT.CREATE_USER_FORM_KEDUA)
                                    .addToBackStack(ENVIRONMENT.CREATE_USER_FORM_KEDUA)
                                    .commit();
                        }
                    }
                }
            }
        });
    }

    private void setSpinnerWitel(){
        List<String> itemWitel = new ArrayList<>();
        itemWitel.add(0, "PILIH WITEL");
        for (WitelData data:dataWitel){
            itemWitel.add(data.getId(), data.getNama());
        }

        ArrayAdapter<String> arrayAdapterWitel = new ArrayAdapter<>(getContext(), R.layout.spinner_item, itemWitel);
        arrayAdapterWitel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerWitel.setAdapter(arrayAdapterWitel);
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
        ArrayAdapter<String> arrayAdapterWitel = new ArrayAdapter<>(getContext(), R.layout.spinner_item, itemSTO);
        arrayAdapterWitel.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerSTO.setAdapter(arrayAdapterWitel);
        spinnerSTO.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                IDSTO = idSTO.get(position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void selectedImage(){
        if (createUserViews.getBitmap() != null){
            Bitmap bitmap = Bitmap.createScaledBitmap(createUserViews.getBitmap(), 400, 600, true);
            btnImage.setImageBitmap(Bitmap.createScaledBitmap(bitmap, ENVIRONMENT.IMAGE_SCALE_WIDTH, ENVIRONMENT.IMAGE_SCALE_HEIGHT, false));
            btnImage.setBackgroundResource(R.color.colorGreyLight);
        }
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageHandle.directoryImage();
            }
        });
    }
}
