package com.example.reminder;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.button.MaterialButton;

public class dialogdetail extends Dialog {
    TextView tvtanggal, tvstatus, tvjudul, tvketerangan;
    ImageView ivclose, ivdelete;
    MaterialButton btnsudah, btnbelum, btnubah;
    Activity context;
    String id = "", status = "", tanggal = "", jam = "", keterangan = "", judul = "";

    public dialogdetail(@NonNull Activity context, String id){
        super(context);
        this.context = context;
        this.id = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layoutdialogdetail);

        setCancelable(true);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        tvtanggal = (TextView) findViewById(R.id.tvtanggal);
        tvstatus = (TextView) findViewById(R.id.tvstatus);
        tvjudul = (TextView) findViewById(R.id.tvjudul);
        tvketerangan = (TextView) findViewById(R.id.tvketerangan);
        ivclose = (ImageView) findViewById(R.id.ivclose);
        ivdelete = (ImageView) findViewById(R.id.ivdelete);
        btnsudah = (MaterialButton) findViewById(R.id.btnsudah);
        btnbelum = (MaterialButton) findViewById(R.id.btnbelum);
        btnubah = (MaterialButton) findViewById(R.id.btnubah);

        getdata();

        ivclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        btnsudah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatetosudah();
            }
        });

        btnbelum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatetobelum();
            }
        });
        ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapusdata();
            }
        });
        btnubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, rubahjadwal_activity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
                dialogdetail.this.dismiss();
            }
        });
    }

    private void getdata() {
        dbjadwal getDatabase = new dbjadwal(context);
        SQLiteDatabase db = getDatabase.getReadableDatabase();
        String[] columns = {dbjadwal.MyColums.id, dbjadwal.MyColums.nama, dbjadwal.MyColums.keterangan, dbjadwal.MyColums.tanggal, dbjadwal.MyColums.jam,
                dbjadwal.MyColums.status};
        Cursor c = db.query(dbjadwal.MyColums.namatabel, columns, dbjadwal.MyColums.id + " = " + id, null, null, null,
                dbjadwal.MyColums.id + " ASC");

        while(c.moveToNext()){
            judul = c.getString(1);
            keterangan = c.getString(2);
            tanggal = c.getString(3);
            jam = c.getString(4);
            status = c.getString(5);
        }
        c.close();
        tvjudul.setText(judul);
        tvstatus.setText(status);
        tvtanggal.setText(tanggal + " "+ jam);
        tvketerangan.setText(keterangan);
    }

    private void updatetosudah() {
        if (status.equalsIgnoreCase(dbjadwal.status_sudah)){
            Toast.makeText(context, "Tidak Dapat Memproses Permintaan", Toast.LENGTH_SHORT).show();
            dismiss();
        }else{
            dbjadwal getDatabase = new dbjadwal(context);
            SQLiteDatabase db = getDatabase.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(dbjadwal.MyColums.status, dbjadwal.status_sudah);
            String selection = dbjadwal.MyColums.id + " LIKE ?";
            String[] selectionArgs = {id};
            db.update(dbjadwal.MyColums.namatabel, values, selection, selectionArgs);

            Toast.makeText(context, "Status Berhasil Diubah", Toast.LENGTH_SHORT).show();
            dismiss();
            context.recreate();
        }
    }

    private void updatetobelum() {
        if (status.equalsIgnoreCase(dbjadwal.status_belum)){
            Toast.makeText(context, "Tidak Dapat Memproses Permintaan", Toast.LENGTH_SHORT).show();
            dismiss();
        }else{
            dbjadwal getDatabase = new dbjadwal(context);
            SQLiteDatabase db = getDatabase.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(dbjadwal.MyColums.status, dbjadwal.status_belum);
            String selection = dbjadwal.MyColums.id + " LIKE ?";
            String[] selectionArgs = {id};
            db.update(dbjadwal.MyColums.namatabel, values, selection, selectionArgs);

            Toast.makeText(context, "Status Berhasil Diubah", Toast.LENGTH_SHORT).show();
            dismiss();
            context.recreate();
        }
    }

    private void hapusdata() {
        dbjadwal getDatabase = new dbjadwal(context);
        SQLiteDatabase deletedata = getDatabase.getWritableDatabase();
        String selection = dbjadwal.MyColums.id + " LIKE ?";
        String[] selectionArgs = {id};
        deletedata.delete(dbjadwal.MyColums.namatabel, selection, selectionArgs);

        dismiss();
        context.recreate();

        Toast.makeText(context, "Jadwal Berhasil Dihapus", Toast.LENGTH_LONG).show();
    }
}
