package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse;

import com.google.gson.annotations.SerializedName;

public class DataDokter {
    @SerializedName("KDDOKTER")
    String kddokter;
    @SerializedName("NAMADOKTER")
    String nama_dokter;
    @SerializedName("signature")
    String signature;

    public DataDokter(String kddokter,String nama_dokter,String signature){
        this.kddokter = kddokter;
        this.nama_dokter = nama_dokter;
        this.signature = signature;
    }

    public String getKddokter() {
        return kddokter;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public String getSignature() {
        return signature;
    }
}
