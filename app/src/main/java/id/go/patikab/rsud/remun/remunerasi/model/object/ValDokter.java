package id.go.patikab.rsud.remun.remunerasi.model.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ValDokter {
    @SerializedName("status")
    String status;
    @SerializedName("message")
    String message;
    @SerializedName("data")
    List<DataDokter>dokterList;

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<DataDokter> getDokterList() {
        return dokterList;
    }
}
