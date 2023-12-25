package com.example.reminder;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<String> judullist, idlist, keteranganlist, tanggallist, jamlist, statuslist;
    Activity activity;

    public RecyclerViewAdapter(Activity activity, ArrayList judullist, ArrayList idlist, ArrayList keteranganlist, ArrayList tanggallist, ArrayList jamlist,
                               ArrayList statuslist){
        this.activity = activity;
        this.judullist = judullist;
        this.idlist = idlist;
        this.keteranganlist = keteranganlist;
        this.tanggallist = tanggallist;
        this.jamlist = jamlist;
        this.statuslist = statuslist;
    }
    class ViewHolder extends  RecyclerView.ViewHolder{
        TextView tvjam, tvtanggal, tvjudul, tvketerangan;
        RelativeLayout btnmenu;
        ImageView ivindicator;

        ViewHolder(View itemView){
            super(itemView);

            tvjam = itemView.findViewById(R.id.tvjam);
            tvtanggal = itemView.findViewById(R.id.tvtanggal);
            tvjudul = itemView.findViewById(R.id.tvjudul);
            tvketerangan = itemView.findViewById(R.id.tvketerangan);
            btnmenu = itemView.findViewById(R.id.btnmenu);
            ivindicator = itemView.findViewById(R.id.ivindicator);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") final int position){
        final String jam = jamlist.get(position);
        final String tanggal = tanggallist.get(position);
        final String judul = judullist.get(position);
        final String keterangan = keteranganlist.get(position);

        if (statuslist.get(position).equalsIgnoreCase(dbjadwal.status_sudah)){
            holder.ivindicator.setColorFilter(ContextCompat.getColor(activity, R.color.addbutton), PorterDuff.Mode.SRC_IN);
        }else if (statuslist.get(position).equalsIgnoreCase(dbjadwal.status_belum)){
            holder.ivindicator.setColorFilter(ContextCompat.getColor(activity, R.color.buttonbelum), PorterDuff.Mode.SRC_IN);
        }else{
            holder.ivindicator.setColorFilter(ContextCompat.getColor(activity, R.color.blue), PorterDuff.Mode.SRC_IN);
        }

        holder.tvjam.setText(jam);
        holder.tvtanggal.setText(tanggal);
        holder.tvjudul.setText(judul);
        holder.tvketerangan.setText(keterangan);
        holder.btnmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogdetail dialogdetail = new dialogdetail(activity, idlist.get(position));
                dialogdetail.show();
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_child_jadwal, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount(){
        return judullist.size();
    }
}
