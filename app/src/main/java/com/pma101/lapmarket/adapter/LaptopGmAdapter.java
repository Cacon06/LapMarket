
package com.pma101.lapmarket.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.pma101.lapmarket.Handel.Item_Handle;
import com.pma101.lapmarket.R;
import com.pma101.lapmarket.models.Laptop;

import java.util.ArrayList;

public class LaptopGmAdapter extends RecyclerView.Adapter<LaptopGmAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Laptop> list;
    private Item_Handle itemHandle;

    public LaptopGmAdapter(Context context, ArrayList<Laptop> list, Item_Handle itemHandle) {
        this.context = context;
        this.list = list;
        this.itemHandle = itemHandle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_laptop_gaming, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Laptop laptop = list.get(position);
        holder.txtTen.setText("Tên: " + laptop.getTen());
        holder.txtGia.setText("Giá: " + laptop.getGia());
        holder.txtThuongHieu.setText("Thương Hiệu: " + laptop.getThuonghieu());
        holder.txtXuatXu.setText("Xuất Xứ: " + laptop.getXuatxu());
        Glide.with(context).load(laptop.getHinhanh()).into(holder.imgAnh);

        holder.btnAddToCart.setOnClickListener(v -> {
            itemHandle.AddToCart(laptop);
            Toast.makeText(context, laptop.getTen() + " đã được thêm vào giỏ hàng", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtGia, txtThuongHieu, txtXuatXu;
        ImageView imgAnh;
        Button btnAddToCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txt_Ten);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtThuongHieu = itemView.findViewById(R.id.txtThuongHieu);
            txtXuatXu = itemView.findViewById(R.id.txtXuatXu);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
        }
    }
}
