package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfilGetData {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<ProfilDetail> dataProfils;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<ProfilDetail> getDataProfils() {
        return dataProfils;
    }
}

