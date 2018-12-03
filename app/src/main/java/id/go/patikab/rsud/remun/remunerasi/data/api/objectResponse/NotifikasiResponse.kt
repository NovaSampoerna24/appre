package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class NotifikasiResponse(@SerializedName("status") val status: String,
                              @SerializedName("message") val message: String,
                              @SerializedName("data") val data: List<Notif>) {
    data class Notif(@SerializedName("id") val id: String,
                     @SerializedName("judul") val judul: String,
                     @SerializedName("pesan") val pesan: String,
                     @SerializedName("tanggal") val tanggal: String,
                     @SerializedName("jenis_pesan") val jp: String,
                     @SerializedName("jam") val jam: String) {
        val jenis_p:String
        get(){
            return if( jp == "1") "Pengumuman" else "Pesan"
        }
        val readableDate: String
            get() {
                return try {
                    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                    val obj = sdf.parse(tanggal)

                    sdf.applyPattern("E, dd MMM yyyy")

                    sdf.format(obj)
                } catch (e: ParseException) {
                    "-"
                }
            }



    }
}