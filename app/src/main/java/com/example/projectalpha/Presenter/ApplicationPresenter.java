package com.example.projectalpha.Presenter;

import android.content.Context;
import android.util.Log;

import com.example.projectalpha.Config.ENVIRONMENT;
import com.example.projectalpha.Models.BIRModels;
import com.example.projectalpha.Models.FuelModels;
import com.example.projectalpha.Models.ImageModel;
import com.example.projectalpha.Models.KondisiUmumModels;
import com.example.projectalpha.Models.LaporanModels;
import com.example.projectalpha.Models.OthersModels;
import com.example.projectalpha.Models.PowerModels;
import com.example.projectalpha.Models.ReportStatusModels;
import com.example.projectalpha.Models.STOModels;
import com.example.projectalpha.Models.StatusModels;
import com.example.projectalpha.Models.UsersModels;
import com.example.projectalpha.Models.WitelModels;
import com.example.projectalpha.Network.RetrofitConnect;
import com.example.projectalpha.Views.ApplicationViews;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApplicationPresenter {

    private ApplicationViews applicationViews;

    private ApplicationViews.UsersViews usersViews;

    private ApplicationViews.UsersViews.GetRequest getRequest;
    private ApplicationViews.UsersViews.UpdateRequest updateRequest;

    private ApplicationViews.ReportViews reportViews;
    private ApplicationViews.ReportViews.GetRequestReport getRequestReport;
    private ApplicationViews.ReportViews.PostRequest postRequestReport;
    private ApplicationViews.ReportViews.GetGeneralRequest getGeneralRequest;
    private ApplicationViews.ReportViews.GetPowerRequest getPowerRequest;
    private ApplicationViews.ReportViews.GetFuelRequest getFuelRequest;
    private ApplicationViews.ReportViews.GetRoomRequest getRoomRequest;
    private ApplicationViews.ReportViews.GetOthersRequest getOthersRequest;
    private ApplicationViews.ReportViews.GetReportStatusRequest getReportStatusRequest;
    private ApplicationViews.ReportViews.GetReportParam getReportParam;
    private ApplicationViews.ReportViews.GetReportValidator getReportValidator;
    private ApplicationViews.ReportViews.GetRequestValidator getRequestValidator;
    private ApplicationViews.StatusReport statusReport;
    private ApplicationViews.StatusReport.GetAreaStatusRequest getAreaStatusRequest;
    private ApplicationViews.StatusReport.GetTemperatureRequest getTemperatureStatusRequest;
    private ApplicationViews.StatusReport.GetFuelRequest getFuelStatusRequest;
    private ApplicationViews.StatusReport.GetPowerRequest getPowerStatusRequest;
    private ApplicationViews.ReportViews.PutValidator putValidatorInput;
    private ApplicationViews.ReportViews.PutValidator.Notification Notification;

    private ApplicationViews.WitelViews.GetRequest getRequestWitel;

    private ApplicationViews.StoViews.GetRequest getRequestSto;

    private ApplicationViews.UploadWithImage uploadWithImage;

    public ApplicationPresenter(Context context) {
        if (context instanceof ApplicationViews) applicationViews = (ApplicationViews) context;

        if (context instanceof ApplicationViews.UsersViews) usersViews = (ApplicationViews.UsersViews) context;
        if (context instanceof ApplicationViews.UsersViews.GetRequest) getRequest = (ApplicationViews.UsersViews.GetRequest) context;
        if (context instanceof ApplicationViews.UsersViews.UpdateRequest) updateRequest = (ApplicationViews.UsersViews.UpdateRequest) context;

        if (context instanceof ApplicationViews.ReportViews) reportViews = (ApplicationViews.ReportViews) context;
        if (context instanceof ApplicationViews.ReportViews.GetRequestReport) getRequestReport = (ApplicationViews.ReportViews.GetRequestReport) context;
        if (context instanceof ApplicationViews.ReportViews.PostRequest) postRequestReport = (ApplicationViews.ReportViews.PostRequest) context;
        if (context instanceof ApplicationViews.ReportViews.GetGeneralRequest) getGeneralRequest = (ApplicationViews.ReportViews.GetGeneralRequest) context;
        if (context instanceof ApplicationViews.ReportViews.GetPowerRequest) getPowerRequest = (ApplicationViews.ReportViews.GetPowerRequest) context;
        if (context instanceof ApplicationViews.ReportViews.GetFuelRequest) getFuelRequest = (ApplicationViews.ReportViews.GetFuelRequest) context;
        if (context instanceof ApplicationViews.ReportViews.GetRoomRequest) getRoomRequest = (ApplicationViews.ReportViews.GetRoomRequest) context;
        if (context instanceof ApplicationViews.ReportViews.GetOthersRequest) getOthersRequest = (ApplicationViews.ReportViews.GetOthersRequest) context;
        if (context instanceof ApplicationViews.ReportViews.GetReportStatusRequest) getReportStatusRequest = (ApplicationViews.ReportViews.GetReportStatusRequest) context;
        if (context instanceof ApplicationViews.ReportViews.GetReportParam) getReportParam = (ApplicationViews.ReportViews.GetReportParam) context;

        if (context instanceof ApplicationViews.StatusReport)  statusReport = (ApplicationViews.StatusReport) context;
        if (context instanceof ApplicationViews.StatusReport.GetAreaStatusRequest)  getAreaStatusRequest = (ApplicationViews.StatusReport.GetAreaStatusRequest) context;
        if (context instanceof ApplicationViews.StatusReport.GetTemperatureRequest)  getTemperatureStatusRequest = (ApplicationViews.StatusReport.GetTemperatureRequest) context;
        if (context instanceof ApplicationViews.StatusReport.GetFuelRequest)  getFuelStatusRequest = (ApplicationViews.StatusReport.GetFuelRequest) context;
        if (context instanceof ApplicationViews.StatusReport.GetPowerRequest)  getPowerStatusRequest = (ApplicationViews.StatusReport.GetPowerRequest) context;

        if (context instanceof ApplicationViews.WitelViews.GetRequest) getRequestWitel = (ApplicationViews.WitelViews.GetRequest) context;

        if (context instanceof ApplicationViews.StoViews.GetRequest) getRequestSto = (ApplicationViews.StoViews.GetRequest) context;

        if (context instanceof ApplicationViews.UploadWithImage)uploadWithImage = (ApplicationViews.UploadWithImage) context;

        if (context instanceof ApplicationViews.ReportViews.GetReportValidator) getReportValidator = (ApplicationViews.ReportViews.GetReportValidator) context;

        if (context instanceof ApplicationViews.ReportViews.GetRequestValidator) getRequestValidator = (ApplicationViews.ReportViews.GetRequestValidator) context;

        if (context instanceof ApplicationViews.ReportViews.PutValidator) putValidatorInput = (ApplicationViews.ReportViews.PutValidator) context;
    }

    public void deleteReportMonth(){
        RetrofitConnect.getInstance()
                .deleteReportMouth()
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        Log.d("MEMON APLIKASI", "onResponse: Menghapus laporan 1 bulan");
                    }
                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        Log.d("MEMON APLIKASI", "onResponse: Failed");
                    }
                });
    }

    public void getUsers() {
        RetrofitConnect.getInstance()
                .getUsers()
                .enqueue(new Callback<UsersModels>() {
                    @Override
                    public void onResponse(@NotNull Call<UsersModels> call, @NotNull Response<UsersModels> response) {
                        if (response.isSuccessful()){
                            getRequest.SuccessRequestGetUsers(response.body() != null ? response.body().getData() : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<UsersModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }

    public void getUsersByID() {
        RetrofitConnect.getInstance()
                .getUsers(usersViews.getEmployee())
                .enqueue(new Callback<UsersModels>() {
                    @Override
                    public void onResponse(@NotNull Call<UsersModels> call, @NotNull Response<UsersModels> response) {
                        if (response.isSuccessful()) {
                            getRequest.SuccessRequestGetUsers(response.body() != null ? response.body().getData() : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<UsersModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }

    public void getUsersBySTO() {
        RetrofitConnect.getInstance()
                .getUsersBySTO(usersViews.getStoArea(),3)
                .enqueue(new Callback<UsersModels>() {
                    @Override
                    public void onResponse(@NotNull Call<UsersModels> call, @NotNull Response<UsersModels> response) {
                        if (response.isSuccessful()) {
                            getRequest.SuccessRequestGetUsers(response.body() != null ? response.body().getData() : null);
                        }else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<UsersModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }


    public void updateUsers() {
        RetrofitConnect.getInstance()
                .updateUsers(usersViews.getEmployee(), updateRequest.getRequestMapBody())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            applicationViews.successRequest();
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_PUT);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }

    public void updateValidator() {
        RetrofitConnect.getInstance()
                .updateValidator(putValidatorInput.getIndexId(), putValidatorInput.getValidator())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            applicationViews.successRequest();
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_PUT);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void postUser(){
        RetrofitConnect.getInstance()
                .postUsers(uploadWithImage.GetRequestBody(), uploadWithImage.GetMultiPart(true))
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            applicationViews.successRequest();
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_IMAGE);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
         });
    }

    public void deleteUsers() {
        RetrofitConnect.getInstance()
                .deleteUsers(usersViews.getEmployee())
                .enqueue(new Callback<UsersModels>() {
                    @Override
                    public void onResponse(@NotNull Call<UsersModels> call, @NotNull Response<UsersModels> response) {
                        if (response.isSuccessful()){
                            applicationViews.successRequest();
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_DELETE);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<UsersModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }

    public void getReportByID() {
        RetrofitConnect.getInstance()
                .getLaporan(reportViews.getIndexReport(), getReportParam.getDateReport())
                .enqueue(new Callback<LaporanModels>() {
                    @Override
                    public void onResponse(@NotNull Call<LaporanModels> call, @NotNull Response<LaporanModels> response) {
                        if (response.isSuccessful()) {
                            getRequestReport.SuccessRequestGetReport(response.body() != null ? response.body().getData() : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LaporanModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }

    public void requestReportByDate() {
        RetrofitConnect.getInstance()
                .getLaporan(getReportParam.getDateReport(), getReportParam.getIndexWitel(), getReportParam.getIndexShift())
                .enqueue(new Callback<LaporanModels>() {
                    @Override
                    public void onResponse(@NotNull Call<LaporanModels> call, @NotNull Response<LaporanModels> response) {
                        if (response.isSuccessful()){
                            getRequestReport.SuccessRequestGetReport(response.body() != null ? response.body().getData() : null);
                        } else {
                            getRequestReport.SuccessRequestGetReport(null);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<LaporanModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }

    public void requestLaporan(){
        RetrofitConnect.getInstance()
                .getLaporan(getReportParam.getIndexWitel(), getReportParam.getIndexSto(), getReportParam.getIndexShift())
                .enqueue(new Callback<LaporanModels>() {
                    @Override
                    public void onResponse(@NotNull Call<LaporanModels> call, @NotNull Response<LaporanModels> response) {
                        if (response.isSuccessful()){
                            getRequestReport.SuccessRequestGetReport(response.body() != null ? response.body().getData() : null);
                        } else {
                            getRequestReport.SuccessRequestGetReport(null);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<LaporanModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }
    public void requestLaporanBySTO(){
        RetrofitConnect.getInstance()
                .getLaporanValidator(getReportValidator.getIndexSto(), "all")
                .enqueue(new Callback<LaporanModels>() {
                    @Override
                    public void onResponse(@NotNull Call<LaporanModels> call, @NotNull Response<LaporanModels> response) {
                        if (response.isSuccessful()){
                            getRequestValidator.SuccessRequestGetValidator(response.body() != null ? response.body().getData() : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<LaporanModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void postLaporan(){
        RetrofitConnect.getInstance()
                .addLaporan(postRequestReport.getRequestMapBody())
                .enqueue(new Callback<LaporanModels>() {
                    @Override
                    public void onResponse(@NotNull Call<LaporanModels> call, @NotNull Response<LaporanModels> response) {
                        if (response.isSuccessful()){
                            postRequestReport.SuccessPostReport(response.body() != null ? response.body().getData() : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_POST);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<LaporanModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }

    public void Notification(){
        RetrofitConnect.getInstance()
                .Notification(Notification.Notification())
                .enqueue(new Callback<JSONObject>() {
                    @Override
                    public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {

                    }
                    @Override
                    public void onFailure(Call<JSONObject> call, Throwable t) {

                    }
                });
    }

    public void getWitel(){
        RetrofitConnect.getInstance()
                .getWitel()
                .enqueue(new Callback<WitelModels>() {
                    @Override
                    public void onResponse(@NotNull Call<WitelModels> call, @NotNull Response<WitelModels> response) {
                        if (response.isSuccessful()){
                            getRequestWitel.SuccessRequestGetWitel(response.body() != null ? response.body().getData() : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<WitelModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
        });
    }

    public void getStoByWitel() {
        RetrofitConnect.getInstance()
                .getSTOWitel(getReportParam.getIndexWitel())
                .enqueue(new Callback<STOModels>() {
                    @Override
                    public void onResponse(@NotNull Call<STOModels> call, @NotNull Response<STOModels> response) {
                        if (response.isSuccessful()){
                            getRequestSto.SucessRequestGetSTO(response.body() != null ? response.body().getData() : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<STOModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
        });
    }

    public void getAllSto(){
        RetrofitConnect.getInstance()
                .getAllSTO()
                .enqueue(new Callback<STOModels>() {
                    @Override
                    public void onResponse(@NotNull Call<STOModels> call, @NotNull Response<STOModels> response) {
                        if (response.isSuccessful()){
                            getRequestSto.SucessRequestGetSTO(response.body() != null ? response.body().getData() : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<STOModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
        });
    }

    public void getGeneral(){
        RetrofitConnect.getInstance()
                .getGeneralReport(reportViews.getIndexReport())
                .enqueue(new Callback<KondisiUmumModels>() {
                    @Override
                    public void onResponse(@NotNull Call<KondisiUmumModels> call, @NotNull Response<KondisiUmumModels> response) {
                        if (response.isSuccessful()){
                            getGeneralRequest.SuccessRequestGeneral(response.body() != null ? response.body().getData().get(0) : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<KondisiUmumModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
         });
    }

    public void putGeneral(){
        RetrofitConnect.getInstance()
                .putGeneral(postRequestReport.getRequestMapBody())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            applicationViews.successRequest();
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_PUT);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void getPower(){
        RetrofitConnect.getInstance()
                .getPowerReport(reportViews.getIndexReport())
                .enqueue(new Callback<PowerModels>() {
                    @Override
                    public void onResponse(@NotNull Call<PowerModels> call, @NotNull Response<PowerModels> response) {
                        if (response.isSuccessful()){
                            getPowerRequest.SuccessRequestPower(response.body() != null ? response.body().getData().get(0) : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<PowerModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void putPower() {
        RetrofitConnect.getInstance()
                .putPower(postRequestReport.getRequestMapBody())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            applicationViews.successRequest();
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_PUT);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void getFuel() {
        RetrofitConnect.getInstance()
                .getFuelReport(reportViews.getIndexReport())
                .enqueue(new Callback<FuelModels>() {
                    @Override
                    public void onResponse(@NotNull Call<FuelModels> call, @NotNull Response<FuelModels> response) {
                        if (response.isSuccessful()){
                            getFuelRequest.SuccessRequestFuel(response.body() != null ? response.body().getData().get(0) : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<FuelModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
        });
    }

    public void putFuel() {
        RetrofitConnect.getInstance()
                .putFuel(postRequestReport.getRequestMapBody())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            applicationViews.successRequest();
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_PUT);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void getRoomReport() {
        RetrofitConnect.getInstance()
                .getRoomReport(reportViews.getIndexReport())
                .enqueue(new Callback<BIRModels>() {
                    @Override
                    public void onResponse(@NotNull Call<BIRModels> call, @NotNull Response<BIRModels> response) {
                        if (response.isSuccessful()) {
                            getRoomRequest.SuccessRequestRoom(response.body() != null ? response.body().getData() : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<BIRModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
        });

    }

    public void getRoomReportByRuangan() {
        RetrofitConnect.getInstance()
                .getRoomReport(reportViews.getIndexReport(), getRoomRequest.getIndexRoom())
                .enqueue(new Callback<BIRModels>() {
                    @Override
                    public void onResponse(@NotNull Call<BIRModels> call, @NotNull Response<BIRModels> response) {
                        if (response.isSuccessful()){
                            getRoomRequest.SuccessRequestRoom(response.body() != null ? response.body().getData() : null);
                        } else {
                           applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<BIRModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
        });
    }

    public void putRoom() {
        RetrofitConnect.getInstance()
                .putRoom(postRequestReport.getRequestMapBody())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            applicationViews.successRequest();
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_PUT);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void getOthersReport() {
        RetrofitConnect.getInstance()
                .getOthersReport(reportViews.getIndexReport())
                .enqueue(new Callback<OthersModels>() {
                    @Override
                    public void onResponse(@NotNull Call<OthersModels> call, @NotNull Response<OthersModels> response) {
                        if (response.isSuccessful()){
                            getOthersRequest.SuccessRequestOthers(response.body() != null ? response.body().getData().get(0) : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<OthersModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
        });
    }

    public void putOthers() {
        RetrofitConnect.getInstance()
                .putOthers(postRequestReport.getRequestMapBody())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NotNull Call<ResponseBody> call, @NotNull Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            applicationViews.successRequest();
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_PUT);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<ResponseBody> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void getStatus() {
        RetrofitConnect.getInstance()
                .requestStatus(reportViews.getIndexReport())
                .enqueue(new Callback<ReportStatusModels>() {
                    @Override
                    public void onResponse(@NotNull Call<ReportStatusModels> call, @NotNull Response<ReportStatusModels> response) {
                        if (response.isSuccessful()){
                            getReportStatusRequest.SuccessRequestStatus(response.body() != null ? response.body().getData().get(0) : null);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<ReportStatusModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
        });
    }

    public void getAreaStatus(){
        RetrofitConnect.getInstance()
                .getAreaStatus()
                .enqueue(new Callback<StatusModels>() {
                    @Override
                    public void onResponse(@NotNull Call<StatusModels> call, @NotNull Response<StatusModels> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                getAreaStatusRequest.successAreaRequest(response.body().getMorning(), response.body().getNight());
                            }
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<StatusModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void getAreaStatusByWitel() {
        RetrofitConnect.getInstance()
                .getAreaStatus(statusReport.getIndexWitel())
                .enqueue(new Callback<StatusModels>() {
                    @Override
                    public void onResponse(@NotNull Call<StatusModels> call, @NotNull Response<StatusModels> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                getAreaStatusRequest.successAreaRequest(response.body().getMorning(), response.body().getNight());
                            }
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<StatusModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void getTemperatureStatus(){
        RetrofitConnect.getInstance()
                .getTemperature()
                .enqueue(new Callback<BIRModels>() {
                    @Override
                    public void onResponse(@NotNull Call<BIRModels> call, @NotNull Response<BIRModels> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                getTemperatureStatusRequest.successTemperatureRequest(response.body().getMorning(), response.body().getNight());
                            }
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<BIRModels> call, @NotNull Throwable t) {
                        t.printStackTrace();
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
         });
    }

    public void getTemperatureStatusByWitel() {
        RetrofitConnect.getInstance()
                .getTemperature(statusReport.getIndexWitel())
                .enqueue(new Callback<BIRModels>() {
                    @Override
                    public void onResponse(@NotNull Call<BIRModels> call, @NotNull Response<BIRModels> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                getTemperatureStatusRequest.successTemperatureRequest(response.body().getMorning(), response.body().getNight());
                            }
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<BIRModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void getFuelStatus() {
        RetrofitConnect.getInstance()
                .getFuel()
                .enqueue(new Callback<FuelModels>() {
                    @Override
                    public void onResponse(@NotNull Call<FuelModels> call, @NotNull Response<FuelModels> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                getFuelStatusRequest.successFuelRequest(response.body().getMorning(), response.body().getNight());
                            }
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<FuelModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
         });
    }

    public void getFuelStatusByWitel() {
        RetrofitConnect.getInstance()
                .getFuel(statusReport.getIndexWitel())
                .enqueue(new Callback<FuelModels>() {
                    @Override
                    public void onResponse(@NotNull Call<FuelModels> call, @NotNull Response<FuelModels> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                getFuelStatusRequest.successFuelRequest(response.body().getMorning(), response.body().getNight());
                            }
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<FuelModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
         });
    }

    public void getPowerStatus(){
        RetrofitConnect.getInstance()
                .getPower()
                .enqueue(new Callback<PowerModels>() {
                    @Override
                    public void onResponse(@NotNull Call<PowerModels> call, @NotNull Response<PowerModels> response) {
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                getPowerStatusRequest.successPowerRequest(response.body().getMorning(), response.body().getNight());
                            }
                        }
                        else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<PowerModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
        });
    }

    public void getPowerStatusByWitel() {
        RetrofitConnect.getInstance()
                .getPower(statusReport.getIndexWitel())
                .enqueue(new Callback<PowerModels>() {
                    @Override
                    public void onResponse(@NotNull Call<PowerModels> call, @NotNull Response<PowerModels> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null) {
                                getPowerStatusRequest.successPowerRequest(response.body().getMorning(), response.body().getNight());
                            }
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_GET);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<PowerModels> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                    }
                });
    }

    public void updateImage(final boolean INDEX_OF_MULTI_IMAGE){
        RetrofitConnect.getInstance()
                .updateImage(uploadWithImage.GetRequestBody(), uploadWithImage.GetMultiPart(INDEX_OF_MULTI_IMAGE))
                .enqueue(new Callback<ImageModel>() {
                    @Override
                    public void onResponse(@NotNull Call<ImageModel> call, @NotNull Response<ImageModel> response) {
                        if (response.isSuccessful()){
                            uploadWithImage.successPostImage(response.body() != null ? response.body().getNama() : null, INDEX_OF_MULTI_IMAGE);
                        } else {
                            applicationViews.requestFailled(ENVIRONMENT.ON_BAD_REQUEST_IMAGE);
                        }
                    }
                    @Override
                    public void onFailure(@NotNull Call<ImageModel> call, @NotNull Throwable t) {
                        applicationViews.requestFailled(ENVIRONMENT.ON_FAILURE_REQUEST);
                }
         });
    }
}
