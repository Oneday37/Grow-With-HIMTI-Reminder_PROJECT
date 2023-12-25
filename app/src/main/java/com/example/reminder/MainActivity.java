package com.example.reminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private dbjadwal mydatabase;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<String> judullist, idlist, keteranganlist, tanggallist, jamlist, statuslist;

    MaterialButton btntambah;
    RecyclerView rvjadwal;
    TextView tvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btntambah = (MaterialButton) findViewById(R.id.btntambah);
        rvjadwal = (RecyclerView) findViewById(R.id.rvjadwal);
        tvInfo = (TextView) findViewById(R.id.tvInfo);

        setup();
        btntambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, tambahjadwal_activity.class);
                startActivity(intent);
            }
        });
    }

    private void setup() {
        judullist = new ArrayList<>();
        idlist = new ArrayList<>();
        keteranganlist = new ArrayList<>();
        tanggallist = new ArrayList<>();
        jamlist = new ArrayList<>();
        statuslist = new ArrayList<>();
        mydatabase = new dbjadwal(getBaseContext());
        
        getdata();

        layoutManager = new LinearLayoutManager(this);
        rvjadwal.setLayoutManager(layoutManager);
        rvjadwal.setHasFixedSize(true);
        adapter = new RecyclerViewAdapter(MainActivity.this, judullist, idlist, keteranganlist, tanggallist, jamlist, statuslist);

        rvjadwal.setAdapter(adapter);
    }

    @SuppressLint("Recycle")
    private void getdata() {
        SQLiteDatabase readdata = mydatabase.getReadableDatabase();
        Cursor cursor = readdata.rawQuery("SELECT * FROM " + dbjadwal.MyColums.namatabel, null);
        cursor.moveToFirst();

        if(cursor.getCount() > 0){
            tvInfo.setVisibility(View.GONE);
            rvjadwal.setVisibility(View.VISIBLE);
        }else{
            tvInfo.setVisibility(View.VISIBLE);
            rvjadwal.setVisibility(View.GONE);
        }

        for (int count = 0; count < cursor.getCount(); count++){
            cursor.moveToPosition(count);

            idlist.add(cursor.getString(0));
            judullist.add(cursor.getString(1));
            keteranganlist.add(cursor.getString(2));
            tanggallist.add(cursor.getString(3));
            jamlist.add(cursor.getString(4));
            statuslist.add(cursor.getString(5));
        }
    }
}