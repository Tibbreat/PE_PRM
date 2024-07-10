package com.example.pe;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pe.adapter.SanPhamAdapter;
import com.example.pe.entities.SanPham;
import com.example.pe.utils.DatabaseHelper;

import java.util.List;

public class DanhSachSanPhamActivity extends AppCompatActivity {
    private ListView listView;
    private DatabaseHelper dbHelper;
    private List<SanPham> sanPhamList;
    private SanPhamAdapter adapter;
    private static final int REQUEST_UPDATE_DELETE = 1; // Khai báo một mã request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach_san_pham);

        listView = findViewById(R.id.listView);
        dbHelper = new DatabaseHelper(this);
        loadSanPhamList(); // Tải danh sách sản phẩm ban đầu

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SanPham sanPham = sanPhamList.get(position);
                Intent intent = new Intent(DanhSachSanPhamActivity.this, ChiTietSanPhamActivity.class);
                intent.putExtra("sanPham", sanPham);
                startActivityForResult(intent, REQUEST_UPDATE_DELETE); // Sử dụng startActivityForResult để chuyển sang ChiTietSanPhamActivity
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSanPhamList(); // Tải lại danh sách sản phẩm khi quay lại activity
    }

    private void loadSanPhamList() {
        sanPhamList = dbHelper.getAllSanPham(); // Lấy danh sách sản phẩm từ DatabaseHelper
        adapter = new SanPhamAdapter(this, R.layout.sanpham_item, sanPhamList);
        listView.setAdapter(adapter); // Cập nhật adapter cho listView
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_UPDATE_DELETE && resultCode == RESULT_OK) {
            loadSanPhamList(); // Nếu có kết quả trả về từ ChiTietSanPhamActivity, tải lại danh sách sản phẩm
        }
    }
}
