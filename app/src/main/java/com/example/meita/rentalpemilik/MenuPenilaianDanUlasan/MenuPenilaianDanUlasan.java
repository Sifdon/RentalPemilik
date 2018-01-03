package com.example.meita.rentalpemilik.MenuPenilaianDanUlasan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.meita.rentalpemilik.R;
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

public class MenuPenilaianDanUlasan extends Fragment {

    private RecyclerView recyclerView;
    private List<UlasanModel> ulasanModel;
    private MenuPenilaianDanUlasanAdapter adapter;
    private DatabaseReference mDatabase;
    private FirebaseAuth auth;
    String idRental;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Penilaian dan Ulasan");
        View v = inflater.inflate(R.layout.fragment_menu_penilaian_dan_ulasan, container, false);
        Firebase.setAndroidContext(getContext());

        recyclerView = (RecyclerView)v.findViewById(R.id.listView);
        progressBar = (ProgressBar)v.findViewById(R.id.progress_circle);
        recyclerView.setHasFixedSize(true);
        progressBar.setVisibility(View.VISIBLE);

        final FragmentActivity c = getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        ulasanModel = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        idRental = user.getUid();

        getUlasan();

        return v;
    }

    public void getUlasan() {
        try {
            mDatabase.child("ulasan").orderByChild("idRental").equalTo(idRental).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        UlasanModel dataUlasan = postSnapshot.getValue(UlasanModel.class);
                        ulasanModel.add(dataUlasan);
                        progressBar.setVisibility(View.GONE);
                    }
                    adapter = new MenuPenilaianDanUlasanAdapter(getActivity(), ulasanModel);
                    recyclerView.setAdapter(adapter);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }
    }
}
