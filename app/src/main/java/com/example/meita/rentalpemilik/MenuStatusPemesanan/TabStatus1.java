package com.example.meita.rentalpemilik.MenuStatusPemesanan;

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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.PemesananModel;
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

public class TabStatus1 extends Fragment {
    private RecyclerView recyclerView;
    private TabStatus1Adapter adapter;
    private List<PemesananModel> pemesananModel;
    private DatabaseReference mDatabase;
    ProgressBar progressBar;
    private FirebaseAuth auth;
    private String idRental;
    ImageView imageViewNoOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Belum Bayar");
        View v = inflater.inflate(R.layout.fragment_tab_status1, container, false);

        recyclerView = (RecyclerView) v.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(true);
        imageViewNoOrder = (ImageView)v.findViewById(R.id.ic_noOrder);

        final FragmentActivity c = getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        progressBar = (ProgressBar)v.findViewById(R.id.progress_circle);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FEBD3D"), PorterDuff.Mode.SRC_ATOP);
        pemesananModel = new ArrayList<>();
        progressBar.setVisibility(View.VISIBLE);
        imageViewNoOrder.setVisibility(View.GONE);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idRental = user.getUid();

        Firebase.setAndroidContext(getActivity());
        Firebase.setAndroidContext(getContext());

        getDataPemesanan();

        return v;
    }

    public void getDataPemesanan() {
        try {
            String status1 = "belumBayar";
            mDatabase.child("pemesananKendaraan").child(status1).orderByChild("idRental").equalTo(idRental).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            PemesananModel dataPemesanan = postSnapshot.getValue(PemesananModel.class);
                            pemesananModel.add(dataPemesanan);
                            adapter = new TabStatus1Adapter(getActivity(), pemesananModel);
                            //adding adapter to recyclerview
                            recyclerView.setAdapter(adapter);
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        imageViewNoOrder.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

        }


    }
}