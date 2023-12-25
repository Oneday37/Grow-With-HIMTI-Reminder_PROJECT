package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class tambahjadwal_activity extends AppCompatActivity {
    EditText etnama, etketerangan, ettanggal, etjam;
    MaterialButton btntambah;
    DatePickerDialog.OnDateSetListener date;
    TimePickerDialog timePickerDialog;
    Calendar mycalendar, mytime;
    dbjadwal dbjadwal;

    String id = "", nama = "", keterangan = "", tanggal = "", jam = "", status = dbjadwal.status_normal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambahjadwal);

        etnama = (EditText) findViewById(R.id.etnama);
        etketerangan = (EditText) findViewById(R.id.etketerangan);
        ettanggal = (EditText) findViewById(R.id.ettanggal);
        etjam = (EditText) findViewById(R.id.etjam);
        btntambah = (MaterialButton) findViewById(R.id.btntambah);

        setup();

        ettanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(tambahjadwal_activity.this, date, mycalendar.get(Calendar.YEAR), mycalendar.get(Calendar.MONTH),
                        mycalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        etjam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialog.show();
            }
        });

        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savedata();
            }
        });
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

                String myformat = "HH:mm";
                SimpleDateFormat sdf = new SimpleDateFormat(myformat, Locale.US);

                etjam.setText(sdf.format(mytime.getTime()));
            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), DateFormat.is24HourFormat(this));
    }

    private void savedata(){
        long mills = System.currentTimeMillis();
        long seconds = mills / 1000;

        String id = String.valueOf(seconds);
        nama = etnama.getText().toString();
        keterangan = etketerangan.getText().toString();
        tanggal = ettanggal.getText().toString();
        jam = etjam.getText().toString();

        if (nama.isEmpty() || keterangan.isEmpty() || tanggal.isEmpty() || jam.isEmpty() || nama.replace(" ", "").isEmpty() ||
                keterangan.replace(" ", "").isEmpty() || tanggal.replace(" ", "").isEmpty() || jam.replace(" ", "").isEmpty()){
            Toast.makeText(tambahjadwal_activity.this, "Harap Mengisi Semua Data!!!", Toast.LENGTH_SHORT).show();
        }else{
            SQLiteDatabase create = dbjadwal.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(com.example.reminder.dbjadwal.MyColums.id, id);
            values.put(com.example.reminder.dbjadwal.MyColums.nama, nama);
            values.put(com.example.reminder.dbjadwal.MyColums.keterangan, keterangan);
            values.put(com.example.reminder.dbjadwal.MyColums.tanggal, tanggal);
            values.put(com.example.reminder.dbjadwal.MyColums.jam, jam);
            values.put(com.example.reminder.dbjadwal.MyColums.status, status);

            Log.d("logvalue", "savedata: " + values);
            create.insert(com.example.reminder.dbjadwal.MyColums.namatabel, null, values);
            Toast.makeText(tambahjadwal_activity.this, "Jadwal Berhasil Ditambahkan", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(tambahjadwal_activity.this, MainActivity.class);
            startActivity(intent);

            finish();
        }
    }
}