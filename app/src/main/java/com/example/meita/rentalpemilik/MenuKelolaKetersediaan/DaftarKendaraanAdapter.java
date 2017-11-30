package com.example.meita.rentalpemilik.MenuKelolaKetersediaan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meita.rentalpemilik.MenuManajemenKendaraan.ImageLoader;
import com.example.meita.rentalpemilik.MenuManajemenKendaraan.ItemClickListener;
import com.example.meita.rentalpemilik.MenuManajemenKendaraan.PostRef;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by meita on 28/07/2017.
 */

public class DaftarKendaraanAdapter extends RecyclerView.Adapter<DaftarKendaraanAdapter.ViewHolder> implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private List<KendaraanModel> dataKendaraan;
    private List<PostRef> postRefs;
    private DatabaseReference mDatabase;


    public DaftarKendaraanAdapter(Activity activity, Context context, List<PostRef> postRefs) {
        this.activity = activity;
        this.postRefs = postRefs;
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_daftar_kendaraan, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final PostRef postRef = postRefs.get(position);
        mDatabase.child("kendaraan").child((postRef.getKategoriKendaraan())).child((postRef.getIdKendaraan())).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    final KendaraanModel data = dataSnapshot.getValue(KendaraanModel.class);
                    final PostRef postRef = dataSnapshot.getValue(PostRef.class);
                    if(data != null) {
                        holder.tipeKendaraan.setText(data.getTipeKendaraan());
                        holder.lamaPenyewaan.setText(data.getLamaPenyewaan());
                        holder.hargaSewa.setText(String.valueOf(data.getHargaSewa()));
                        ImageLoader.getInstance().loadImageOther(context, data.getUriFotoKendaraan().get(0), holder.fotoKendaraan);
                        if (data.isSupir() == true ) {
                            holder.supir.setVisibility(View.VISIBLE);
                            holder.checkList.setVisibility(View.VISIBLE);
                            holder.supirFalse.setVisibility(View.GONE);
                            holder.checkListFalse.setVisibility(View.GONE);
                        } else {
                            holder.supir.setVisibility(View.GONE);
                            holder.checkList.setVisibility(View.GONE);
                            holder.supirFalse.setVisibility(View.VISIBLE);
                            holder.checkListFalse.setVisibility(View.VISIBLE);
                        }
                        holder.setClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                if (isLongClick) {
                                    Bundle bundle = new Bundle();
                                    Intent intent = new Intent(context, CekTanggalKetersediaan.class);
                                    bundle.putString("idKendaraan", data.getIdKendaraan());
                                    bundle.putString("kategoriKendaraan", data.getKategoriKendaraan());
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                } else {
                                    Bundle bundle = new Bundle();
                                    Intent intent = new Intent(context, CekTanggalKetersediaan.class);
                                    bundle.putString("idKendaraan", data.getIdKendaraan());
                                    bundle.putString("kategoriKendaraan", data.getKategoriKendaraan());
                                    intent.putExtras(bundle);
                                    context.startActivity(intent);
                                }
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    @Override
    public int getItemCount() {
        return postRefs.size();
    }

    @Override
    public void onClick(View view) {

    }


    public static class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener, View.OnLongClickListener {

        public TextView tipeKendaraan, lamaPenyewaan, hargaSewa, supir, supirFalse;
        public ImageView fotoKendaraan,checkList, checkListFalse;
        private ItemClickListener clickListener;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tipeKendaraan = (TextView) itemView.findViewById(R.id.tipe_kendaraan);
            lamaPenyewaan = (TextView) itemView.findViewById(R.id.lama_penyewaan);
            hargaSewa = (TextView) itemView.findViewById(R.id.harga_sewa);
            fotoKendaraan = (ImageView) itemView.findViewById(R.id.imageViewFotoKendaraan);
            supir = (TextView)itemView.findViewById(R.id.txtViewSupir);
            checkList = (ImageView)itemView.findViewById(R.id.icCheckList);
            supirFalse = (TextView)itemView.findViewById(R.id.txtViewSupirFalse);
            checkListFalse = (ImageView)itemView.findViewById(R.id.icCheckListFalse);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}
