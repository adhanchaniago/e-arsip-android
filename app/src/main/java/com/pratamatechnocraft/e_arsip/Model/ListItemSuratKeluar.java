package com.pratamatechnocraft.e_arsip.Model;

public class ListItemSuratKeluar {
    private String IdSuratKeluar,tujuanSurat,perihalSurat, tanggalArsip;

    public ListItemSuratKeluar(String IdSuratKeluar, String tujuanSurat, String perihalSurat, String tanggalArsip) {
        this.IdSuratKeluar = IdSuratKeluar;
        this.tujuanSurat = tujuanSurat;
        this.perihalSurat = perihalSurat;
        this.tanggalArsip = tanggalArsip;
    }

    public String getIdSuratKeluar() {
        return IdSuratKeluar;
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
