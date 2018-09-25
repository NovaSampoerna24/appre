package id.go.patikab.rsud.remun.remunerasi.data.api.object;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataTindakan {
    @SerializedName("status")
    String status;
    @SerializedName("total")
    String total;
    @SerializedName("pendapatan_bpjs")
    String pendapatan_bpjs;
    @SerializedName("pendapatan_umum")
    String pendapatan_umum;
    @SerializedName("nama_dokter")
    String nama_dokter;
    @SerializedName("judul")
    String judul;


    @SerializedName("data")
    List<DetailTindakan>detailTindakanList;


    public String getStatus() {
        return status;
    }

    public String getTotal() {
        return total;
    }

    public String getPendapatan_bpjs() {
        return pendapatan_bpjs;
    }

    public String getPendapatan_umum() {
        return pendapatan_umum;
    }

    public String getNama_dokter() {
        return nama_dokter;
    }

    public String getJudul() {
        return judul;
    }

    public List<DetailTindakan> getDetailTindakanList() {
        return detailTindakanList;
    }
}
