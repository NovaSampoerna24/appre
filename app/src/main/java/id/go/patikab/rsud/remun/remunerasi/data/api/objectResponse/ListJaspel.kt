package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class ListJaspel(@SerializedName("message") val message: String,
                          @SerializedName("status") val status: String,
                          @SerializedName("data") val listJp: List<listJaspel>) {
    data class listJaspel(@SerializedName("id") val id: String,
                          @SerializedName("judul") val judul: String,
                          @SerializedName("kategori") val kategori: String,
                          @SerializedName("periode") val periode: String,
                          @SerializedName("dari") val dari: String,
                          @SerializedName("sampai") val sampai: String,
                          @SerializedName("token") val token: String)
}