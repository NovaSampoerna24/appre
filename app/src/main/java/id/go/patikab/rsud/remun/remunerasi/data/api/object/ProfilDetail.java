package id.go.patikab.rsud.remun.remunerasi.data.api.object;

import com.google.gson.annotations.SerializedName;

public class ProfilDetail {
    @SerializedName("id")
    String id;
    @SerializedName("id_profile")
    String id_profile;
    @SerializedName("nama")
    String nama;
    @SerializedName("foto")
    String foto;
    @SerializedName("deskripsi")
    String deskripsi;

    public String getId() {
        return id;
    }

    public String getId_profile() {
        return id_profile;
    }

    public String getNama() {
        return nama;
    }

    public String getFoto() {
        return foto;
    }

    public String getDeskripsi() {
        return deskripsi;
    }
}
