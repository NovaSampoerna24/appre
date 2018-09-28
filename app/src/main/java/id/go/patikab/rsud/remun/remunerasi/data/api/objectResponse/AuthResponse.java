package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AuthResponse {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<DataDokter> dataUser;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public List<DataDokter> getDataUser() {
        return dataUser;
    }
}
