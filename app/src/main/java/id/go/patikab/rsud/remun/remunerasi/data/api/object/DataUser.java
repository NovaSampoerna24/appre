package id.go.patikab.rsud.remun.remunerasi.data.api.object;

import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("KDDOKTER")
    String KDDOKTER;

    public String getKDDOKTER() {
        return KDDOKTER;
    }

}
