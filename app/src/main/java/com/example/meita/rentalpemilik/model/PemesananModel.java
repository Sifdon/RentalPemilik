package com.example.meita.rentalpemilik.model;

import java.io.Serializable;

/**
 * Created by meita on 17/09/2017.
 */

public class PemesananModel implements Serializable {
    public String idPemesanan, idKendaraan, idPelanggan, idRental, statusPemesanan, tglPembuatanPesanan, tglSewa, tglKembali,
            keteranganKhusus, jamPenjemputan, alamatPenjemputan;
    public int jumlahKendaraan, jumlahHariPenyewaan, totalBiayaPembayaran;
    public double latitude_penjemputan, longitude_penjemputan;
    public String jamPengambilan, batasWaktuPembayaran, kategoriKendaraan, idRekeningRental;
    String alasanPembatalan;
    String namaBankRental, namaRekeningRental, nomorRekeningRental, jumlahTransferPengembalian, uriBuktiPengembalian;
    String idPembayaran, uriFotoBuktiPembayaran,
            bankPelanggan, namaPemilikRekeningPelanggan, nomorRekeningPelanggan, jumlahTransfer, waktuPembayaran;


    public PemesananModel() {

    }

    public PemesananModel(String idPemesanan, String idKendaraan, String idPelanggan, String idRental, String statusPemesanan,
                          String tglPembuatanPesanan, String tglSewa, String tglKembali, String keteranganKhusus, String jamPengambilan,
                          int jumlahKendaraan, int jumlahHariPenyewaan, int totalBiayaPembayaran, String batasWaktuPembayaran, String kategoriKendaraan, String idRekeningRental) {
        this.idPemesanan = idPemesanan;
        this.idKendaraan = idKendaraan;
        this.idPelanggan = idPelanggan;
        this.idRental = idRental;
        this.statusPemesanan = statusPemesanan;
        this.tglPembuatanPesanan = tglPembuatanPesanan;
        this.tglSewa = tglSewa;
        this.tglKembali = tglKembali;
        this.keteranganKhusus = keteranganKhusus;
        this.jamPengambilan = jamPengambilan;
        this.jumlahKendaraan = jumlahKendaraan;
        this.jumlahHariPenyewaan = jumlahHariPenyewaan;
        this.totalBiayaPembayaran = totalBiayaPembayaran;
        this.batasWaktuPembayaran = batasWaktuPembayaran;
        this.kategoriKendaraan = kategoriKendaraan;
        this.idRekeningRental = idRekeningRental;
    }

    public PemesananModel(String idPemesanan, String idKendaraan, String idPelanggan, String idRental, String statusPemesanan,
                          String tglPembuatanPesanan, String tglSewa, String tglKembali, String keteranganKhusus, String jamPenjemputan,
                          int jumlahKendaraan, double latitude_penjemputan, double longitude_penjemputan, String alamatPenjemputan,
                          int jumlahHariPenyewaan, int totalBiayaPembayaran, String batasWaktuPembayaran, String kategoriKendaraan, String idRekeningRental) {
        this.idPemesanan = idPemesanan;
        this.idKendaraan = idKendaraan;
        this.idPelanggan = idPelanggan;
        this.idRental = idRental;
        this.statusPemesanan = statusPemesanan;
        this.tglPembuatanPesanan = tglPembuatanPesanan;
        this.tglSewa = tglSewa;
        this.tglKembali = tglKembali;
        this.keteranganKhusus = keteranganKhusus;
        this.jamPenjemputan = jamPenjemputan;
        this.jumlahKendaraan = jumlahKendaraan;
        this.latitude_penjemputan = latitude_penjemputan;
        this.longitude_penjemputan = longitude_penjemputan;
        this.alamatPenjemputan = alamatPenjemputan;
        this.jumlahHariPenyewaan = jumlahHariPenyewaan;
        this.totalBiayaPembayaran = totalBiayaPembayaran;
        this.tglPembuatanPesanan = tglPembuatanPesanan;
        this.batasWaktuPembayaran = batasWaktuPembayaran;
        this.kategoriKendaraan = kategoriKendaraan;
        this.idRekeningRental = idRekeningRental;
    }

