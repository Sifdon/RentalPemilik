package com.example.meita.rentalpemilik.MenuStatusPemesanan;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meita.rentalpemilik.R;

import java.util.ArrayList;
import java.util.List;

public class MenuStatusPemesanan extends Fragment {
    ViewPager viewPager;
    TabLayout tabLayout;
    View view;
    Adapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Pencarian Kendaraan");
        View v = inflater.inflate(R.layout.fragment_menu_status_pemesanan, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        return v;

    }

    private void setupViewPager(ViewPager viewPager) {
        adapter = new Adapter(getChildFragmentManager());
        adapter.addFragment(new TabStatus1(), "Belum Bayar");
        adapter.addFragment(new TabStatus2(), "Menunggu Konfirmasi");
        adapter.addFragment(new TabStatus3(), "Pemesanan Berhasil");
        adapter.addFragment(new TabStatus4(), "Selesai");
        adapter.addFragment(new TabStatus5(), "Pengajuan Pembatalan");
        adapter.addFragment(new TabStatus6(), "Batal");
        viewPager.setAdapter(adapter);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(android.support.v4.app.FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}