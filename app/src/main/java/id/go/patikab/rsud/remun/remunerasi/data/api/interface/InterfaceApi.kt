package id.go.patikab.rsud.remun.remunerasi.data.api.`interface`

import android.accounts.NetworkErrorException
import com.androidnetworking.AndroidNetworking
import id.go.patikab.rsud.remun.remunerasi.data.api.Apiurl
import id.go.patikab.rsud.remun.remunerasi.data.api.`object`.Interface
import id.go.patikab.rsud.remun.remunerasi.data.api.`object`.ProfileGetData
import org.jetbrains.anko.coroutines.experimental.bg
import kotlinx.coroutines.experimental.Deferred
class ApiInterfac: Interface {

    override fun detailProfil(ids: String): Deferred<ProfileGetData> = req(
            Apiurl.DetailProfile,
            ProfileGetData::class.java,
            "id" to ids
    ) as Deferred<ProfileGetData>

    private fun req(url: String, type: Class<*>, vararg pairs: Pair<String, String>): Deferred<Any> {
        return bg {
            val response = AndroidNetworking
                    .get(url)
                    .apply { pairs.forEach { addQueryParameter(it.first, it.second) } }
                    .build()
                    .executeForObject(type)

            if (!response.isSuccess) {
                throw NetworkErrorException(response.error.localizedMessage)
            }

            response.result
        }
    }
}