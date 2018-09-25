package id.go.patikab.rsud.remun.remunerasi.data.lokal

import android.content.Context
import id.go.patikab.rsud.remun.remunerasi.data.database
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi
import id.go.patikab.rsud.remun.remunerasi.data.lokal.table.informasi_tb
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class AnkoDb(private val mContext: Context) : DbInterface {

    override fun addtoInformasi(informasi: Informasi) {
        mContext.database.use {
            insert(informasi_tb.TABLE,
                    informasi_tb.id_doktere to informasi.id_dokter,
                    informasi_tb.judule to informasi.judul,
                    informasi_tb.deskripsie to informasi.deskripsi,
                    informasi_tb.labele to informasi.label
            )
        }
    }

    override fun getInformasi(kduser: String): List<Informasi> {
        return mContext.database.use {
            select(informasi_tb.TABLE).parseList(classParser<informasi_tb>()).map {
                Informasi(
                        it.id_dokter ?: "",
                        it.judul ?: "",
                        it.deskripsi ?: "",
                        it.label ?: "-"


                )
            }
        }
    }

}