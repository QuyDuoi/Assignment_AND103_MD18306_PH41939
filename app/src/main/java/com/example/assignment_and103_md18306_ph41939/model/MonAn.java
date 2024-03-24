package com.example.assignment_and103_md18306_ph41939.model;

import android.content.Context;
import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.FileOutputStream;

public class MonAn {
    @SerializedName("_id")
    private String _id;
    private String tenMon, moTa, loaiMon;
    private int trangThai;
    private double giaMon;
    private byte[] anhMon;

    public MonAn() {
    }

    public MonAn(String _id, String tenMon, String moTa, String loaiMon, int trangThai, double giaMon) {
        this._id = _id;
        this.tenMon = tenMon;
        this.moTa = moTa;
        this.loaiMon = loaiMon;
        this.trangThai = trangThai;
        this.giaMon = giaMon;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenMon() {
        return tenMon;
    }

    public void setTenMon(String tenMon) {
        this.tenMon = tenMon;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getLoaiMon() {
        return loaiMon;
    }

    public void setLoaiMon(String loaiMon) {
        this.loaiMon = loaiMon;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public double getGiaMon() {
        return giaMon;
    }

    public void setGiaMon(double giaMon) {
        this.giaMon = giaMon;
    }

    public byte[] getAnhMon() {
        return anhMon;
    }

    public void setAnhMon(byte[] anhMon) {
        this.anhMon = anhMon;
    }

//    public Uri hienthi(Context context) {
//        byte[] imageData = getAnhMon();// Mảng byte chứa dữ liệu hình ảnh
//        String tempFileName = "temp_image.jpg";
//        Uri uri;
//        // Tạo đường dẫn tới tập tin ảnh tạm
//        File tempFile = new File(context.getCacheDir(), tempFileName);
//        // Ghi dữ liệu blob vào tập tin ảnh tạm
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
//            fileOutputStream.write(imageData);
//            fileOutputStream.close();
//            uri = Uri.fromFile(tempFile);
//            return uri;
//        } catch (Exception e) {
//            return null;
//        }
//    }
}
