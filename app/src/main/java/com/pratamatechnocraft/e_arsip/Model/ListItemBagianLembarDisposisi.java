package com.pratamatechnocraft.e_arsip.Model;

public class ListItemBagianLembarDisposisi {
    private String idBagian, namaBagian, idBagianTmp;

    public ListItemBagianLembarDisposisi(String idBagian, String namaBagian, String idBagianTmp) {
        this.idBagian = idBagian;
        this.namaBagian = namaBagian;
        this.idBagianTmp = idBagianTmp;
    }

    public String getIdBagian() {
        return idBagian;
    }

    public String getNamaBagian() {
        return namaBagian;
    }

    public String getIdBagianTmp() {
        return idBagianTmp;
    }
}
