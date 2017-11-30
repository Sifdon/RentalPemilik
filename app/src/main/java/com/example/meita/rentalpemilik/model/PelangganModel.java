package com.example.meita.rentalpemilik.model;

/**
 * Created by meita on 09/06/2017.
 */

public class PelangganModel {
    private String idPelanggan;
    private String noIdentitas;
    private String namaLengkap;
    private String alamat;
    private String email;
    private String noTelp;
    private String createdAt;


        public PelangganModel() {

        }

        public PelangganModel(String idPelanggan, String noIdentitas, String namaLengkap, String alamat, String email, String noTelp, String createdAt) {
            this.idPelanggan = idPelanggan;
            this.noIdentitas = noIdentitas;
            this.namaLengkap = namaLengkap;
            this.alamat = alamat;
            this.email = email;
            this.noTelp = noTelp;
            this.createdAt = createdAt;
        }

        public String getNoIdentitas() {
            return noIdentitas;
        }

        public void setNoIdentitas(String noIdentitas) {
            this.noIdentitas = noIdentitas;
        }

        public String getNamaLengkap() {
            return namaLengkap;
        }

        public void setNamaLengkap(String namaLengkap) {
            this.namaLengkap = namaLengkap;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNoTelp() {
            return noTelp;
        }

        public void setNoTelp(String noTelp) {
            this.noTelp = noTelp;
        }

        public String getIdPelanggan() {
            return idPelanggan;
        }

        public void setIdPelanggan(String idPelanggan) {
            this.idPelanggan = idPelanggan;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }

