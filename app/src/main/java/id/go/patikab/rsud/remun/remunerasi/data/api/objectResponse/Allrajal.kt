package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse
import com.google.gson.annotations.SerializedName

data class Allrajal(@SerializedName("jumlah") val jumlah: String? = null,
                    @SerializedName("tanggal") val tanggal: String?=null,
                    @SerializedName("data_rajal") val dataRajal: List<Datarajal>? = null) {
    data class Datarajal(@SerializedName("jumlah") val jumlah: String? = null,
                         @SerializedName("dokter") val dokter: String?=null,
                         @SerializedName("kddokter") val kddokter: String?=null)
}
