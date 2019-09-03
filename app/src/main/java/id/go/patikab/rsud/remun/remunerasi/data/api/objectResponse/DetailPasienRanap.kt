package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class DetailPasienRanap(@SerializedName("status") val status: String,
                             @SerializedName("message") val message: String,
                             @SerializedName("title") val judul: String,
                             @SerializedName("data_pendaftaran") val data: dataPendaftaran) {
    data class dataPendaftaran(
            @SerializedName("nomr") val NOMR: String,
            @SerializedName("nama_pasien") val NAMA: String,
            @SerializedName("masukrs")val masukrse:String,
            @SerializedName("keluarrs")val keluarrse:String,
            @SerializedName("carabayar")val carabayar:String,
            @SerializedName("unit")val unit:String,
            @SerializedName("type")val type:String

    ){
        val masukrs: String
            get() {
                if (masukrse == null) return "-"
                return try {
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val obj = sdf.parse(masukrse)

                    sdf.applyPattern("dd MMM yyyy")

                    sdf.format(obj)
                } catch (e: ParseException) {
                    "-"
                }
            }
     val keluarrs : String
        get(){
            if(keluarrse == null)return "-"
            return try {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val obj = sdf.parse(keluarrse)

                sdf.applyPattern("dd MMM yyyy")

                sdf.format(obj)
            }catch (e:ParseException){
                "-"
            }
        }
    }

}
