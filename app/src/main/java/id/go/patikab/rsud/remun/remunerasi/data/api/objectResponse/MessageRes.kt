package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class MessageRes(@SerializedName("message") val message:String,
                      @SerializedName("waktu_visit") val waktuvisit:String);