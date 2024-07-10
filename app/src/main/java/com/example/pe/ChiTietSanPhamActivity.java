package com.example.pe;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pe.entities.SanPham;
import com.example.pe.utils.DatabaseHelper;

public class ChiTietSanPhamActivity extends AppCompatActivity {
    private EditText edtTen, edtDonGia, edtMaDM, edtMa;
    private Button btnUpdate, btnDelete, btnInsert;
    private SanPham sanPham;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);

        edtTen = findViewById(R.id.tvTen);
        edtDonGia = findViewById(R.id.tvDonGia);
        edtMaDM = findViewById(R.id.tvMaDanhMuc);
        edtMa = findViewById(R.id.tvMa);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnInsert = findViewById(R.id.btnInsert);

        dbHelper = new DatabaseHelper(this);

        // Lấy dữ liệu sản phẩm từ intent
        sanPham = (SanPham) getIntent().getSerializableExtra("sanPham");

        if (sanPham != null) {
            // Nếu sanPham khác null, hiển thị thông tin sản phẩm và nút Update, Delete
            edtTen.setText(sanPham.getTen());
            edtDonGia.setText(String.valueOf(sanPham.getDonGia()));
            edtMa.setText(String.valueOf(sanPham.getMa()));
            edtMaDM.setText(String.valueOf(sanPham.getMaDanhMuc()));
            btnUpdate.setVisibility(View.VISIBLE);
            btnDelete.setVisibility(View.VISIBLE);
            btnInsert.setVisibility(View.VISIBLE); // Ẩn nút Insert
        } else {
            // Nếu sanPham là null, hiển thị form trống để thêm mới sản phẩm và nút Insert
            btnUpdate.setVisibility(View.GONE);
            btnDelete.setVisibility(View.GONE);
            btnInsert.setVisibility(View.VISIBLE);
        }

        // Xử lý sự kiện click nút Update
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSanPham();
            }
        });

        // Xử lý sự kiện click nút Delete
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDelete();
            }
        });

        // Xử lý sự kiện click nút Insert
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertSanPham();
            }
        });
    }

    // Phương thức cập nhật thông tin sản phẩm
    private void updateSanPham() {
        String tenMoi = edtTen.getText().toString();
        float donGiaMoi = Float.parseFloat(edtDonGia.getText().toString());

        // Kiểm tra xem dữ liệu có thay đổi so với ban đầu không
        if (tenMoi.equals(sanPham.getTen()) && donGiaMoi == sanPham.getDonGia()) {
            Toast.makeText(ChiTietSanPhamActivity.this, "Dữ liệu không có thay đổi", Toast.LENGTH_SHORT).show();
        } else if (tenMoi.isEmpty() || edtDonGia.getText().toString().isEmpty()) {
            Toast.makeText(ChiTietSanPhamActivity.this, "Nhập đầy đủ thông tin để sửa", Toast.LENGTH_SHORT).show();
        } else {
            // Cập nhật dữ liệu mới vào đối tượng sanPham
            sanPham.setTen(tenMoi);
            sanPham.setDonGia(donGiaMoi);

            // Gọi phương thức updateSanPham từ dbHelper để cập nhật vào cơ sở dữ liệu
            int rowsAffected = dbHelper.updateSanPham(sanPham);
            if (rowsAffected > 0) {
                Toast.makeText(ChiTietSanPhamActivity.this, "Đã sửa thành công", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ChiTietSanPhamActivity.this, "Lỗi khi sửa sản phẩm", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Phương thức xác nhận xóa sản phẩm
    private void confirmDelete() {
        new AlertDialog.Builder(ChiTietSanPhamActivity.this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Gọi phương thức deleteSanPham từ dbHelper để xóa sản phẩm
                        dbHelper.deleteSanPham(sanPham.getMa());
                        Toast.makeText(ChiTietSanPhamActivity.this, "Đã xóa sản phẩm", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton("NO", null)
                .show();
    }

    // Phương thức thêm mới sản phẩm
    private void insertSanPham() {
        String tenMoi = edtTen.getText().toString();
        String donGiaMoiStr = edtDonGia.getText().toString();

        // Kiểm tra xem các trường dữ liệu có rỗng không
        if (tenMoi.isEmpty() || donGiaMoiStr.isEmpty()) {
            Toast.makeText(ChiTietSanPhamActivity.this, "Nhập đầy đủ thông tin để thêm mới", Toast.LENGTH_SHORT).show();
        } else {
            // Parse dữ liệu đơn giá từ string sang float
            float donGiaMoi = Float.parseFloat(donGiaMoiStr);

            // Tạo đối tượng SanPham mới và set dữ liệu
            SanPham newSanPham = new SanPham();
            newSanPham.setTen(tenMoi);
            newSanPham.setDonGia(donGiaMoi);

            // Gọi phương thức addSanPham từ dbHelper để thêm sản phẩm mới vào cơ sở dữ liệu
              dbHelper.addSanPham(newSanPham);

                Toast.makeText(ChiTietSanPhamActivity.this, "Đã thêm mới sản phẩm", Toast.LENGTH_SHORT).show();
                finish(); // Kết thúc activity sau khi thêm mới thành công

        }
    }
}
