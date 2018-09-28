package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DokterGetData {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<DataDokter>dokterList;

    public String getMessage() {
        return message;
    }

    public List<DataDokter> getDokterList() {
        return dokterList;
    }
}
