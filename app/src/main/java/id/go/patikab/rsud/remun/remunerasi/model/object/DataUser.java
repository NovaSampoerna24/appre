package id.go.patikab.rsud.remun.remunerasi.model.object;

import com.google.gson.annotations.SerializedName;

public class DataUser {
    @SerializedName("KDDOKTER")
    String KDDOKTER;


    public String getKDDOKTER() {
        return KDDOKTER;
    }

    public void setKDDOKTER(String KDDOKTER) {
        this.KDDOKTER = KDDOKTER;
    }

}
