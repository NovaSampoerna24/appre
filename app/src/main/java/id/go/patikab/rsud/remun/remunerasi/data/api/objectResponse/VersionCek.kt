package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse
import com.google.gson.annotations.SerializedName

data class VersionCek(@SerializedName("nama_aplikasi") val nama_aplikasi: String,
                      @SerializedName("versi") val versi: Int,
                      @SerializedName("status") val status: String,
                      @SerializedName("title") val title: String,
                      @SerializedName("message") val message: String)