package id.go.patikab.rsud.remun.remunerasi.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegisterResponse {
    @SerializedName("status")
    String status;
    @SerializedName("data")
    List<DataUser> dataUser;

    public String getStatus() {
        return status;
    }

    public List<DataUser> getDataUser() {
        return dataUser;
    }
}
