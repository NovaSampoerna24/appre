package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class ListPasienDokter(@SerializedName("status") val status: String,
                            @SerializedName("msg") val msg: String,
                            @SerializedName("pendapatan") val pendapatan: String,
                            @SerializedName("jumlah_pasien")val j_pasien:String,
                            @SerializedName("jumlah_tindakan")val j_tindakan:String,
                            @SerializedName("data") val remidList: List<Remid>) {
    data class Remid(@SerializedName("NAMA") val nama: String,
                     @SerializedName("TOTAL") val total: String,
                     @SerializedName("TARIF_PER_RM") val tarif_per_rm: String,
                     @SerializedName("TOTAL_JP_PER_RM") val total_jp_per_rm: String,
                     @SerializedName("PORSI") val porsi: String,
                     @SerializedName("TOTAL_PORSI") val total_porsi: String,
                     @SerializedName("tgl_pulang") val tanggal_pulang: String,
                     @SerializedName("cara_bayar") val carabayar: String,

                     @SerializedName("TINDAKAN") val tindakanList: List<Tindakan>) {
        data class Tindakan(@SerializedName("id_tindakan") val idtindakan: String,
                            @SerializedName("tindakan") val tindakan: String,
                            @SerializedName("tarif_jp") val tarif_jp: String,
                            @SerializedName("reward") val reward: String,
                            @SerializedName("tarif_tindakan") val tarif_tindakan: String,
                            @SerializedName("qty") val qty: String)

    }

}