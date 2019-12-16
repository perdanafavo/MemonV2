package com.example.projectalpha.Network;

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
import com.example.projectalpha.Models.TimeModels;
import com.example.projectalpha.Models.UsersModels;
import com.example.projectalpha.Models.WitelModels;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

public interface ApiConnection {

    /*=====================================================*/
    /*================== API TIME SERVER ==================*/
    /*=====================================================*/

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Time")
    Call<TimeModels> getTime();


    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("DeleteReport")
    Call<ResponseBody> deleteReportMouth();

    /*=====================================================*/
    /*===================== API USERS =====================*/
    /*=====================================================*/

    // Login
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Users")
    Call<UsersModels> getUsersLogin(
            @Query("login") boolean login,
            @Query("username") String username,
            @Query("password") String password
    );

    // Ambil Users untuk Admin
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Users")
    Call<UsersModels> getUsers();

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Users")
    Call<UsersModels> getUsers(
            @Query("id") String id
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Users")
    Call<UsersModels> getUsersBySTO(
            @Query("sto") int sto,
            @Query("privileges") int privileges
    );

    //Reset Password
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @PUT("Users")
    @FormUrlEncoded
    Call<ResponseBody> updateUsers(
            @Field("id") String id,
            @FieldMap Map<String, String> data
    );

    //Update Validator
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @PUT("Report")
    @FormUrlEncoded
    Call<ResponseBody> updateValidator(
            @Field("id") int id,
            @Field("status_approved") int status_approved

    );

    @Multipart
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @POST("Upload")
    Call<ImageModel> updateImage(
            @PartMap Map<String, RequestBody> data,
            @Part MultipartBody.Part photo
    );

    @Multipart
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @POST("Users")
    Call<ResponseBody> postUsers(
            @PartMap Map<String, RequestBody> data,
            @Part MultipartBody.Part photo
    );

    //Menghapus Users via delete
//    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
//    @DELETE("Users/{id}")
//    Call<UsersModels> deleteUsers(
//            @Path("id") String id
//    );

    //Menghapus Users
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("DeleteUser")
    Call<UsersModels> deleteUsers(
            @Query("id") String id
    );

    /*=====================================================*/
    /*===================== API REPORT ====================*/
    /*=====================================================*/

    //Mengambil semua laporan / Shift / waktu hari ini
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Report")
    Call<LaporanModels> getLaporan(
            @Query("id") int id,
            @Query("date") String date
    );

    //Mengambil semua laporan / waktu yang diinginkan
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Report")
    Call<LaporanModels> getLaporan(
            @Query("date") String date,
            @Query("witel") int witel,
            @Query("shift") int shift
    );

    //Mengambil laporan STO / hari ini
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Report")
    Call<LaporanModels> getLaporan(
            @Query("witel") int witel,
            @Query("sto") int sto,
            @Query("shift") int shift
    );

    //Mengambil laporan STO untuk validator / hari ini
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Report")
    Call<LaporanModels> getLaporanValidator(
            @Query("sto") int sto,
            @Query("date") String date
    );

    //Membuat Users
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @FormUrlEncoded
    @POST("Report")
    Call<LaporanModels> addLaporan(
            @FieldMap Map<String, String> data
    );

    /*=====================================================*/
    /*===================== API WITEL ====================*/
    /*=====================================================*/

    //Mengambil semua daftar witel
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Witel")
    Call<WitelModels> getWitel();

    /*=====================================================*/
    /*====================== API STO =====================*/
    /*=====================================================*/

    //Mengambil sto dari witel
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("STO")
    Call<STOModels> getSTOWitel(
            @Query("witel") int witel
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("STO")
    Call<STOModels> getAllSTO();

    /*=====================================================*/
    /*====================== API GENERAL =====================*/
    /*=====================================================*/

    //Mengambil sto dari id
    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @FormUrlEncoded
    @PUT("General")
    Call<ResponseBody> putGeneral(
            @FieldMap Map<String, String> update
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("General")
    Call<KondisiUmumModels> getGeneralReport(
            @Query("laporan") int laporan
    );

    /*=====================================================*/
    /*====================== API POWER =====================*/
    /*=====================================================*/

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Power")
    Call<PowerModels> getPowerReport(
            @Query("laporan") int laporan
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @FormUrlEncoded
    @PUT("Power")
    Call<ResponseBody> putPower(
            @FieldMap Map<String, String> update
    );

    /*=====================================================*/
    /*====================== API FUEL =====================*/
    /*=====================================================*/

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Fuel")
    Call<FuelModels> getFuelReport(
            @Query("laporan") int laporan
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @FormUrlEncoded
    @PUT("Fuel")
    Call<ResponseBody> putFuel(
            @FieldMap Map<String, String> update
    );

    /*=====================================================*/
    /*====================== API ROOM =====================*/
    /*=====================================================*/

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("BIR")
    Call<BIRModels> getRoomReport(
            @Query("laporan") int laporan
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("BIR")
    Call<BIRModels> getRoomReport(
            @Query("laporan") int laporan,
            @Query("ruangan") int ruangan
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @FormUrlEncoded
    @PUT("BIR")
    Call<ResponseBody> putRoom(
            @FieldMap Map<String, String> update
    );

    /*=====================================================*/
    /*====================== API OTHERS =====================*/
    /*=====================================================*/

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Others")
    Call<OthersModels> getOthersReport(
            @Query("laporan") int laporan
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @FormUrlEncoded
    @PUT("Others")
    Call<ResponseBody> putOthers(
            @FieldMap Map<String, String> update
    );


    /*=======================================================*/
    /*====================== API STATUS =====================*/
    /*=======================================================*/

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("Status")
    Call<ReportStatusModels> requestStatus(
            @Query("laporan") int laporan
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("StatusWitel")
    Call<StatusModels> getAreaStatus();

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("StatusWitel")
    Call<StatusModels> getAreaStatus(
            @Query("witel") int witel
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("StatusBIR")
    Call<BIRModels> getTemperature();

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("StatusBIR")
    Call<BIRModels> getTemperature(
            @Query("witel") int witel
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("StatusFuel")
    Call<FuelModels> getFuel();

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("StatusFuel")
    Call<FuelModels> getFuel(
            @Query("witel") int witel
    );

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("StatusPower")
    Call<PowerModels> getPower();

    @Headers("TELKOM-API-KEY: Nu2xhCePUtRoZWiJAWtS2WkInIptC2IKN5XzYIZT")
    @GET("StatusPower")
    Call<PowerModels> getPower(
            @Query("witel") int witel
    );
}
