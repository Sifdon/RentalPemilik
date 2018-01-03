package com.example.meita.rentalpemilik.MenuPenilaianDanUlasan;

import java.io.Serializable;

/**
 * Created by meita on 11/7/2017.
 */

public class UlasanModel implements Serializable{
    public String idKategori, idPelanggan, idRental, idUlasan, idPemesanan,
            idKendaraan, ulasan;
    public float ratingKendaraan, ratingPelayanan;
    public String waktuUlasan;

    public UlasanModel(){}

    public UlasanModel(String idKategori, String idPelanggan, String idRental, String idUlasan, String idPemesanan,
                       String idKendaraan, String ulasan, float ratingKendaraan, float ratingPelayanan, String waktuUlasan) {
        this.idKategori = idKategori;
        this.idPelanggan = idPelanggan;
        this.idRental = idRental;
        this.idUlasan = idUlasan;
        this.idPemesanan = idPemesanan;
        this.idKendaraan = idKendaraan;
        this.ulasan = ulasan;
        this.ratingKendaraan = ratingKendaraan;
        this.ratingPelayanan = ratingPelayanan;
        this.waktuUlasan = waktuUlasan;
    }

    public String getIdKategori() {
        return idKategori;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public String getIdRental() {
        return idRental;
    }

    public String getIdUlasan() {
        return idUlasan;
    }

    public String getIdPemesanan() {
        return idPemesanan;
    }

    public String getUlasan() {
        return ulasan;
    }

    public float getRatingKendaraan() {
        return ratingKendaraan;
    }

    public float getRatingPelayanan() {
        return ratingPelayanan;
    }

    public String getWaktuUlasan() {
        return waktuUlasan;
    }

    public String getIdKendaraan() {
        return idKendaraan;
    }
}