package id.go.patikab.rsud.remun.remunerasi.data.api.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProfilData {
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

