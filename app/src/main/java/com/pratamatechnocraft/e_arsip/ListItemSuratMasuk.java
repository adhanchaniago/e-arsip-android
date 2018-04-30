package com.pratamatechnocraft.e_arsip;

public class ListItemSuratMasuk {
    private String asalSurat,perihalSurat, tanggalArsip;

    public ListItemSuratMasuk(String asalSurat, String perihalSurat, String tanggalArsip) {
        this.asalSurat = asalSurat;
        this.perihalSurat = perihalSurat;
        this.tanggalArsip = tanggalArsip;
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
