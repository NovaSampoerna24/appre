package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class AktivasiResponse(@SerializedName("status") val status: String,
                            @SerializedName("message") val message: String,
                            @SerializedName("data") val dataUser: List<Status>) {
    data class Status(@SerializedName("status") val st: Boolean,
                      @SerializedName("kd_dokter") val kd_dokter:String ,
                      @SerializedName("status_akun") val status_akun:String ,
                      @SerializedName("signature") val signaturee:String ,
                      @SerializedName("nm_doker") val nama_dokter:String ,

                      @SerializedName("email") val email:String )
}