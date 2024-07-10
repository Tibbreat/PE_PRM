package com.example.pe.entities;

import java.io.Serializable;

public class SanPham implements Serializable {
    private int ma;
    private String maDanhMuc;
    private String ten;
    private float donGia;

    public SanPham() {
    }

    public SanPham(int ma, String maDanhMuc, String ten, float donGia) {
        this.ma = ma;
        this.maDanhMuc = maDanhMuc;
        this.ten = ten;
        this.donGia = donGia;
    }

    public int getMa() {
        return ma;
    }

    public void setMa(int ma) {
        this.ma = ma;
    }

    public String getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(String maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public float getDonGia() {
        return donGia;
    }

    public void setDonGia(float donGia) {
        this.donGia = donGia;
    }
}

