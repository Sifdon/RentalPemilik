package com.example.meita.rentalpemilik.MenuProfilRental;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.meita.rentalpemilik.MenuStatusPemesanan.ItemClickListener;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.RentalModel;

import java.util.List;

/**
 * Created by meita on 10/28/2017.
 */

public class RekeningPembayaranRentalAdapter extends RecyclerView.Adapter<RekeningPembayaranRentalAdapter.ViewHolder> implements View.OnClickListener {
    private List<RentalModel> rentalModel;
    Context context;

    public RekeningPembayaranRentalAdapter(Context context, List<RentalModel> rentalModel) {
        this.rentalModel = rentalModel;
        this.context = context;
    }

    @Override
    public RekeningPembayaranRentalAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_rekening_pembayaran_rental, parent, false);
        RekeningPembayaranRentalAdapter.ViewHolder viewHolder = new RekeningPembayaranRentalAdapter.ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RekeningPembayaranRentalAdapter.ViewHolder holder, int position) {
        final RentalModel dataRental = rentalModel.get(position);
        holder.textViewNamaBankRental.setText(dataRental.getNamaBank());
        holder.textViewNamaPemilikRekeningRental.setText(dataRental.getNamaPemilikBank());
        holder.textViewNomorRekeningRental.setText(dataRental.getNomorRekeningBank());
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

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            textViewNamaBankRental = (TextView)itemView.findViewById(R.id.textViewNamaBankRental);
            textViewNamaPemilikRekeningRental = (TextView)itemView.findViewById(R.id.textViewNamaPemilikRekeningRental);
            textViewNomorRekeningRental = (TextView)itemView.findViewById(R.id.textViewNomorRekeningRental);

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