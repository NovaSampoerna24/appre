package id.go.patikab.rsud.remun.remunerasi.data.lokal.table

data class informasi_tb(val id: Long?,
                         val id_dokter: String?,
                         val label: String?,
                         val judul: String?,
                         val deskripsi: String?,
                         val tanggal: String?,
                         val time: String?) {

    companion object {
        const val TABLE = "tb_informasi"
        const val IDe = "ID_"
        const val id_doktere = "id_dokter"
        const val judule = "judul"
        const val deskripsie = "deskripsi"
        const val tanggale = "tanggal"
        const val timee = "time"
        const val labele = "label"
    }
}