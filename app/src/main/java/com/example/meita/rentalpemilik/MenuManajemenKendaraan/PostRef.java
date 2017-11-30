package com.example.meita.rentalpemilik.MenuManajemenKendaraan;


import java.io.Serializable;

public class PostRef implements Serializable {
    private String idKendaraan;
    private String kategoriKendaraan;
    private String idRental;

    public PostRef() {}

    public PostRef(String idKendaraan, String kategoriKendaraan, String idRental) {
        this.idKendaraan = idKendaraan;
        this.kategoriKendaraan = kategoriKendaraan;
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

    public String getIdRental() {
        return idRental;
    }

    public void setIdRental(String idRental) {
        this.idRental = idRental;
    }
}