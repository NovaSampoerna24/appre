package id.go.patikab.rsud.remun.remunerasi.data.api.objectResponse

import com.google.gson.annotations.SerializedName

data class ResponseMenu(@SerializedName("status")val status:String,
                        @SerializedName("message")val message:String,
                        @SerializedName("title")val title:String,
                        @SerializedName("data")val menuList:List<Menune>){
    data class Menune(@SerializedName("id")val id:String,
                      @SerializedName("judul")val judul:String,
                      @SerializedName("icon")val icon:String,
                      @SerializedName("url")val url:String,
                        @SerializedName("message")val messagee:String)

}