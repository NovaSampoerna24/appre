package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class ListPasienDokter(@SerializedName("status") val status: String,
                            @SerializedName("msg") val msg: String,
                            @SerializedName("data") val remidList: List<Remid>) {
    data class Remid(@SerializedName("NAMA") val nama: String,
                     @SerializedName("TOTAL") val total: String,
                     @SerializedName("TARIF_PER_RM") val tarif_per_rm: String,
                     @SerializedName("TOTAL_JP_PER_RM") val total_jp_per_rm: String,
                     @SerializedName("PORSI") val porsi: String,
                     @SerializedName("TOTAL_PORSI") val total_porsi: String,
                     @SerializedName("TINDAKAN") val tindakanList: List<Tindakan>) {

        data class Tindakan(@SerializedName("tindakan") val tindakan: String,
                            @SerializedName("jp3") val jp3: String)
    }

}