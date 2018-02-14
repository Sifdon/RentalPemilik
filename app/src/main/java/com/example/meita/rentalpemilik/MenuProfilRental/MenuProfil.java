package com.example.meita.rentalpemilik.MenuProfilRental;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MenuProfil extends Fragment {
    private RecyclerView recyclerView;
    private RekeningPembayaranRentalAdapter adapter;
    private List<RentalModel> rentalModel;
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    private FirebaseAuth auth;
    private String idRental, emailRental;
    TextView textViewNamaPemilikRental, textViewNamaRental, textViewAlamatRental,
            textViewTelpRental, textViewEmailRental, textViewKebijakanSewa, textViewKebijakanPemesanan,
            textViewKebijakanPembatalan;
    Button buttonUbahProfile;
    ImageView imageViewFotoRental;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Profil Rental");
        View v = inflater.inflate(R.layout.fragment_menu_profil, container, false);
        Firebase.setAndroidContext(getContext());

        textViewNamaPemilikRental = (TextView)v.findViewById(R.id.textViewNamaPemilikRental);
        textViewNamaRental = (TextView)v.findViewById(R.id.textViewNamaRental);
        textViewAlamatRental = (TextView)v.findViewById(R.id.textViewAlamatRental);
        textViewTelpRental = (TextView)v.findViewById(R.id.textViewTelpRental);
        textViewEmailRental = (TextView)v.findViewById(R.id.textViewEmailRental);
        textViewKebijakanSewa = (TextView)v.findViewById(R.id.textViewKebijakanSewa);
        textViewKebijakanPemesanan = (TextView)v.findViewById(R.id.textViewKebijakanPemesanan);
        textViewKebijakanPembatalan = (TextView)v.findViewById(R.id.textViewKebijakanPembatalan);
        buttonUbahProfile = (Button)v.findViewById(R.id.buttonUbahProfile);
        imageViewFotoRental = (ImageView)v.findViewById(R.id.imageView);

        recyclerView = (RecyclerView) v.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);

        progressBar = (ProgressBar)v.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FEBD3D"), PorterDuff.Mode.SRC_ATOP);
        progressBar.setVisibility(View.VISIBLE);

        final FragmentActivity c = getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);
        rentalModel = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idRental = user.getUid();
        emailRental = user.getEmail();
        Firebase.setAndroidContext(getActivity());

        buttonUbahProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PengaturanProfil.class);
                startActivity(intent);
            }
        });

        getDataRental();

        return v;
    }

    public void getDataRental() {
        mDatabase.child("rentalKendaraan").child(idRental).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                progressBar.setVisibility(View.GONE);
                RentalModel dataRental = dataSnapshot.getValue(RentalModel.class);
                textViewNamaPemilikRental.setText(dataRental.getNama_pemilik());
                textViewNamaRental.setText(dataRental.getNama_rental());
                textViewAlamatRental.setText(dataRental.getAlamat_rental());
                textViewTelpRental.setText(dataRental.getNotelfon_rental());
                textViewEmailRental.setText(emailRental);
                textViewKebijakanSewa.setText(dataRental.getkebijakanPembatalan());
                textViewKebijakanPemesanan.setText(dataRental.getkebijakanPemakaian());
                textViewKebijakanPembatalan.setText(dataRental.getkebijakanKelebihanWaktu());
                Glide.with(getActivity()).load(dataRental.getUriFotoProfil()).into(imageViewFotoRental);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    RentalModel dataRekening = postSnapshot.getValue(RentalModel.class);
                    rentalModel.add(dataRekening);
                    adapter = new RekeningPembayaranRentalAdapter(getActivity(), rentalModel);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        progressBar.setVisibility(View.GONE);
    }

}
