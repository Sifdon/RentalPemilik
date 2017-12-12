package com.example.meita.rentalpemilik.MenuKelolaKetersediaan;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meita.rentalpemilik.MenuManajemenKendaraan.KendaraanReference;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by meita on 13/06/2017.
 */

public class DaftarKendaraanFragment extends Fragment {

    public static final String idKendaraan = "idKendaraan";
    private RecyclerView recyclerView;
    private DaftarKendaraanAdapter adapter;
    private DatabaseReference mDatabase;
    private List<KendaraanModel> dataKendaraan;
    private Activity activity;

    private List<KendaraanReference> postRefs = new ArrayList<>();

    private FirebaseAuth auth;
    private String userID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Kelola Ketersediaan Kendaraan");
        View v = inflater.inflate(R.layout.fragment_daftar_kendaraan, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.listViewKendaraan);
        recyclerView.setHasFixedSize(true);

        final FragmentActivity c = getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        dataKendaraan = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();
        Firebase.setAndroidContext(getActivity());

        getDaftarKendaraan();

        return v;
    }

    public void getDaftarKendaraan() {
        getAllPost(userID).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    if (dataSnapshot != null) {
                        KendaraanReference postRef = dataSnapshot.getValue(KendaraanReference.class);
                        if (!postRefs.contains(postRef)) {
                            postRefs.add(postRef);
                            adapter = new DaftarKendaraanAdapter(activity, getActivity(), postRefs);
                            //adding adapter to recyclerview
                            recyclerView.setAdapter(adapter);
                        }
                    }
                } catch (Exception e) {
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                try {
                    KendaraanReference postRef = dataSnapshot.getValue(KendaraanReference.class);
                    int indexMyPostInList = IndexProduk(postRef);
                    postRefs.remove(indexMyPostInList);
                    adapter = new DaftarKendaraanAdapter(activity, getActivity(), postRefs);
                    //adding adapter to recyclerview
                    recyclerView.setAdapter(adapter);
                } catch (Exception e) {
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private int IndexProduk(KendaraanReference postRef){
        int index = 0;
        for (int i=0; i < postRefs.size(); i++){
            if (postRefs.get(i).getIdKendaraan().equals(postRef.getIdKendaraan())){
                index = i;
                break;
            }
        }
        return index;
    }

    public Query getAllPost(String userID) {
        Query query = mDatabase.child("rentalKendaraan").child(userID).child("kendaraan");
        return query;
    }
}