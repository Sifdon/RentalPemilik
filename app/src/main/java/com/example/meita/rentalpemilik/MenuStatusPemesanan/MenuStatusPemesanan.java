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
import android.widget.Toast;

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
        getActivity().setTitle("Kelola Pesanan");
        View v = inflater.inflate(R.layout.fragment_menu_status_pemesanan, container, false);
        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);

        try {
            int position = getArguments().getInt("tab1");
            viewPager.setCurrentItem(position);
         } catch (Exception e) {

         }

        try {
            int position = getArguments().getInt("tab2");
            viewPager.setCurrentItem(position);
        } catch (Exception e) {

        }

        try {
            int position = getArguments().getInt("tab3");
            viewPager.setCurrentItem(position);
        } catch (Exception e) {

        }

        try {
            int position = getArguments().getInt("tab4");
            viewPager.setCurrentItem(position);
        } catch (Exception e) {

        }

//        try {
//            Bundle extras = getActivity().getIntent().getExtras();
//            if (extras.getInt("notifStatus1") == 1) {
//                viewPager.setCurrentItem(0);
//                Toast.makeText(getActivity(), "tab belum bayar ", Toast.LENGTH_LONG).show();
//            } else if (extras.getInt("notifStatus2") == 2) {
//                viewPager.setCurrentItem(1);
//                Toast.makeText(getActivity(), "tab menunggu konfirmasi ", Toast.LENGTH_LONG).show();
//            }
//         } catch (Exception e) {
//
//         }
//
//
//
//        try {
//            Bundle extras = getActivity().getIntent().getExtras();
//            String valueStatusHalaman0 = extras.getString("a");
//            if (valueStatusHalaman0.equals("laper")) {
//                viewPager.setCurrentItem(0);
//            }
//        } catch (Exception e) {
//        }
//
//        try {
//            Bundle extras = getActivity().getIntent().getExtras();
//            String valueStatusHalaman1 = extras.getString("notifStatus2");
//            if (valueStatusHalaman1.equals("menungguKonfirmasi")) {
//                viewPager.setCurrentItem(1);
//            }
//        } catch (Exception e) {
//        }

//        try {
//            Bundle extras = getActivity().getIntent().getExtras();
//            int valueStatusHalaman1 = extras.getInt("notifStatus1", 0);
//            if (valueStatusHalaman1 == 1) {
//                Toast.makeText(getActivity(), "tab belum bayar ", Toast.LENGTH_LONG).show();
//
//            }
//        } catch (Exception e) {
//        }
//
//        try {
//            Bundle extras = getActivity().getIntent().getExtras();
//            int valueStatusHalaman2 = extras.getInt("valueHalamanStatus2", 1);
//            if (valueStatusHalaman2 == 2) {
//                viewPager.setCurrentItem(1);
//                Toast.makeText(getActivity(), "tab menunggu konfirmasi ", Toast.LENGTH_LONG).show();
//            }
//        } catch (Exception e) {
//        }


//
//        try {
//            Bundle extras = getActivity().getIntent().getExtras();
//            int valueStatusHalaman3 = extras.getInt("halamanStatus3", 2);
//            if (valueStatusHalaman3 == 3) {
//                viewPager.setCurrentItem(3);
//            }
//        } catch (Exception e) {
//        }
//
//        try {
//            Bundle extras = getActivity().getIntent().getExtras();
//            int valueStatusHalaman4= extras.getInt("halamanStatus4", 3);
//            if (valueStatusHalaman4 == 4) {
//                viewPager.setCurrentItem(5);
//            }
//        } catch (Exception e) {
//        }
//
//        try {
//            Bundle extras = getActivity().getIntent().getExtras();
//            String valueHalamanPemberitahuanBelumBayar = extras.getString("valueHalamanPemberitahuanBelumBayar");
//            if (valueHalamanPemberitahuanBelumBayar.equals("pemberitahuanBelumBayar")) {
//                viewPager.setCurrentItem(0);
//            }
//        } catch (Exception e) {
//        }


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