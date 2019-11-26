package com.example.projectalpha.Presenter;

import com.example.projectalpha.Models.UsersModels;
import com.example.projectalpha.Network.RetrofitConnect;
import com.example.projectalpha.Views.LoginViews;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPresenter {

    private LoginViews loginViews;

    public LoginPresenter(LoginViews loginViews){
        this.loginViews = loginViews;
    }

    public void getUsersLogin(){
        String Username = loginViews.getUsername();
        String Password = loginViews.getPassword();

        RetrofitConnect.getInstance()
                .getUsersLogin(true, Username, Password)
                .enqueue(new Callback<UsersModels>() {
                    @Override
                    public void onResponse(@NotNull Call<UsersModels> call, @NotNull Response<UsersModels> response) {
                        if (response.isSuccessful()){
                            assert response.body() != null;
                            boolean status = response.body().getStatus();
                            if (status){
                                loginViews.successLogin(response.body().getData());
                            } else {
                                loginViews.failedLogin(response.body().getMessage());
                            }
                        } else{
                            loginViews.failedLogin("Username atau Password Salah");
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<UsersModels> call, @NotNull Throwable t) {
                        t.printStackTrace();
                        loginViews.failedLogin("Terjadi Kesalahan, Coba lagi nanti");
                    }
                });
    }
}
