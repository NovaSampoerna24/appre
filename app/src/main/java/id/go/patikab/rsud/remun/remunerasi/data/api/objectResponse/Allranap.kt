package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse
import com.google.gson.annotations.SerializedName

data class Allranap(@SerializedName("jumlah") val jumlah: String? = null,
                          @SerializedName("data_ranap") val dataRanap: List<Dataranap>? = null) {
    data class Dataranap(@SerializedName("jumlah") val jumlah: String? = null,
                    @SerializedName("dokter") val dokter: String?=null,
                         @SerializedName("kddokter") val kddokter: String?=null
                         )
}
