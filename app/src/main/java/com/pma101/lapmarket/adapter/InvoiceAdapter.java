package com.pma101.lapmarket.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.pma101.lapmarket.R;
import com.pma101.lapmarket.models.InvoiceItem;

import java.util.List;

public class InvoiceAdapter extends RecyclerView.Adapter<InvoiceAdapter.ViewHolder> {

    private List<InvoiceItem> invoiceItems;

    public InvoiceAdapter(List<InvoiceItem> invoiceItems) {
        this.invoiceItems = invoiceItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ql_hoadon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InvoiceItem invoiceItem = invoiceItems.get(position);
        holder.txtTenHoaDon.setText(invoiceItem.getLaptop().getTen());
        holder.txtGiaTienHoaDon.setText(invoiceItem.getLaptop().getGia() + " VND");
        holder.txtSoLuongHoaDon.setText(String.valueOf(invoiceItem.getQuantity()));
        holder.txtCustomerName.setText(invoiceItem.getCustomerName());
        holder.txtCustomerPhone.setText(invoiceItem.getCustomerPhone());
        holder.txtCustomerAddress.setText(invoiceItem.getCustomerAddress());
        holder.txtStatus.setText(invoiceItem.getStatus());
        Glide.with(holder.itemView.getContext()).load(invoiceItem.getLaptop().getHinhanh()).into(holder.imgAnhHoaDon);

        holder.btnThanhToan.setOnClickListener(v -> showPaymentDialog(holder.itemView.getContext(), invoiceItem, position));
    }

    @Override
    public int getItemCount() {
        return invoiceItems.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenHoaDon, txtGiaTienHoaDon, txtSoLuongHoaDon;
        TextView txtCustomerName, txtCustomerPhone, txtCustomerAddress, txtStatus;
        ImageView imgAnhHoaDon;
        Button btnThanhToan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenHoaDon = itemView.findViewById(R.id.txt_tenHoaDon);
            txtGiaTienHoaDon = itemView.findViewById(R.id.txt_giaTienHoaDon);
            txtSoLuongHoaDon = itemView.findViewById(R.id.txt_soLuongHoaDon);
            txtCustomerName = itemView.findViewById(R.id.txt_customerName);
            txtCustomerPhone = itemView.findViewById(R.id.txt_customerPhone);
            txtCustomerAddress = itemView.findViewById(R.id.txt_customerAddress);
            txtStatus = itemView.findViewById(R.id.txt_status);
            imgAnhHoaDon = itemView.findViewById(R.id.imgAnhHoaDon);
            btnThanhToan = itemView.findViewById(R.id.btn_thanhToan);
        }
    }

    private void showPaymentDialog(Context context, InvoiceItem invoiceItem, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_payment, null);
        builder.setView(view);

        ImageView imgProduct = view.findViewById(R.id.imgProduct);
        Button btnPayment = view.findViewById(R.id.btnPayment);

        Glide.with(context).load(invoiceItem.getLaptop().getHinhanh()).into(imgProduct);

        AlertDialog dialog = builder.create();
        dialog.show();

        btnPayment.setOnClickListener(v -> {
            invoiceItem.setStatus("Đã thanh toán");
            notifyDataSetChanged();
            dialog.dismiss();
        });
    }
}
