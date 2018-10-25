package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse;

import com.google.gson.annotations.SerializedName;

public class DetailTindakan {
    @SerializedName("id")
    String id;
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
    @SerializedName("jp_kotor")
    String jp_kotor;
    @SerializedName("tarif_setelah")
    String tarif_setelah;
    @SerializedName("PROPORSI")
    String proporsi;
    @SerializedName("JP")
    String JP;

    public void setTindakan(String tindakan) {
        this.tindakan = tindakan;
    }

    public String getTarif_jl2() {
        return tarif_jl2;
    }

    public void setTarif_jl2(String tarif_jl2) {
        this.tarif_jl2 = tarif_jl2;
    }

    public String getTarif_jtl1() {
        return tarif_jtl1;
    }

    public void setTarif_jtl1(String tarif_jtl1) {
        this.tarif_jtl1 = tarif_jtl1;
    }

    public String getTarif_jtl2() {
        return tarif_jtl2;
    }

    public void setTarif_jtl2(String tarif_jtl2) {
        this.tarif_jtl2 = tarif_jtl2;
    }

    public void setJp_kotor(String jp_kotor) {
        this.jp_kotor = jp_kotor;
    }

    public void setTarif_setelah(String tarif_setelah) {
        this.tarif_setelah = tarif_setelah;
    }

    public String getProporsi() {
        return proporsi;
    }

    public void setProporsi(String proporsi) {
        this.proporsi = proporsi;
    }

    public String getJP() {
        return JP;
    }

    public void setJP(String JP) {
        this.JP = JP;
    }

    public String getTarif_setelah() {
        return tarif_setelah;
    }
    public String getJp_kotor() {
        return jp_kotor;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTindakan() {
        return tindakan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getTarif_jl1() {
        return tarif_jl1;
    }

    public void setTarif_jl1(String tarif_jl1) {
        this.tarif_jl1 = tarif_jl1;
    }

    public DetailTindakan(String id, String tindakan, String tanggal, String tarif, String tarif_jl1, String tarif_jl2, String tarif_jtl1, String tarif_jtl2,String JP,String PROPORSI) {

        this.id = id;
        this.tindakan = tindakan;
        this.tanggal = tanggal;
        this.tarif = tarif;
        this.tarif_jl1 = tarif_jl1;
        this.tarif_jl2 = tarif_jl2;
        this.tarif_jtl1 = tarif_jtl1;
        this.tarif_jtl2 = tarif_jtl2;
        this.JP  = JP;
        this.proporsi = PROPORSI;
    }
}
