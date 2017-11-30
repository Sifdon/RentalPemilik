package com.example.meita.rentalpemilik.MenuManajemenKendaraan;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meita.rentalpemilik.Base.BaseActivity;
import com.example.meita.rentalpemilik.Constants;
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

public class MenuManajemenKendaraanAdapter extends RecyclerView.Adapter<MenuManajemenKendaraanAdapter.ViewHolder> implements View.OnClickListener {

    private Activity activity;
    private Context context;
    private List<PostRef> postRefs;
    private DatabaseReference mDatabase;


    public MenuManajemenKendaraanAdapter(Activity activity, Context context, List<PostRef> postRefs) {
        this.activity = activity;
        this.postRefs = postRefs;
        this.context = context;
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_menu_manajemen_kendaraan, parent, false);
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
                    final KendaraanModel dataKendaraan = dataSnapshot.getValue(KendaraanModel.class);
                    final PostRef postRef = dataSnapshot.getValue(PostRef.class);
                    if(dataKendaraan != null) {
                        holder.tipeKendaraan.setText(dataKendaraan.getTipeKendaraan());
                        holder.lamaPenyewaan.setText(dataKendaraan.getLamaPenyewaan());
                        holder.hargaSewa.setText("Rp."+ BaseActivity.rupiah().format(dataKendaraan.getHargaSewa()));
                        ImageLoader.getInstance().loadImageOther(context, dataKendaraan.getUriFotoKendaraan().get(0), holder.fotoKendaraan);
                        if (dataKendaraan.isSupir() == true ) {
                            holder.textViewDenganSupir.setVisibility(View.VISIBLE);
                            holder.imageChecklistSupirTrue.setVisibility(View.VISIBLE);
                            holder.textViewTanpaSupir.setVisibility(View.GONE);
                            holder.imageCheckListSupirFalse.setVisibility(View.GONE);
                        } else {
                            holder.textViewTanpaSupir.setVisibility(View.VISIBLE);
                            holder.imageCheckListSupirFalse.setVisibility(View.VISIBLE);
                            holder.textViewDenganSupir.setVisibility(View.GONE);
                            holder.imageChecklistSupirTrue.setVisibility(View.GONE);
                        }

                        if (dataKendaraan.isBahanBakar() == true ) {
                            holder.textViewDenganBBM.setVisibility(View.VISIBLE);
                            holder.imageCheckListBBMTrue.setVisibility(View.VISIBLE);
                            holder.textViewTanpaBBM.setVisibility(View.GONE);
                            holder.imageCheckListBBMFalse.setVisibility(View.GONE);
                        } else {
                            holder.textViewTanpaBBM.setVisibility(View.VISIBLE);
                            holder.imageCheckListBBMFalse.setVisibility(View.VISIBLE);
                            holder.textViewDenganBBM.setVisibility(View.GONE);
                            holder.imageCheckListBBMTrue.setVisibility(View.GONE);
                        }
                        holder.setClickListener(new ItemClickListener() {
                            @Override
                            public void onClick(View view, int position, boolean isLongClick) {
                                if (isLongClick) {
                                    Intent intent = new Intent(context, MenuLihatDetailKendaraan.class);
                                    intent.putExtra(Constants.KENDARAAN, dataKendaraan);
                                    intent.putExtra(Constants.POSTREF, postRef);
                                    context.startActivity(intent);
                                } else {
                                    Intent intent = new Intent(context, MenuLihatDetailKendaraan.class);
                                    intent.putExtra(Constants.KENDARAAN, dataKendaraan);
                                    intent.putExtra(Constants.POSTREF, postRef);
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

        public TextView tipeKendaraan, lamaPenyewaan, hargaSewa,  textViewDenganSupir, textViewTanpaSupir, textViewDenganBBM, textViewTanpaBBM;
        public ImageView fotoKendaraan, imageChecklistSupirTrue, imageCheckListSupirFalse, imageCheckListBBMTrue, imageCheckListBBMFalse;
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
            textViewDenganSupir = (TextView)itemView.findViewById(R.id.txtViewSupir);
            textViewTanpaSupir = (TextView)itemView.findViewById(R.id.txtViewSupirFalse);
            imageChecklistSupirTrue = (ImageView)itemView.findViewById(R.id.icCheckListDenganSupir);
            imageCheckListSupirFalse = (ImageView)itemView.findViewById(R.id.icCheckListTanpaSupir);
            textViewDenganBBM = (TextView)itemView.findViewById(R.id.txtViewBBMTrue);
            textViewTanpaBBM = (TextView)itemView.findViewById(R.id.txtViewBBMFalse);

            imageCheckListBBMTrue = (ImageView)itemView.findViewById(R.id.icCheckListDenganBBM);
            imageCheckListBBMFalse = (ImageView)itemView.findViewById(R.id.icCheckListTanpaBBM);
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