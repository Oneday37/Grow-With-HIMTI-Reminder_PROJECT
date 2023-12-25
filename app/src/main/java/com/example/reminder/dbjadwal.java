package com.example.reminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class dbjadwal extends SQLiteOpenHelper {
    private static final String namadb = "jadwal.db";
    private static final int versidb = 1;
    public static final String status_normal = "Normal";
    public static final String status_sudah = "Sudah";
    public static final String status_belum = "Belum";

    static abstract class MyColums implements BaseColumns{
      static final String namatabel = "Jadwal";
      static final String id = "ID";
      static final String nama = "Nama";
      static final String keterangan = "Keterangan";
      static final String tanggal = "Tanggal";
      static final String jam = "Jam";
      static final String status = "Status";
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+ MyColums.namatabel + "(" + MyColums.id + " TEXT PRIMARY KEY, " + MyColums.nama + " TEXT NOT NULL, "
            + MyColums.keterangan + " TEXT NOT NULL, " + MyColums.tanggal + " TEXT NOT NULL, " + MyColums.jam + " TEXT NOT NULL, " + MyColums.status + " TEXT NOT NULL)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + MyColums.namatabel;

    public dbjadwal (Context context){
        super(context, namadb, null, versidb);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }
}
