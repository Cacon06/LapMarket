package com.pma101.lapmarket.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pma101.lapmarket.CartRepository;
import com.pma101.lapmarket.InvoiceRepository;
import com.pma101.lapmarket.R;
import com.pma101.lapmarket.models.CartItem;
import com.pma101.lapmarket.models.InvoiceItem;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartItem> cartItems;

    public CartAdapter(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_giohang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartItem cartItem = cartItems.get(position);
        holder.txtTenGioHang.setText("Tên laptop: " +cartItem.getLaptop().getTen());
        holder.txtGiaTienGioHang.setText("Giá tiền:: " +cartItem.getLaptop().getGia() + " VND");
        holder.txtSoLuong.setText("Số lượng: " +String.valueOf(cartItem.getQuantity()));
        Glide.with(holder.itemView.getContext()).load(cartItem.getLaptop().getHinhanh()).into(holder.imgAnh);

        holder.btnSlT.setOnClickListener(v -> {
            if (cartItem.getQuantity() > 1) {
                cartItem.setQuantity(cartItem.getQuantity() - 1);
                notifyDataSetChanged();
            }
        });

        holder.btnSlC.setOnClickListener(v -> {
            cartItem.setQuantity(cartItem.getQuantity() + 1);
            notifyDataSetChanged();
        });
        holder.btnMuaHang.setOnClickListener(v -> {
            showPurchaseDialog(holder.itemView.getContext(), cartItem, position);
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenGioHang, txtGiaTienGioHang, txtSoLuong;
        ImageView imgAnh;
        Button btnSlT, btnSlC, btnMuaHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenGioHang = itemView.findViewById(R.id.txt_tenGioHang);
            txtGiaTienGioHang = itemView.findViewById(R.id.txt_giaTiengh);
            txtSoLuong = itemView.findViewById(R.id.txt_soLuong);
            imgAnh = itemView.findViewById(R.id.imgAnh);
            btnSlT = itemView.findViewById(R.id.btn_slT);
            btnSlC = itemView.findViewById(R.id.btn_slC);
            btnMuaHang = itemView.findViewById(R.id.btn_muahang_gh);
        }
    }

    private void showPurchaseDialog(Context context, CartItem cartItem, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_thongtin_muahang, null);
        builder.setView(view);

        EditText edtSdt = view.findViewById(R.id.edt_sdt);
        EditText edtNamSinh = view.findViewById(R.id.edt_namsinh);
        EditText edtDiaChi = view.findViewById(R.id.edt_diachi);
        Button btnConfirm = view.findViewById(R.id.btnConfirm);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnConfirm.setOnClickListener(v -> {
            String sdt = edtSdt.getText().toString();
            String namSinh = edtNamSinh.getText().toString();
            String diaChi = edtDiaChi.getText().toString();

            if (sdt.isEmpty() || namSinh.isEmpty() || diaChi.isEmpty()) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;
            }

            // Xử lý mua hàng thành công
            handlePurchase(cartItem, sdt, namSinh, diaChi);

            dialog.dismiss();
        });
    }

    private void handlePurchase(CartItem cartItem, String customerPhone, String customerBirthYear, String customerAddress) {
        // Thêm laptop vào hóa đơn với thông tin khách hàng
        InvoiceRepository.getInstance().addInvoiceItem(new InvoiceItem(cartItem.getLaptop(), cartItem.getQuantity(), customerPhone, customerBirthYear, customerAddress));

        // Xóa laptop khỏi giỏ hàng
        CartRepository.getInstance().removeFromCart(cartItem);
    }
}
