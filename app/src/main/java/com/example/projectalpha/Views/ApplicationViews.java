package com.example.projectalpha.Views;

import com.example.projectalpha.Models.SubModels.BIRData;
import com.example.projectalpha.Models.SubModels.FuelData;
import com.example.projectalpha.Models.SubModels.KondisiUmumData;
import com.example.projectalpha.Models.SubModels.LaporanData;
import com.example.projectalpha.Models.SubModels.OthersData;
import com.example.projectalpha.Models.SubModels.PowerData;
import com.example.projectalpha.Models.SubModels.ReportStatusData;
import com.example.projectalpha.Models.SubModels.STOData;
import com.example.projectalpha.Models.SubModels.UsersData;
import com.example.projectalpha.Models.SubModels.WitelData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface ApplicationViews {
    void requestFailled(String message);
    void successRequest();

    interface UsersViews{
        int getStoArea();
        String getEmployee();

        interface GetRequest{
            void SuccessRequestGetUsers(ArrayList<UsersData> data);
        }

        interface UpdateRequest{
            Map<String, String> getRequestMapBody();
        }
    }

    interface WitelViews{
        interface GetRequest{
            void SuccessRequestGetWitel(List<WitelData> data);
        }
    }

    interface StoViews{
        interface GetRequest {
            void SucessRequestGetSTO(List<STOData> data);
        }
    }

    interface ReportViews{
        int getIndexReport();

        interface GetReportParam{
            int getIndexWitel();
            int getIndexShift();
            int getIndexSto();
            String getDateReport();
        }

        interface GetRequestReport {
            void SuccessRequestGetReport(List<LaporanData> data);
        }

        interface GetGeneralRequest{
            void SuccessRequestGeneral(KondisiUmumData data);
        }

        interface GetPowerRequest{
            void SuccessRequestPower(PowerData data);
        }

        interface GetFuelRequest{
            void SuccessRequestFuel(FuelData data);
        }

        interface GetRoomRequest{
            void SuccessRequestRoom(List<BIRData> data);
            int getIndexRoom();
        }

        interface GetOthersRequest{
            void SuccessRequestOthers(OthersData data);
        }

        interface GetReportStatusRequest{
            void SuccessRequestStatus(ReportStatusData data);
        }

        interface PostRequest{
            Map<String, String> getRequestMapBody();
            void SuccessPostReport(List<LaporanData> data);
        }
    }

    interface StatusReport{
        int getIndexWitel();

        interface GetTemperatureRequest{
            void successTemperatureRequest(List<BIRData> morning, List<BIRData> night);
        }

        interface GetFuelRequest{
            void successFuelRequest(List<FuelData> morning, List<FuelData> night);
        }

        interface GetPowerRequest{
            void successPowerRequest(List<PowerData> morning, List<PowerData> night);
        }

        interface GetAreaStatusRequest{
            void successAreaRequest(List<LaporanData> morning, List<LaporanData> night);
        }
    }

    interface UploadWithImage{
        void successPostImage(String nama, boolean index);
        Map<String, RequestBody> GetRequestBody();
        MultipartBody.Part GetMultiPart(boolean index);
    }
}
