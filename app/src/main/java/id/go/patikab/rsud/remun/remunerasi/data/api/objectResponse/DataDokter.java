package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse;

import com.google.gson.annotations.SerializedName;

public class DataDokter {
    @SerializedName("KDDOKTER")
    String kddokter;
    @SerializedName("NAMADOKTER")
    String nama_dokter;

    public DataDokter(String kddokter,String nama_dokter){
        this.kddokter = kddokter;
        this.nama_dokter = nama_dokter;
    }

    public String getKddokter() {
        return kddokter;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }


}
