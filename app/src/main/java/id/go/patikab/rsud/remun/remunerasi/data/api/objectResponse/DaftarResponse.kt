package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class DaftarResponse(@SerializedName("status") val status: String? = null,
                         @SerializedName("message") val message: String? = null,
                         @SerializedName("data") val dataUser: List<Akun>? = null) {
    data class Akun(@SerializedName("username") val username: String? = null,
                    @SerializedName("status") val status: String?=null,
                    @SerializedName("signature") val signatured: String? = null)
}
