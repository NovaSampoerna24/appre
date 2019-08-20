package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class ListPasien(@SerializedName("status") val status: String,
                           @SerializedName("message") val message: String,
                           @SerializedName("title") val judul: String,
                      @SerializedName("jumlah") val jumlah: Int,
                           @SerializedName("data") val data: List<Pasiene>) {
    data class Pasiene(
                    @SerializedName("NOMR") val NOMR: String,
                     @SerializedName("NAMA") val NAMA: String,
                     @SerializedName("TGLREG") val TGLREG: String,
                     @SerializedName("UNIT") val UNIT: String,
                    @SerializedName("tgllahir") val TGLLAHIR: String,
                    @SerializedName("IDXDAFTAR") val IDXDAFTAR:String,
                    @SerializedName("carabayar") val carabayar:String
                    ){
        val tanggalLahir: String
            get() {
                if (TGLLAHIR == null) return "-"
                return try {
                    val sdf = SimpleDateFormat("yyyy-mm-dd", Locale.getDefault())
                    val obj = sdf.parse(TGLLAHIR)

                    sdf.applyPattern("dd MMM yyyy")

                    sdf.format(obj)
                } catch (e: ParseException) {
                    "-"
                }
            }
    }


}
