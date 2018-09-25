package id.go.patikab.rsud.remun.remunerasi.data.api.object;

import com.google.gson.annotations.SerializedName;

public class valueDetailTindakan {
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

    public valueDetailTindakan(String status, String message, String nama_pasien, String ttl, String alamat, String ruang) {
        this.status = status;
        this.message = message;
        this.nama_pasien = nama_pasien;
        this.ttl = ttl;
        this.alamat = alamat;
        this.ruang = ruang;
    }
}
