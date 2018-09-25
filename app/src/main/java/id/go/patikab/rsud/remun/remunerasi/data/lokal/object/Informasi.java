package id.go.patikab.rsud.remun.remunerasi.data.lokal.object;

public class Informasi {
    String id_dokter;
    String label;
    String judul;
    String deskripsi;

    public Informasi(){

    }

    public Informasi(String id_dokter, String label, String judul, String deskripsi) {
        this.id_dokter = id_dokter;
        this.label = label;
        this.judul = judul;
        this.deskripsi = deskripsi;
    }

    public String getId_dokter() {
        return id_dokter;
    }

    public void setId_dokter(String id_dokter) {
        this.id_dokter = id_dokter;
    }

    public String getLabel() {
        return label;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }


    public void setLabel(String label) {
        this.label = label;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