    public PemesananModel(String idPemesanan, String idKendaraan, String idRental, String statusPemesanan, String tglPembuatanPesanan, String tglSewa,
                          String tglKembali, int jumlahKendaraan, String kategoriKendaraan) {
        this.idPemesanan = idPemesanan;
        this.idKendaraan = idKendaraan;
        this.idRental = idRental;
        this.statusPemesanan = statusPemesanan;
        this.tglPembuatanPesanan = tglPembuatanPesanan;
        this.tglSewa = tglSewa;
        this.tglKembali = tglKembali;
        this.jumlahKendaraan = jumlahKendaraan;
        this.kategoriKendaraan = kategoriKendaraan;
    }

    public String getIdPemesanan() {
        return idPemesanan;
    }

    public void setIdPemesanan(String idPemesanan) {
        this.idPemesanan = idPemesanan;
    }

    public String getIdKendaraan() {
        return idKendaraan;
    }

    public void setIdKendaraan(String idKendaraan) {
        this.idKendaraan = idKendaraan;
    }

    public String getIdPelanggan() {
        return idPelanggan;
    }

    public void setIdPelanggan(String idPelanggan) {
        this.idPelanggan = idPelanggan;
    }

    public String getIdRental() {
        return idRental;
    }

    public void setIdRental(String idRental) {
        this.idRental = idRental;
    }

    public String getStatusPemesanan() {
        return statusPemesanan;
    }

    public void setStatusPemesanan(String statusPemesanan) {
        this.statusPemesanan = statusPemesanan;
    }

    public String getTglSewa() {
        return tglSewa;
    }

    public void setTglSewa(String tglSewa) {
        this.tglSewa = tglSewa;
    }

    public String getTglKembali() {
        return tglKembali;
    }

    public void setTglKembali(String tglKembali) {
        this.tglKembali = tglKembali;
    }


    public int getJumlahKendaraan() {
        return jumlahKendaraan;
    }

    public void setJumlahKendaraan(int jumlahKendaraan) {
        this.jumlahKendaraan = jumlahKendaraan;
    }

    public String getKeteranganKhusus() {
        return keteranganKhusus;
    }

    public void setKeteranganKhusus(String keteranganKhusus) {
        this.keteranganKhusus = keteranganKhusus;
    }

    public String getJamPenjemputan() {
        return jamPenjemputan;
    }

    public void setJamPenjemputan(String jamPenjemputan) {
        this.jamPenjemputan = jamPenjemputan;
    }

    public String getAlamatPenjemputan() {
        return alamatPenjemputan;
    }

    public void setAlamatPenjemputan(String alamatPenjemputan) {
        this.alamatPenjemputan = alamatPenjemputan;
    }

    public int getJumlahHariPenyewaan() {
        return jumlahHariPenyewaan;
    }

    public void setJumlahHariPenyewaan(int jumlahHariPenyewaan) {
        this.jumlahHariPenyewaan = jumlahHariPenyewaan;
    }

    public int getTotalBiayaPembayaran() {
        return totalBiayaPembayaran;
    }

    public void setTotalBiayaPembayaran(int totalBiayaPembayaran) {
        this.totalBiayaPembayaran = totalBiayaPembayaran;
    }

    public double getLatitude_penjemputan() {
        return latitude_penjemputan;
    }

    public void setLatitude_penjemputan(double latitude_penjemputan) {
        this.latitude_penjemputan = latitude_penjemputan;
    }

    public double getLongitude_penjemputan() {
        return longitude_penjemputan;
    }

    public void setLongitude_penjemputan(double longitude_penjemputan) {
        this.longitude_penjemputan = longitude_penjemputan;
    }

    public String getJamPengambilan() {
        return jamPengambilan;
    }

