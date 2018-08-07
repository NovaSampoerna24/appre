package id.go.patikab.rsud.remun.remunerasi.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DataTindakan {
    @SerializedName("status")
    String status;
    @SerializedName("total")
        String total;
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
