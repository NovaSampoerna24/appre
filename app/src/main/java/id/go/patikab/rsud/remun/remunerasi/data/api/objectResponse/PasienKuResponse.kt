package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName
import java.util.*

data class PasienKuResponse(@SerializedName("status") val status: String,
                              @SerializedName("message") val message: String,
                            @SerializedName("title") val judul: String,
                              @SerializedName("data") val data: List<Pasienku>) {
    data class Pasienku(
                    @SerializedName("judul") val judul: String,
                     @SerializedName("jumlah") val jumlah: String,
                     @SerializedName("dari") val dari: Date,
                     @SerializedName("sampai") val sampai: Date
                    )
    }
