package id.go.patikab.rsud.remun.remunerasi.config.database.model;

public class NotifikasiData {
    int id;
    String title;
    String message;
    String waktu;

    public NotifikasiData() {

    }

    public NotifikasiData(int id, String title, String message, String waktu) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.waktu = waktu;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWaktu() {
        return waktu;
    }

    public void setWaktu(String waktu) {
        this.waktu = waktu;
    }
}
