package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class ListDokter(@SerializedName("status") val status: String,
                      @SerializedName("message") val message: String,
                      @SerializedName("data") val data: List<Dokter>) {
    data class Dokter(
            @SerializedName("KDDOKTER") val KDDOKTER: String,
            @SerializedName("NAMADOKTER") val NAMADOKTER: String
    )
}