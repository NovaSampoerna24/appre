package id.go.patikab.rsud.remun.remunerasi.data.lokal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import id.go.patikab.rsud.remun.remunerasi.data.lokal.object.DokterData;
import id.go.patikab.rsud.remun.remunerasi.data.lokal.object.Informasi;
import id.go.patikab.rsud.remun.remunerasi.data.lokal.object.NotifikasiData;
import id.go.patikab.rsud.remun.remunerasi.data.api.object.DataDokter;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String nama_db = "db_dotker";

    private static String nm_tb2 = "tb_dokter_2";
    private static String nm_tb1 = "tb_dokter";
    private static String field1_tb = "kd_dokter";
    private static String field2_tb = "nama_dokter";

    private static String nm_tb4 = "tb_informasi";
    private static String field1_tb4 = "id_dokter";
    private static String field2_tb4 = "label";
    private static String field3_tb4 = "judul";
    private static String field4_tb4 = "deskripsi";

    private static String nm_tb3 = "tb_notifikasi";
    private static String field1_tb3 = "title";
    private static String field2_tb3 = "message";
    private static String field3_tb3 = "waktu";

    private static final String tb1 = "create table " + nm_tb1 + " ( " +
            "id integer primary key autoincrement, " +
            field1_tb + " text," +
            field2_tb + " text)";
    private static final String tb2 = "create table " + nm_tb2 + " ( " +
            "id integer primary key autoincrement, " +
            field1_tb + " text," +
            field2_tb + " text)";
    private static final String tb3 = "create table " + nm_tb3 + " ( " +
            "id integer primary key autoincrement, " +
            field1_tb3 + " text, " +
            field2_tb3 + " text, " +
            field3_tb3 + " text)";
    private static final String tb4 = "create table " + nm_tb4 + "(" +
            "id integer primary key autoincrement," +
            field1_tb4 + " integer, " +
            field2_tb4 + " text, " +
            field3_tb4 + " text," +
            field4_tb4 + " text )";
    private static DatabaseHandler sInstance;


    public static synchronized DatabaseHandler getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new DatabaseHandler(context.getApplicationContext());
        }
        return sInstance;
    }

    public DatabaseHandler(Context context) {
        super(context, nama_db, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(tb1);
        db.execSQL(tb2);
        db.execSQL(tb3);
        db.execSQL(tb4);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + nm_tb1);
        db.execSQL("drop table if exists " + nm_tb2);
        db.execSQL("drop table if exists " + nm_tb3);
        db.execSQL("drop table if exists " + nm_tb4);
    }

    public void insertDokterListLogin(DokterData dokterData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(field1_tb, dokterData.getKode());
        values.put(field2_tb, dokterData.getNama());
        db.insert(nm_tb1, null, values);
        db.close();
    }


    public void insertDokterListRegister(DokterData dokterData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(field1_tb, dokterData.getKode());
        values.put(field2_tb, dokterData.getNama());
        db.insert(nm_tb2, null, values);
        db.close();
    }

    public void insertInformasi(Informasi informasi) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(field1_tb4, informasi.getId_dokter());
        values.put(field2_tb4, informasi.getLabel());
        values.put(field3_tb4, informasi.getJudul());
        values.put(field4_tb4, informasi.getDeskripsi());
        db.insert(nm_tb4, null, values);
        db.close();
    }

    public void addRecord3(NotifikasiData notifikasiData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(field1_tb3, notifikasiData.getTitle());
        values.put(field2_tb3, notifikasiData.getMessage());
        values.put(field3_tb3, notifikasiData.getWaktu());
        db.insert(nm_tb3, null, values);
        db.close();
    }

    public List<DokterData> getAllRecordListLogin() {
        List<DokterData> dokterList = new ArrayList<DokterData>();
        String selectQuery = "select * from " + nm_tb1;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DokterData dokterData = new DokterData();
                dokterData.setId(cursor.getInt(0));
                dokterData.setKode(cursor.getString(1));
                dokterData.setNama(cursor.getString(2));
                dokterList.add(dokterData);
            } while (cursor.moveToNext());
        }
        return dokterList;
    }

    public List<Informasi> getInformasi(String iddokter) {
        List<Informasi> informasis = new ArrayList<>();
        String query = "select * from " + nm_tb4 + " where " + field4_tb4 + " = " +
                "" + iddokter + " ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                Informasi informasi = new Informasi();
                informasi.setId_dokter(cursor.getString(0));
                informasi.setLabel(cursor.getString(1));
                informasi.setJudul(cursor.getString(2));
                informasi.setDeskripsi(cursor.getString(3));
            } while (cursor.moveToNext());
        }
        return informasis;
    }

    public List<DokterData> getAllRecordLoginRegister() {
        List<DokterData> dokterList = new ArrayList<DokterData>();
        String selectQuery = "select * from " + nm_tb2;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                DokterData dokterData = new DokterData();
                dokterData.setId(cursor.getInt(0));
                dokterData.setKode(cursor.getString(1));
                dokterData.setNama(cursor.getString(2));
                dokterList.add(dokterData);
            } while (cursor.moveToNext());
        }
        return dokterList;
    }

    public DataDokter getDokter(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(nm_tb1, new String[]{field2_tb, field2_tb}, field1_tb + " = ?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        DataDokter getDokter = new DataDokter(
                cursor.getString(0),
                cursor.getString(1));
        // return contact
        return getDokter;
    }

    public int getUserModelCount() {
        String countQuery = "SELECT  * FROM " + nm_tb1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int counts = cursor.getCount();
        cursor.close();
        return counts;
    }

    public void deleteModel(DokterData dokterData) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(nm_tb1, field1_tb + " = ?",
                new String[]{String.valueOf(dokterData.getKode())});
        db.close();
    }

    public void deleteListLogin() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + nm_tb1);
        db.close();
    }

    public void deleteListRegister() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + nm_tb2);
        db.close();
    }

    public void deleteInformasi(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + nm_tb4);
        db.close();
    }

}
