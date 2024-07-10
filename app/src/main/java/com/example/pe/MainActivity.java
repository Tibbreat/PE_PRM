package com.example.pe;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Button btnDanhSachSanPham, btnDanhSachSanPhamTheoDM, btnTimKiemSanPham, btnLienHe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnDanhSachSanPham = findViewById(R.id.btnDanhSachSanPham);
        btnDanhSachSanPhamTheoDM = findViewById(R.id.btnDanhSachSanPhamTheoDM);
        btnTimKiemSanPham = findViewById(R.id.btnTimKiemSanPham);
        btnLienHe = findViewById(R.id.btnLienHe);

        btnDanhSachSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DanhSachSanPhamActivity.class);
                startActivity(intent);
            }
        });

        btnDanhSachSanPhamTheoDM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TimKiemSanPhamTheoDMActivity.class);
                startActivity(intent);
            }
        });

        btnTimKiemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Chức năng đang xây dựng", Toast.LENGTH_SHORT).show();
            }
        });

        btnLienHe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Hiển thị thông tin liên hệ
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Thông tin liên hệ");
                builder.setMessage("Chủ cửa hàng: Nguyễn Văn A\nSố điện thoại: 0123456789");
                builder.setPositiveButton("OK", null);
                builder.show();
            }
        });
    }
}