package com.example.pe.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.pe.entities.DanhMuc;
import com.example.pe.entities.SanPham;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "sanpham.db";
    private static final int DATABASE_VERSION = 1;

    // Bảng sản phẩm
    private static final String TABLE_SANPHAM = "SanPham";
    private static final String COLUMN_MA = "Ma";
    private static final String COLUMN_MADANHMUC = "MaDanhMuc";
    private static final String COLUMN_TEN = "Ten";
    private static final String COLUMN_DONGIA = "DonGia";

    // Bảng danh mục
    private static final String TABLE_DANHMUC = "DanhMuc";
    private static final String COLUMN_MADANHMUC_DM = "MaDanhMuc";
    private static final String COLUMN_TENDANHMUC = "TenDanhMuc";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng DanhMuc
        String CREATE_DANHMUC_TABLE = "CREATE TABLE " + TABLE_DANHMUC + "("
                + COLUMN_MADANHMUC_DM + " TEXT PRIMARY KEY,"
                + COLUMN_TENDANHMUC + " TEXT NOT NULL"
                + ")";
        db.execSQL(CREATE_DANHMUC_TABLE);

        // Thêm danh mục mẫu
        addDanhMuc(db, new DanhMuc("DM001", "Danh mục 1"));
        addDanhMuc(db, new DanhMuc("DM002", "Danh mục 2"));
        addDanhMuc(db, new DanhMuc("DM003", "Danh mục 3"));
        addDanhMuc(db, new DanhMuc("DM004", "Danh mục 4"));
        addDanhMuc(db, new DanhMuc("DM005", "Danh mục 5"));

        // Tạo bảng SanPham
        String CREATE_SANPHAM_TABLE = "CREATE TABLE " + TABLE_SANPHAM + "("
                + COLUMN_MA + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_MADANHMUC + " TEXT,"
                + COLUMN_TEN + " TEXT NOT NULL,"
                + COLUMN_DONGIA + " FLOAT CHECK(" + COLUMN_DONGIA + " >= 0),"
                + "FOREIGN KEY (" + COLUMN_MADANHMUC + ") REFERENCES " + TABLE_DANHMUC + "(" + COLUMN_MADANHMUC_DM + ")"
                + ")";
        db.execSQL(CREATE_SANPHAM_TABLE);

        // Thêm sản phẩm mẫu
        addSanPham( new SanPham(1, "DM001", "Sản phẩm 1", 100.0f));
        addSanPham( new SanPham(2, "DM002", "Sản phẩm 2", 150.0f));
        addSanPham( new SanPham(3, "DM001", "Sản phẩm 3", 120.0f));
        addSanPham( new SanPham(4, "DM003", "Sản phẩm 4", 80.0f));
        addSanPham( new SanPham(5, "DM002", "Sản phẩm 5", 200.0f));

        Log.d("DatabaseHelper", "Database tables created successfully");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SANPHAM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DANHMUC);
        onCreate(db);
    }

    public void addSanPham(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MADANHMUC, sanPham.getMaDanhMuc());
        values.put(COLUMN_TEN, sanPham.getTen());
        values.put(COLUMN_DONGIA, sanPham.getDonGia());
        db.insert(TABLE_SANPHAM, null, values);
        db.close(); // Close the database connection
    }

    public void addDanhMuc(SQLiteDatabase db, DanhMuc danhMuc) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_MADANHMUC_DM, danhMuc.getMaDanhMuc());
        values.put(COLUMN_TENDANHMUC, danhMuc.getTenDanhMuc());
        db.insert(TABLE_DANHMUC, null, values);
    }

    public List<SanPham> getAllSanPham() {
        List<SanPham> sanPhamList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SANPHAM;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SanPham sanPham = new SanPham();
                sanPham.setMa(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA)));
                sanPham.setMaDanhMuc(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MADANHMUC)));
                sanPham.setTen(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN)));
                sanPham.setDonGia(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_DONGIA)));
                sanPhamList.add(sanPham);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return sanPhamList;
    }

    public List<SanPham> getSanPhamByDanhMuc(String maDanhMuc) {
        List<SanPham> sanPhamList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SANPHAM + " WHERE " + COLUMN_MADANHMUC + " = ?";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{maDanhMuc});

        if (cursor.moveToFirst()) {
            do {
                SanPham sanPham = new SanPham();
                sanPham.setMa(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_MA)));
                sanPham.setMaDanhMuc(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MADANHMUC)));
                sanPham.setTen(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEN)));
                sanPham.setDonGia(cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_DONGIA)));
                sanPhamList.add(sanPham);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return sanPhamList;
    }

    // Method to update a product
    public int updateSanPham(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TEN, sanPham.getTen());
        values.put(COLUMN_DONGIA, sanPham.getDonGia());

        // updating row
        int rowsAffected = db.update(TABLE_SANPHAM, values, COLUMN_MA + " = ?",
                new String[]{String.valueOf(sanPham.getMa())});

        db.close();
        return rowsAffected;
    }

    // Method to delete a product
    public void deleteSanPham(int maSanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SANPHAM, COLUMN_MA + " = ?",
                new String[]{String.valueOf(maSanPham)});
        db.close();
    }

}
