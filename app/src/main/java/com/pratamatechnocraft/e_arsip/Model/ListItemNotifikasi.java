package com.pratamatechnocraft.e_arsip.Model;

public class ListItemNotifikasi {
    private String idNotif,id,jenisNotif,judulNotif,isiNotfi,tglNotif;

    public ListItemNotifikasi(String idNotif, String id, String jenisNotif, String judulNotif, String isiNotfi, String tglNotif) {
        this.idNotif = idNotif;
        this.id = id;
        this.jenisNotif = jenisNotif;
        this.judulNotif = judulNotif;
        this.isiNotfi = isiNotfi;
        this.tglNotif = tglNotif;
    }

    public String getIdNotif() {
        return idNotif;
    }

    public String getId() {
        return id;
    }

    public String getJenisNotif() {
        return jenisNotif;
    }

    public String getJudulNotif() {
        return judulNotif;
    }

    public String getIsiNotfi() {
        return isiNotfi;
    }

    public String getTglNotif() {
        return tglNotif;
    }
}
