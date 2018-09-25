package id.go.patikab.rsud.remun.remunerasi.data.api;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {

    // variable name should be same as in the json response from php
    @SerializedName("success")
    boolean success;
    @SerializedName("message")
    String message;
    @SerializedName("msg")
    String msg;

    public String getMsg() {
        return msg;
    }

    public String getMessage() {
        return message;
    }

    public boolean getSuccess() {
        return success;
    }

}