    public void setJamPengambilan(String jamPengambilan) {
        this.jamPengambilan = jamPengambilan;
    }

    public String getTglPembuatanPesanan() {
        return tglPembuatanPesanan;
    }

    public void setTglPembuatanPesanan(String tglPembuatanPesanan) {
        this.tglPembuatanPesanan = tglPembuatanPesanan;
    }

    public String getBatasWaktuPembayaran() {
        return batasWaktuPembayaran;
    }

    public void setBatasWaktuPembayaran(String batasWaktuPembayaran) {
        this.batasWaktuPembayaran = batasWaktuPembayaran;
    }

    public String getKategoriKendaraan() {
        return kategoriKendaraan;
    }

    public void setKategoriKendaraan(String kategoriKendaraan) {
        this.kategoriKendaraan = kategoriKendaraan;
    }

    public String getIdRekeningRental() {
        return idRekeningRental;
    }

    public void setIdRekeningRental(String idRekeningRental) {
        this.idRekeningRental = idRekeningRental;
    }

    public String getAlasanPembatalan() {
        return alasanPembatalan;
    }

    public void setAlasanPembatalan(String alasanPembatalan) {
        this.alasanPembatalan = alasanPembatalan;
    }

    public String getNamaBankRental() {
        return namaBankRental;
    }

    public void setNamaBankRental(String namaBankRental) {
        this.namaBankRental = namaBankRental;
    }

    public String getNamaRekeningRental() {
        return namaRekeningRental;
    }

    public void setNamaRekeningRental(String namaRekeningRental) {
        this.namaRekeningRental = namaRekeningRental;
    }

    public String getNomorRekeningRental() {
        return nomorRekeningRental;
    }

    public void setNomorRekeningRental(String nomorRekeningRental) {
        this.nomorRekeningRental = nomorRekeningRental;
    }

    public String getJumlahTransferPengembalian() {
        return jumlahTransferPengembalian;
    }

    public void setJumlahTransferPengembalian(String jumlahTransferPengembalian) {
        this.jumlahTransferPengembalian = jumlahTransferPengembalian;
    }

    public String getUriBuktiPengembalian() {
        return uriBuktiPengembalian;
    }

    public void setUriBuktiPengembalian(String uriBuktiPengembalian) {
        this.uriBuktiPengembalian = uriBuktiPengembalian;
    }

    public String getIdPembayaran() {
        return idPembayaran;
    }

    public void setIdPembayaran(String idPembayaran) {
        this.idPembayaran = idPembayaran;
    }

    public String getUriFotoBuktiPembayaran() {
        return uriFotoBuktiPembayaran;
    }

    public void setUriFotoBuktiPembayaran(String uriFotoBuktiPembayaran) {
        this.uriFotoBuktiPembayaran = uriFotoBuktiPembayaran;
    }

    public String getBankPelanggan() {
        return bankPelanggan;
    }

    public void setBankPelanggan(String bankPelanggan) {
        this.bankPelanggan = bankPelanggan;
    }

    public String getNamaPemilikRekeningPelanggan() {
        return namaPemilikRekeningPelanggan;
    }

    public void setNamaPemilikRekeningPelanggan(String namaPemilikRekeningPelanggan) {
        this.namaPemilikRekeningPelanggan = namaPemilikRekeningPelanggan;
    }

    public String getNomorRekeningPelanggan() {
        return nomorRekeningPelanggan;
    }

    public void setNomorRekeningPelanggan(String nomorRekeningPelanggan) {
        this.nomorRekeningPelanggan = nomorRekeningPelanggan;
    }

    public String getJumlahTransfer() {
        return jumlahTransfer;
    }

    public void setJumlahTransfer(String jumlahTransfer) {
        this.jumlahTransfer = jumlahTransfer;
    }

    public String getWaktuPembayaran() {
        return waktuPembayaran;
    }

    public void setWaktuPembayaran(String waktuPembayaran) {
        this.waktuPembayaran = waktuPembayaran;
    }
}

