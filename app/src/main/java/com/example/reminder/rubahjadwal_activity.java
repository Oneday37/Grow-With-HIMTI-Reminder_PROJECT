package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class rubahjadwal_activity extends AppCompatActivity {
    EditText etnama, etketerangan, ettanggal, etjam;
    MaterialButton btnubah;
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog timePickerDialog;
    Calendar mycalendar, mytime;
    dbjadwal dbjadwal;

    String id = "", nama = "", keterangan = "", tanggal = "", jam = "", status = dbjadwal.status_normal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rubahjadwal);

        Intent intent = getIntent();
        if (intent != null){
            id = intent.getStringExtra("id");
        }
        etnama = (EditText) findViewById(R.id.etnama);
        etketerangan = (EditText) findViewById(R.id.etketerangan);
        ettanggal = (EditText) findViewById(R.id.ettanggal);
        etjam = (EditText) findViewById(R.id.etjam);
        btnubah = (MaterialButton) findViewById(R.id.btnubah);

        setup();

        ettanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(rubahjadwal_activity.this, date, mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH),
                        mycalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        btnubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatedata();
            }
        });
    }

    private void updatedata() {
        String namabaru = etnama.getText().toString();
        String keteranganbaru = etketerangan.getText().toString();
        String tanggalbaru = ettanggal.getText().toString();
        String jambaru = etjam.getText().toString();

        if(namabaru.isEmpty() || keteranganbaru.isEmpty() || tanggalbaru.isEmpty() || jam.isEmpty()){
            Toast.makeText(rubahjadwal_activity.this, "Harap Mengisi Semua Data", Toast.LENGTH_SHORT).show();
        }else{
            SQLiteDatabase database = dbjadwal.getReadableDatabase();
            ContentValues values = new ContentValues();
            values.put(com.example.reminder.dbjadwal.MyColums.nama, namabaru);
            values.put(com.example.reminder.dbjadwal.MyColums.keterangan, keteranganbaru);
            values.put(com.example.reminder.dbjadwal.MyColums.tanggal, tanggalbaru);
            values.put(com.example.reminder.dbjadwal.MyColums.jam, jambaru);

            String selection = com.example.reminder.dbjadwal.MyColums.id + " LIKE ?";
            String[] selectionArgs = {id};
            database.update(com.example.reminder.dbjadwal.MyColums.namatabel, values, selection, selectionArgs);
            Toast.makeText(rubahjadwal_activity.this, "Data Berhasil Diubah", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(rubahjadwal_activity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
    }

    private void setup() {
        dbjadwal = new dbjadwal(getBaseContext());
        mycalendar = Calendar.getInstance();
        mytime = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                mycalendar.set(Calendar.YEAR, year);
                mycalendar.set(Calendar.MONTH, month);
                mycalendar.set(Calendar.DAY_OF_MONTH, day);

                String myformat = "dd MMMM yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myformat, Locale.US);

                ettanggal.setText(sdf.format(mycalendar.getTime()));
            }
        };
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
                mytime.set(Calendar.HOUR, hours);
                mytime.set(Calendar.MINUTE, minutes);

                String myformat = "hh:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myformat, Locale.US);

                etjam.setText(sdf.format(mytime.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));

        SQLiteDatabase db = dbjadwal.getReadableDatabase();
        String[] columns = {com.example.reminder.dbjadwal.MyColums.id, com.example.reminder.dbjadwal.MyColums.nama, com.example.reminder.dbjadwal.MyColums.keterangan,
                com.example.reminder.dbjadwal.MyColums.tanggal, com.example.reminder.dbjadwal.MyColums.jam, com.example.reminder.dbjadwal.MyColums.status};
        Cursor c = db.query(com.example.reminder.dbjadwal.MyColums.namatabel, columns, com.example.reminder.dbjadwal.MyColums.id + " = " + id, null,
                null, null, com.example.reminder.dbjadwal.MyColums.id + " ASC");

        while (c.moveToNext()){
            nama = c.getString(1);
            keterangan = c.getString(2);
            tanggal = c.getString(3);
            jam = c.getString(4);
        }
        c.close();
        etnama.setText(nama);
        etketerangan.setText(keterangan);
        ettanggal.setText(tanggal);
        etjam.setText(jam);
    }
}