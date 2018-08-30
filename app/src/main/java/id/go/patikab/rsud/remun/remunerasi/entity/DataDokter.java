package id.go.patikab.rsud.remun.remunerasi.entity;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DataDokter {
    @SerializedName("KDDOKTER")
    String kddokter;
    @SerializedName("NAMADOKTER")
    String nama_dokter;

    public DataDokter(String kddokter,String nama_dokter){
        this.kddokter = kddokter;
        this.nama_dokter = nama_dokter;
    }
    public DataDokter(){
    }

    public String getKddokter() {
        return kddokter;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public void setKddokter(String kddokter) {
        this.kddokter = kddokter;
    }

    public void setNama_dokter(String nama_dokter) {
        this.nama_dokter = nama_dokter;
    }

}
