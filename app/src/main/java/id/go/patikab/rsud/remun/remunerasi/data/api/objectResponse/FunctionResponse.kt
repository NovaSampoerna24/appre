package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class FunctionResponse(@SerializedName("status") val status: String? = null,
                            @SerializedName("message") val message: String? = null,
                            @SerializedName("data") val dataUser: List<Status>? = null) {
    data class Status(@SerializedName("status") val status: Boolean? = true,
                      @SerializedName("akun") val akun: List<Akun>? = null) {
        data class Akun(@SerializedName("kd_dokter") val kd_dokter: String? = null,
                        @SerializedName("email") val emaile: String? = null)
    }
}