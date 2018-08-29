package id.go.patikab.rsud.remun.remunerasi.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ValDetail {
    @SerializedName("response")
    String responses;
    @SerializedName("status")
    String status;
    @SerializedName("totalResult")
    String totalResult;
    @SerializedName("totalDapat")
    String totalDapat;
    @SerializedName("pendapatanUmum")
    String pendapatan_bpjs;
    @SerializedName("pendapatanBpjs")
    String pendapatan_umum;
    @SerializedName("detail")
    List<DetailList>detailListList;

    public String getPendapatan_bpjs() {
        return pendapatan_bpjs;
    }

    public String getPendapatan_umum() {
        return pendapatan_umum;
    }

    public String getResponses() {
        return responses;
    }

    public List<DetailList> getDetailListList() {
        return detailListList;
    }

    public String getStatus() {
        return status;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public String getTotalDapat() {
        return totalDapat;
    }
}
