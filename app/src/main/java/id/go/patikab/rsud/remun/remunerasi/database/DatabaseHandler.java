package id.go.patikab.rsud.remun.remunerasi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import id.go.patikab.rsud.remun.remunerasi.database.model.DokterData;
import id.go.patikab.rsud.remun.remunerasi.entity.DataDokter;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static String nama_db = "db_dotker";
    private static String nm_tb1 = "tb_dokter";
    private static String field1_tb = "kd_dokter";
    private static String field2_tb = "nama_dokter";
    private static String nm_tb2 = "tb_dokter_2";

    private static final String tb1 ="create table " + nm_tb1 + " ( " +
            "id integer primary key autoincrement, " +
            field1_tb + " text," +
            field2_tb + " text)";
    private static final String tb2 = "create table " + nm_tb2 + " ( " +
            "id integer primary key autoincrement, " +
            field1_tb + " text," +
            field2_tb + " text)";
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists " + nm_tb1);
    }

    public void addRecord(DokterData dokterData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(field1_tb, dokterData.getKode());
        values.put(field2_tb, dokterData.getNama());
        db.insert(nm_tb1, null, values);
        db.close();
    }

    public void addRecord2(DokterData dokterData) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(field1_tb, dokterData.getKode());
        values.put(field2_tb, dokterData.getNama());
        db.insert(nm_tb2, null, values);
        db.close();
    }

    public List<DokterData> getAllrecord() {
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

    public List<DokterData> getAllrecord2() {
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

    public void deleteAllrc() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + nm_tb1);
        db.close();
    }
    public void deleteAllrc2() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + nm_tb2);
        db.close();
    }


}
