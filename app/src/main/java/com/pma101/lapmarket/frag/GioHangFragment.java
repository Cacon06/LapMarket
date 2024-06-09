package com.pma101.lapmarket.frag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pma101.lapmarket.CartRepository;
import com.pma101.lapmarket.R;
import com.pma101.lapmarket.adapter.CartAdapter;
import com.pma101.lapmarket.models.CartItem;

import java.util.List;

public class GioHangFragment extends Fragment implements CartRepository.CartChangeListener {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartItem> cartItems;
    private TextView txtTotalPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gio_hang, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.rec_giohang);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize TextView for total price
        txtTotalPrice = view.findViewById(R.id.txt_tongtien_gh);

        // Initialize Adapter
        cartItems = CartRepository.getInstance().getCartItems();
        cartAdapter = new CartAdapter(cartItems);
        recyclerView.setAdapter(cartAdapter);

        updateTotalPrice();

        // Register for cart changes
        CartRepository.getInstance().registerListener(this);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Unregister for cart changes
        CartRepository.getInstance().unregisterListener(this);
    }

    private void updateTotalPrice() {
        int totalPrice = 0;
        for (CartItem item : cartItems) {
            int giaTien = Integer.parseInt(item.getLaptop().getGia());
            totalPrice += giaTien * item.getQuantity();
        }
        txtTotalPrice.setText("Tổng tiền: " + totalPrice + " VND");
    }

    @Override
    public void onCartChanged() {
        cartAdapter.notifyDataSetChanged();
        updateTotalPrice();
    }
}
