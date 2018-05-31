package com.pratamatechnocraft.e_arsip.Model;

public class ListItemSuratMasuk {
    private String idSuratMasuk,asalSurat,perihalSurat, tanggalArsip;

    public ListItemSuratMasuk(String idSuratMasuk, String asalSurat, String perihalSurat, String tanggalArsip) {
        this.idSuratMasuk = idSuratMasuk;
        this.asalSurat = asalSurat;
        this.perihalSurat = perihalSurat;
        this.tanggalArsip = tanggalArsip;
    }

    public String getIdSuratMasuk() {
        return idSuratMasuk;
    }

    public String getAsalSurat() {
        return asalSurat;
    }

    public String getPerihalSurat() {
        return perihalSurat;
    }

    public String getTanggalArsip() {
        return tanggalArsip;
    }
}
