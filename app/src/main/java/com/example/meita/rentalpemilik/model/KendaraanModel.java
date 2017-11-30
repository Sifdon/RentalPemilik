package com.example.meita.rentalpemilik.model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by meita on 07/07/2017.
 */

public class KendaraanModel implements Serializable {
   // public String uriFotoKendaraan;
    public String idRental;
    public String idKendaraan, kategoriKendaraan, tipeKendaraan, fasilitasKendaraan,  lamaPenyewaan, jumlahPenumpang, areaPemakaian;
    int jumlahKendaraan;
    double hargaSewa;
    boolean supir, bahanBakar;
    ArrayList<String> uriFotoKendaraan = new ArrayList<>();

    public KendaraanModel() {

    }

    public KendaraanModel(String idKendaraan, String kategoriKendaraan) {
        this.idKendaraan = idKendaraan;
        this.kategoriKendaraan = kategoriKendaraan;
    }

    public KendaraanModel(String idRental, String idKendaraan, String kategoriKendaraan, String tipeKendaraan, String fasilitasKendaraan,
                          double hargaSewa, String lamaPenyewaan, String jumlahPenumpang, String areaPemakaian, int jumlahKendaraan, boolean supir, boolean bahanBakar) {
        //this.uriFotoKendaraan = uriFotoKendaraan;
        this.idRental = idRental;
        this.idKendaraan = idKendaraan;
        this.kategoriKendaraan = kategoriKendaraan;
        this.tipeKendaraan = tipeKendaraan;
        this.fasilitasKendaraan = fasilitasKendaraan;
        this.hargaSewa = hargaSewa;
        this.lamaPenyewaan = lamaPenyewaan;
        this.jumlahPenumpang = jumlahPenumpang;
        this.areaPemakaian = areaPemakaian;
        this.jumlahKendaraan = jumlahKendaraan;
        this.supir = supir;
        this.bahanBakar = bahanBakar;
    }

    public ArrayList<String> getUriFotoKendaraan() {
        return uriFotoKendaraan;
    }

    public void setUriFotoKendaraan(ArrayList<String> uriFotoKendaraan) {
        this.uriFotoKendaraan = uriFotoKendaraan;
    }
    //    public String getUriFotoKendaraan() {
//        return uriFotoKendaraan;
//    }
//
//    public void setUriFotoKendaraan(String uriFotoKendaraan) {
//        this.uriFotoKendaraan = uriFotoKendaraan;
//    }

    public String getIdRental() {
        return idRental;
    }

    public void setIdRental(String idRental) {
        this.idRental = idRental;
    }

    public String getIdKendaraan() {
        return idKendaraan;
    }

    public void setIdKendaraan(String idKendaraan) {
        this.idKendaraan = idKendaraan;
    }

    public String getKategoriKendaraan() {
        return kategoriKendaraan;
    }

    public void setKategoriKendaraan(String kategoriKendaraan) {
        this.kategoriKendaraan = kategoriKendaraan;
    }

    public String getTipeKendaraan() {
        return tipeKendaraan;
    }

    public void setTipeKendaraan(String tipeKendaraan) {
        this.tipeKendaraan = tipeKendaraan;
    }

    public String getFasilitasKendaraan() {
        return fasilitasKendaraan;
    }

    public void setFasilitasKendaraan(String fasilitasKendaraan) {
        this.fasilitasKendaraan = fasilitasKendaraan;
    }

    public double getHargaSewa() {
        return hargaSewa;
    }

    public void setHargaSewa(double hargaSewa) {
        this.hargaSewa = hargaSewa;
    }

    public String getLamaPenyewaan() {
        return lamaPenyewaan;
    }

    public void setLamaPenyewaan(String lamaPenyewaan) {
        this.lamaPenyewaan = lamaPenyewaan;
    }

    public String getJumlahPenumpang() {
        return jumlahPenumpang;
    }

    public void setJumlahPenumpang(String jumlahPenumpang) {
        this.jumlahPenumpang = jumlahPenumpang;
    }

    public int getJumlahKendaraan() {
        return jumlahKendaraan;
    }

    public void setJumlahKendaraan(int jumlahKendaraan) {
        this.jumlahKendaraan = jumlahKendaraan;
    }

    public boolean isSupir() {
        return supir;
    }

    public void setSupir(boolean supir) {
        this.supir = supir;
    }

    public String getAreaPemakaian() {
        return areaPemakaian;
    }

    public void setAreaPemakaian(String areaPemakaian) {
        this.areaPemakaian = areaPemakaian;
    }

    public boolean isBahanBakar() {
        return bahanBakar;
    }

    public void setBahanBakar(boolean bahanBakar) {
        this.bahanBakar = bahanBakar;
    }

}
