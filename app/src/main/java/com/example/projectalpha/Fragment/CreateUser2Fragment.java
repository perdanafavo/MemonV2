package com.example.projectalpha.Fragment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.projectalpha.R;
import com.example.projectalpha.Views.CreateUserViews;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import okhttp3.RequestBody;

public class CreateUser2Fragment extends Fragment{

    private Button btnCreate;
    private EditText editUsername, editPassword, editKonfirmasi;

    private CreateUserViews createUserViews;

    private Map<String, RequestBody> param;

    public CreateUser2Fragment() {

    }


    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        createUserViews = (CreateUserViews) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_user2, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editUsername = view.findViewById(R.id.editUsernameNew);
        editPassword = view.findViewById(R.id.editPasswordNew);
        editKonfirmasi = view.findViewById(R.id.editKonfirmNew);
        btnCreate = view.findViewById(R.id.btnCreate);
        add();
    }

    @Override
    public void onResume() {
        super.onResume();
        param = createUserViews.getUsersData();
        getActivity().findViewById(R.id.backWrap).setVisibility(View.VISIBLE);
    }

    private void add(){
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editUsername.getText().toString().matches("")||editPassword.getText().toString().matches("")||editKonfirmasi.getText().toString().matches("")){
                    Toast.makeText(getActivity().getApplicationContext(), "Data masih ada yang kosong", Toast.LENGTH_SHORT).show();
                }
                else if(!editPassword.getText().toString().matches(editKonfirmasi.getText().toString())){
                    Toast.makeText(getActivity().getApplicationContext(), "Konfirmasi password tidak sesuai", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Apa anda yakin ingin menambahkan user?")
                            .setTitle("Konfirmasi Tambah")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    param.put("username", createUserViews.createPartFromString(editUsername.getText().toString()));
                                    param.put("password", createUserViews.createPartFromString(editPassword.getText().toString()));
                                    createUserViews.actionAddUsers(param, true);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }
}
