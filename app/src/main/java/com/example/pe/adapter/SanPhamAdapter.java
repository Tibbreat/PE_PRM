package com.example.pe.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.pe.R;
import com.example.pe.entities.SanPham;

import java.util.List;

public class SanPhamAdapter extends ArrayAdapter<SanPham> {
    private Context context;
    private int resource;
    private List<SanPham> sanPhamList;

    public SanPhamAdapter(Context context, int resource, List<SanPham> sanPhamList) {
        super(context, resource, sanPhamList);
        this.context = context;
        this.resource = resource;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
        }

        SanPham sanPham = sanPhamList.get(position);

        TextView tvMa = convertView.findViewById(R.id.tvMa);
        TextView tvTen = convertView.findViewById(R.id.tvTen);
        TextView tvDonGia = convertView.findViewById(R.id.tvDonGia);
        TextView tvMaDanhMuc = convertView.findViewById(R.id.tvMaDanhMuc);

        tvMa.setText("Mã: " + sanPham.getMa());
        tvTen.setText("Tên: " + sanPham.getTen());
        tvDonGia.setText("Đơn giá: " + sanPham.getDonGia() + " VND");
        tvMaDanhMuc.setText("Mã danh mục: " + sanPham.getMaDanhMuc());

        return convertView;
    }
}
