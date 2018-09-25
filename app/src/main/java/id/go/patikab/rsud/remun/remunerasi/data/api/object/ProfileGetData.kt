package id.go.patikab.rsud.remun.remunerasi.data.api.`object`

import com.google.gson.annotations.SerializedName

data class ProfileGetData(@SerializedName("status") val status: String,
                          @SerializedName("message")val message: String,
                          @SerializedName("data")val dataProfils: List<DetailP>?) {
    data class DetailP(@SerializedName("id") val id: String,
                            @SerializedName("id_profile")val id_profile: String,
                            @SerializedName("nama") val nama: String,
                            @SerializedName("foto")val foto: String,
                            @SerializedName("deskripsi")val deskripsi: String)
}