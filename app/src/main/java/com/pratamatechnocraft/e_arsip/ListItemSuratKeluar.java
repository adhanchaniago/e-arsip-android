package com.pratamatechnocraft.e_arsip;

public class ListItemSuratKeluar {
    private String tujuanSurat,perihalSurat, tanggalArsip;

    public ListItemSuratKeluar(String asalSurat, String perihalSurat, String tanggalArsip) {
        this.tujuanSurat = asalSurat;
        this.perihalSurat = perihalSurat;
        this.tanggalArsip = tanggalArsip;
    }

    public String getTujuanSurat() {
        return tujuanSurat;
    }

    public String getPerihalSurat() {
        return perihalSurat;
    }

    public String getTanggalArsip() {
        return tanggalArsip;
    }
}
