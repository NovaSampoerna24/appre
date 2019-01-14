package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class RingkasanModel(@SerializedName("status") val status:String,
                          @SerializedName("msg") val message:String,
                          @SerializedName("data") val data:List<Ringkasan>){
    data class Ringkasan(@SerializedName("judul")val judul:String,
                         @SerializedName("taksiran")val taksiran:String,
                         @SerializedName("tanggal_awal")val tanggal_awal:String,
                         @SerializedName("tanggal_akhir")val tanggal_akhir:String )
}