package com.pma101.lapmarket.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pma101.lapmarket.Handel.Item_Handle;
import com.pma101.lapmarket.Handel.Item_Handle_KH;
import com.pma101.lapmarket.R;
import com.pma101.lapmarket.models.Khachhang;
import com.pma101.lapmarket.models.Laptop;
import com.pma101.lapmarket.services.HttpRequest;

import java.util.ArrayList;

public class KhachHangAdapter extends RecyclerView.Adapter<KhachHangAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Khachhang> list;

    private Item_Handle_KH handle;
    private HttpRequest httpRequest;

    public KhachHangAdapter(Context context, ArrayList<Khachhang> list, Item_Handle_KH handle) {
        this.context = context;
        this.list = list;
        this.handle = handle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_khachhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Khachhang khachhang = list.get(position);
        holder.txtTenKH.setText("Tên Khách Hàng: " + khachhang.getTenKH());
        holder.txtTuoiKH.setText("Tuổi: " + khachhang.getTuoiKH());
        holder.txtDiachi.setText("Địa chỉ: " + khachhang.getDiachi());
        holder.txtGioitinh.setText("Giới tính: " + khachhang.getGioitinh());
        Glide.with(context).load(khachhang.getAnhDD()).into(holder.imgAnh);
        httpRequest = new HttpRequest();


        holder.btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setMessage("Bạn có muốn xoá không?")
                    .setCancelable(false)
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        if (handle != null) {
                            handle.Delete(khachhang.get_id());
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.cancel())
                    .create()
                    .show();
        });

        holder.btnUpdate.setOnClickListener(view -> {
            if (handle != null) {
                handle.Update(khachhang.get_id(), khachhang);
            }
        });

        holder.itemView.setOnLongClickListener(view -> {
            if (handle != null) {
                handle.Update(khachhang.get_id(), khachhang);
            }
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenKH, txtTuoiKH, txtDiachi, txtGioitinh;
        ImageView imgAnh, btnDelete, btnUpdate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenKH = itemView.findViewById(R.id.txt_TenKH);
            txtTuoiKH = itemView.findViewById(R.id.txt_tuoi);
            txtDiachi = itemView.findViewById(R.id.txt_diachi);
            txtGioitinh = itemView.findViewById(R.id.txt_gioitinh);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
        }
    }
}
