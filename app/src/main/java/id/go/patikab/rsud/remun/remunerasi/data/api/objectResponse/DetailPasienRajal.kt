package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class DetailPasienRajal(@SerializedName("status") val status: String,
                      @SerializedName("message") val message: String,
                      @SerializedName("title") val judul: String,
                      @SerializedName("data_pendaftaran") val data: dataPendaftaran) {
    data class dataPendaftaran(
            @SerializedName("NOMR") val NOMR: String,
            @SerializedName("nama_pasien") val NAMA: String,
            @SerializedName("TGLREG")val tglreg:String,
            @SerializedName("MASUKPOLY")val masukpoly:String,
            @SerializedName("KELUARPOLY")val keluarpoly:String,
            @SerializedName("carabayar")val carabayar:String,
            @SerializedName("unit")val unit:String,
            @SerializedName("type")val type:String

    ){
        val tanggalmasuk: String
            get() {
                if (tglreg == null) return "-"
                return try {
                    val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
                    val obj = sdf.parse(tglreg)

                    sdf.applyPattern("dd MMM yyyy")

                    sdf.format(obj)
                } catch (e: ParseException) {
                    "-"
                }
            }
    }

}
