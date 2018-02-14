package com.example.meita.rentalpemilik.model;

/**
 * Created by meita on 17/06/2017.
 */

public class RentalModel {
    public String uriFotoProfil, deviceToken;
    public String idRental, nama_pemilik, nama_rental, alamat_rental, notelfon_rental, kebijakanPembatalan,
    kebijakanPemakaian, kebijakanKelebihanWaktu, namaBank, namaPemilikBank, nomorRekeningBank, idRekening;
    public double latitude, longitude;
    String emailRental;

    //kebijakan pembatalan, pemakaian, kelebihan waktu.

    public RentalModel() {

    }


    public RentalModel(String idRental, String uriFotoProfil, String nama_pemilik, String nama_rental, String alamat_rental, String notelfon_rental,
                       String kebijakanPembatalan, String kebijakanPemakaian, String kebijakanKelebihanWaktu,
                       double latitude, double longitude, String deviceToken, String emailRental) {
        this.idRental = idRental;
        this.uriFotoProfil = uriFotoProfil;
        this.nama_pemilik = nama_pemilik;
        this.nama_rental = nama_rental;
        this.alamat_rental = alamat_rental;
        this.notelfon_rental = notelfon_rental;
        this.kebijakanPembatalan = kebijakanPembatalan;
        this.kebijakanPemakaian = kebijakanPemakaian;
        this.kebijakanKelebihanWaktu = kebijakanKelebihanWaktu;
        this.latitude = latitude;
        this.longitude = longitude;
        this.deviceToken = deviceToken;
        this.emailRental = emailRental;
    }

    public RentalModel(String idRekening, String namaPemilikBank, String nomorRekeningBank, String namaBank) {
        this.idRekening = idRekening;
        this.namaBank = namaBank;
        this.namaPemilikBank = namaPemilikBank;
        this.nomorRekeningBank = nomorRekeningBank;
    }

    public RentalModel(String uriFotoProfil, String nama_pemilik, String nama_rental, String alamat_rental, String notelfon_rental, String kebijakanPembatalan, String kebijakanPemakaian, String kebijakanKelebihanWaktu) {
        this.uriFotoProfil = uriFotoProfil;
        this.nama_pemilik = nama_pemilik;
        this.nama_rental = nama_rental;
        this.alamat_rental = alamat_rental;
        this.notelfon_rental = notelfon_rental;
        this.kebijakanPembatalan = kebijakanPembatalan;
        this.kebijakanPemakaian = kebijakanPemakaian;
        this.kebijakanKelebihanWaktu = kebijakanKelebihanWaktu;
    }

    public String getIdRental() {
        return idRental;
    }

    public void setIdRental(String idRental) {
        this.idRental = idRental;
    }

    public String getUriFotoProfil() {
        return uriFotoProfil;
    }

    public void setUriFotoProfil(String uriFotoProfil) {
        this.uriFotoProfil = uriFotoProfil;
    }

    public String getNama_pemilik() {
        return nama_pemilik;
    }

    public void setNama_pemilik(String nama_pemilik) {
        this.nama_pemilik = nama_pemilik;
    }

    public String getNama_rental() {
        return nama_rental;
    }

    public void setNama_rental(String nama_rental) {
        this.nama_rental = nama_rental;
    }

    public String getAlamat_rental() {
        return alamat_rental;
    }

    public void setAlamat_rental(String alamat_rental) {
        this.alamat_rental = alamat_rental;
    }

    public String getNotelfon_rental() {
        return notelfon_rental;
    }

    public void setNotelfon_rental(String notelfon_rental) {
        this.notelfon_rental = notelfon_rental;
    }

    public String getkebijakanPembatalan() {
        return kebijakanPembatalan;
    }

    public void setkebijakanPembatalan(String kebijakanPembatalan) {
        this.kebijakanPembatalan = kebijakanPembatalan;
    }

    public String getkebijakanPemakaian() {
        return kebijakanPemakaian;
    }

    public void setkebijakanPemakaian(String kebijakanPemakaian) {
        this.kebijakanPemakaian = kebijakanPemakaian;
    }

    public String getkebijakanKelebihanWaktu() {
        return kebijakanKelebihanWaktu;
    }

    public void setkebijakanKelebihanWaktu(String kebijakanKelebihanWaktu) {
        this.kebijakanKelebihanWaktu = kebijakanKelebihanWaktu;
    }

    public String getNamaBank() {
        return namaBank;
    }

    public void setNamaBank(String namaBank) {
        this.namaBank = namaBank;
    }

    public String getNamaPemilikBank() {
        return namaPemilikBank;
    }

    public void setNamaPemilikBank(String namaPemilikBank) {
        this.namaPemilikBank = namaPemilikBank;
    }

    public String getNomorRekeningBank() {
        return nomorRekeningBank;
    }

    public void setNomorRekeningBank(String nomorRekeningBank) {
        this.nomorRekeningBank = nomorRekeningBank;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getIdRekening() {
        return idRekening;
    }

    public void setIdRekening(String idRekening) {
        this.idRekening = idRekening;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getEmailRental() {
        return emailRental;
    }
}
