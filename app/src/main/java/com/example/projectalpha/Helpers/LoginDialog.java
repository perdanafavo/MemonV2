package com.example.projectalpha.Helpers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatDialogFragment;

import org.jetbrains.annotations.NotNull;

public class LoginDialog extends AppCompatDialogFragment {
    //status
    //0=username/password salah
    //1=username kosong
    //2=password kosong
    private static int status;

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (status==0) {
            builder.setTitle("Informasi")
                    .setMessage("Login Gagal")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
        }
        else if(status==1){
            builder.setTitle("Informasi")
                    .setMessage("Username tidak boleh kosong!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
        }
        else if(status==2){
            builder.setTitle("Informasi")
                    .setMessage("Password tidak boleh kosong!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
        }
        return builder.create();
    }
    public static void paketLogin(int status){
        LoginDialog.status=status;
    }
}