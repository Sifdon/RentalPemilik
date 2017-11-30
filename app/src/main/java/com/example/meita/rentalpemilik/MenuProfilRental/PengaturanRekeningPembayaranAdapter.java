package com.example.meita.rentalpemilik.MenuProfilRental;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meita.rentalpemilik.MenuStatusPemesanan.ItemClickListener;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by meita on 10/28/2017.
 */

public class PengaturanRekeningPembayaranAdapter extends RecyclerView.Adapter<PengaturanRekeningPembayaranAdapter.ViewHolder> implements View.OnClickListener {
    private List<RentalModel> rentalModel;
    Context context;
    DatabaseReference mDatabase;
    String idRekening, idRental;

    public PengaturanRekeningPembayaranAdapter(Context context, List<RentalModel> rentalModel, String idRental) {
        this.rentalModel = rentalModel;
        this.context = context;
        this.idRental = idRental;
    }

    @Override
    public PengaturanRekeningPembayaranAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_pengaturan_rekening_pembayaran, parent, false);
        PengaturanRekeningPembayaranAdapter.ViewHolder viewHolder = new PengaturanRekeningPembayaranAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PengaturanRekeningPembayaranAdapter.ViewHolder holder, int position) {
        final RentalModel dataRental = rentalModel.get(position);
        String idRekeningRental = dataRental.getIdRekening();
        idRekening = idRekeningRental;
        holder.textViewNamaBankRental.setText(dataRental.getNamaBank());
        holder.textViewNamaPemilikRekeningRental.setText(dataRental.getNamaPemilikBank());
        holder.textViewNomorRekeningRental.setText(dataRental.getNomorRekeningBank());
        holder.hapusRekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialog();
            }
        });

        holder.editRekening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, UbahRekeningPembayaran.class);
                bundle.putString("idRekening", dataRental.getIdRekening());
                intent.putExtras(bundle);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return rentalModel.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        public TextView textViewNamaBankRental, textViewNamaPemilikRekeningRental, textViewNomorRekeningRental;
        ImageView editRekening, hapusRekening;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            textViewNamaBankRental = (TextView) itemView.findViewById(R.id.textViewNamaBankRental);
            textViewNamaPemilikRekeningRental = (TextView) itemView.findViewById(R.id.textViewNamaPemilikRekeningRental);
            textViewNomorRekeningRental = (TextView) itemView.findViewById(R.id.textViewNomorRekeningRental);
            editRekening = (ImageView) itemView.findViewById(R.id.editRekening);
            hapusRekening = (ImageView) itemView.findViewById(R.id.hapusRekening);

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

    public void showDeleteDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View dialogView = inflater.inflate(R.layout.delete_dialog, null);
        dialogBuilder.setView(dialogView);

        final Button buttonYa = (Button) dialogView.findViewById(R.id.buttonDeleteYa);
        final Button buttonTidak = (Button) dialogView.findViewById(R.id.buttonDeleteTidak);

        final AlertDialog b = dialogBuilder.create();
        b.show();

        buttonYa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hapusRekening();
                b.dismiss();

            }
        });

        buttonTidak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b.dismiss();
            }
        });
    }

    public void hapusRekening() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").child(idRekening).removeValue();
        Toast.makeText(context, "Rekening Anda Berhasil Dihapus", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context, PengaturanRekeningPembayaran.class);
        context.startActivity(intent);
    }
}
