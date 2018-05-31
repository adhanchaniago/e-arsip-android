package com.pratamatechnocraft.e_arsip.Model;

public class ListItemDisposisi {
    private String idDisposisi,noSurat,isiDisposisi;

    public ListItemDisposisi(String idDisposisi, String noSurat, String isiDisposisi) {
        this.idDisposisi = idDisposisi;
        this.noSurat = noSurat;
        this.isiDisposisi = isiDisposisi;
    }

    public String getidDisposisi() {
        return idDisposisi;
    }

    public String getNoSurat() {
        return noSurat;
    }

    public String getIsiDisposisi() {
        return isiDisposisi;
    }
}
