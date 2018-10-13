package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class NotifikasiResponse(@SerializedName("status")val status:String,
                              @SerializedName("message")val message:String,
                              @SerializedName("data")val data:List<Notif>){
    data class Notif(@SerializedName("id") val id:String,
                     @SerializedName("judul")val judul:String,
                     @SerializedName("pesan")val pesan:String,
                     @SerializedName("waktu")val waktu:String,
                     @SerializedName("jenis_pesan")val jp:String)
}