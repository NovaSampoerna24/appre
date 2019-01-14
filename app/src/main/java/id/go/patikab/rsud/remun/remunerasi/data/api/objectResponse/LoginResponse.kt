package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName
import java.security.Signature

data class LoginResponse(@SerializedName("status") val status: String? = null,
                         @SerializedName("message") val message: String? = null,
                         @SerializedName("data") val dataUser: List<Akun>? = null) {
    data class Akun(@SerializedName("username") val username: String? = null,
                    @SerializedName("status") val status: String? = null,
                    @SerializedName("kd_dokter") val kd_dokter: String? = null,
                    @SerializedName("email") val email_user: String? = null,
                    @SerializedName("nm_dokter") val nama_dokter: String? = null,
                    @SerializedName("signature") val signatured: String? = null)
}
