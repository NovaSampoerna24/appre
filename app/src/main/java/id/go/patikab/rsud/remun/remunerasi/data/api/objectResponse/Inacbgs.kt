package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class Inacbgs(@SerializedName("response")
                   val res: String,
                   @SerializedName("data")
                   val tr: List<Tarife>) {
    data class Tarife(
            @SerializedName("tarif_rp")
            val tarif_rp: String,
            @SerializedName("status")
            val status: String
    )
}