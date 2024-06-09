package com.pma101.lapmarket;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pma101.lapmarket.adapter.InvoiceAdapter;
import com.pma101.lapmarket.models.InvoiceItem;

import java.util.List;

public class HoaDonFragment extends Fragment {

    private RecyclerView recyclerView;
    private InvoiceAdapter invoiceAdapter;
    private List<InvoiceItem> invoiceItems;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_hoa_don, container, false);

        // Initialize RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_hoa_don);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Adapter
        invoiceItems = InvoiceRepository.getInstance().getInvoiceItems();
        invoiceAdapter = new InvoiceAdapter(invoiceItems);
        recyclerView.setAdapter(invoiceAdapter);

        return view;
    }
}
