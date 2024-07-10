package com.example.pe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.pe.adapter.SanPhamAdapter;
import com.example.pe.entities.SanPham;
import com.example.pe.utils.DatabaseHelper;

import java.util.List;

public class TimKiemSanPhamTheoDMActivity extends AppCompatActivity {
    private EditText edtDanhMuc;
    private Button btnXemSanPham;
    private ListView listView;
    private DatabaseHelper dbHelper;
    private List<SanPham> sanPhamList;
    private SanPhamAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem_san_pham_theo_dmactivity);

        edtDanhMuc = findViewById(R.id.edtDanhMuc);
        btnXemSanPham = findViewById(R.id.btnXemSanPham);
        listView = findViewById(R.id.listView);
        dbHelper = new DatabaseHelper(this);

        btnXemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String maDanhMuc = edtDanhMuc.getText().toString();
                if (maDanhMuc.isEmpty()) {
                    Toast.makeText(TimKiemSanPhamTheoDMActivity.this, "Nhập từ khóa cần tìm", Toast.LENGTH_SHORT).show();
                } else {
                    sanPhamList = dbHelper.getSanPhamByDanhMuc(maDanhMuc);
                    if (sanPhamList.isEmpty()) {
                        Toast.makeText(TimKiemSanPhamTheoDMActivity.this, "Không tìm thấy mặt hàng nào", Toast.LENGTH_SHORT).show();
                    } else {
                        adapter = new SanPhamAdapter(TimKiemSanPhamTheoDMActivity.this, R.layout.sanpham_item, sanPhamList);
                        listView.setAdapter(adapter);
                    }
                }
            }
        });
    }
}
