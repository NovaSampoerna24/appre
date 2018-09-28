package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse;

import com.google.gson.annotations.SerializedName;

public class DetailPasien {
    @SerializedName("status")
    String status;
    @SerializedName("msg")
    String message;
    @SerializedName("nama_pasien")
    String nama_pasien;
    @SerializedName("ttl")
    String ttl;
    @SerializedName("alamat")
    String alamat;
    @SerializedName("ruang")
    String ruang;
    @SerializedName("nama")
    String nama_gedung;

    public String getNama_gedung() {
        return nama_gedung;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getNama_pasien() {
        return nama_pasien;
    }

    public String getTtl() {
        return ttl;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getRuang() {
        return ruang;
    }

    public DetailPasien(String status, String message, String nama_pasien, String ttl, String alamat, String ruang, String nama_gedung) {
        this.status = status;
        this.message = message;
        this.nama_pasien = nama_pasien;
        this.ttl = ttl;
        this.alamat = alamat;
        this.ruang = ruang;
        this.nama_gedung = nama_gedung;
    }
}
