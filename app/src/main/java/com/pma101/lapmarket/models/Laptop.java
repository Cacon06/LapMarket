package com.pma101.lapmarket.models;

import com.google.android.gms.common.api.Result;

public class Laptop{
    private String _id;
    private String hinhanh,ten,gia,thuonghieu,xuatxu;

    public Laptop() {
    }

    public Laptop(String _id, String hinhanh, String ten, String gia, String thuonghieu, String xuatxu) {
        this._id = _id;
        this.hinhanh = hinhanh;
        this.ten = ten;
        this.gia = gia;
        this.thuonghieu = thuonghieu;
        this.xuatxu = xuatxu;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public String getThuonghieu() {
        return thuonghieu;
    }

    public void setThuonghieu(String thuonghieu) {
        this.thuonghieu = thuonghieu;
    }

    public String getXuatxu() {
        return xuatxu;
    }

    public void setXuatxu(String xuatxu) {
        this.xuatxu = xuatxu;
    }
}
