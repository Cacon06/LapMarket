package com.pma101.lapmarket.models;

public class Khachhang {
    private String _id;
    private String anhDD,tenKH,tuoiKH,diachi,gioitinh;

    public Khachhang() {
    }

    public Khachhang(String _id, String anhDD, String tenKH, String tuoiKH, String diachi, String gioitinh) {
        this._id = _id;
        this.anhDD = anhDD;
        this.tenKH = tenKH;
        this.tuoiKH = tuoiKH;
        this.diachi = diachi;
        this.gioitinh = gioitinh;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getAnhDD() {
        return anhDD;
    }

    public void setAnhDD(String anhDD) {
        this.anhDD = anhDD;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getTuoiKH() {
        return tuoiKH;
    }

    public void setTuoiKH(String tuoiKH) {
        this.tuoiKH = tuoiKH;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        this.gioitinh = gioitinh;
    }
}
