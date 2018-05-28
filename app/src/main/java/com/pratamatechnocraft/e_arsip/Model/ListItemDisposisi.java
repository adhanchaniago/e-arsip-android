package com.pratamatechnocraft.e_arsip.Model;

public class ListItemDisposisi {
    private String noSurat,isiDisposisi;

    public ListItemDisposisi(String noSurat, String isiDisposisi) {
        this.noSurat = noSurat;
        this.isiDisposisi = isiDisposisi;
    }

    public String getNoSurat() {
        return noSurat;
    }

    public String getIsiDisposisi() {
        return isiDisposisi;
    }
}
