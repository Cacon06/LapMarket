package com.pma101.lapmarket.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.pma101.lapmarket.R;
import com.pma101.lapmarket.models.Laptop;
import com.pma101.lapmarket.services.HttpRequest;

import java.util.ArrayList;

public class LaptopAdapter extends RecyclerView.Adapter<LaptopAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Laptop> list;

    private Item_Handle handle;
    private HttpRequest httpRequest;

    public LaptopAdapter(Context context, ArrayList<Laptop> list, Item_Handle handle) {
        this.context = context;
        this.list = list;
        this.handle = handle;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_laptop, parent, false);
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
        httpRequest = new HttpRequest();

        holder.btn_xemthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showdialogXemThem(list.get(holder.getAdapterPosition()));
            }
        });

        holder.btnDelete.setOnClickListener(view -> {
            new AlertDialog.Builder(context)
                    .setMessage("Bạn có muốn xoá không?")
                    .setCancelable(false)
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        if (handle != null) {
                            handle.Delete(laptop.get_id());
                            Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("Không", (dialog, which) -> dialog.cancel())
                    .create()
                    .show();
        });

        holder.btnUpdate.setOnClickListener(view -> {
            if (handle != null) {
                handle.Update(laptop.get_id(), laptop);
            }
        });

        holder.itemView.setOnLongClickListener(view -> {
            if (handle != null) {
                handle.Update(laptop.get_id(), laptop);
            }
            return true;
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtGia, txtThuongHieu, txtXuatXu;
        ImageView imgAnh, btnDelete, btnUpdate;

        Button btn_xemthem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txt_Ten);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtThuongHieu = itemView.findViewById(R.id.txtThuongHieu);
            txtXuatXu = itemView.findViewById(R.id.txtXuatXu);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            btn_xemthem = itemView.findViewById(R.id.btn_xemthem);
        }
    }

    private void showdialogXemThem(Laptop lapTop){
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_chitiet_sanpham, null);
        builder.setView(view);

        TextView txt_tensp_chitiet = view.findViewById(R.id.txt_tensp_chitiet);
        TextView txt_giasp_chitiet = view.findViewById(R.id.txt_giasp_chitiet);
        TextView txt_thuonghieu_chtiet = view.findViewById(R.id.txt_thuonghieu_chtiet);
        TextView txt_xuatxu_chtiet = view.findViewById(R.id.txt_xuatxu_chtiet);



        txt_tensp_chitiet.setText("Tên: " + lapTop.getTen());
        txt_giasp_chitiet.setText("Giá: " + lapTop.getGia()
        );
        txt_thuonghieu_chtiet.setText("Thương hiệu: " + lapTop.getThuonghieu());
        txt_xuatxu_chtiet.setText("Xuất xứ: " + lapTop.getXuatxu());


        builder.setPositiveButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

}