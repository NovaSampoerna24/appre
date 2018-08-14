package id.go.patikab.rsud.remun.remunerasi.entity;

import com.google.gson.annotations.SerializedName;

public class DetailTindakan {
    @SerializedName("tindakan")
    String tindakan;
    @SerializedName("tanggal")
    String tanggal;
    @SerializedName("tarif")
    String tarif;
    @SerializedName("tarif_jl1")
    String tarif_jl1;
    @SerializedName("tarif_jl2")
    String tarif_jl2;
    @SerializedName("tarif_jtl1")
    String tarif_jtl1;
    @SerializedName("tarif_jtl2")
    String tarif_jtl2;


    public DetailTindakan(String tindakan, String tanggal, String tarif, String tarif_jl1, String tarif_jl2, String tarif_jtl1, String tarif_jtl2) {
        this.tindakan = tindakan;
        this.tanggal = tanggal;
        this.tarif = tarif;
        this.tarif_jl1 = tarif_jl1;
        this.tarif_jl2 = tarif_jl2;
        this.tarif_jtl1 = tarif_jtl1;
        this.tarif_jtl2 = tarif_jtl2;
    }

    public String getTarif_jl1() {
        return tarif_jl1;
    }

    public String getTarif_jl2() {
        return tarif_jl2;
    }

    public String getTarif_jtl1() {
        return tarif_jtl1;
    }

    public String getTarif_jtl2() {
        return tarif_jtl2;
    }

    public String getTindakan() {
        return tindakan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getTarif() {
        return tarif;
    }
}
