package id.go.patikab.rsud.remun.remunerasi.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.go.patikab.rsud.remun.remunerasi.data.lokal.`object`.Informasi
import id.go.patikab.rsud.remun.remunerasi.data.lokal.table.informasi_tb
import org.jetbrains.anko.db.*

class DatabaseHandler1 (ctx: Context) : ManagedSQLiteOpenHelper(ctx, "sqli.db", null, 1) {

    companion object {
        private var instance: DatabaseHandler1? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHandler1 {
            if (instance == null) {
                instance = DatabaseHandler1(ctx.applicationContext)
            }
            return instance as DatabaseHandler1
        }
    }
    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(informasi_tb.TABLE, true,
                informasi_tb.IDe to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                informasi_tb.id_doktere to TEXT + UNIQUE,
                informasi_tb.judule to TEXT,
                informasi_tb.deskripsie to TEXT,
                informasi_tb.labele to TEXT)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(informasi_tb.TABLE, true)

    }

}

// access property for Context
val Context.database: DatabaseHandler1
    get() = DatabaseHandler1.getInstance(applicationContext)