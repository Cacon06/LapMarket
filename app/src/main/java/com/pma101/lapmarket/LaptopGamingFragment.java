package com.pma101.lapmarket;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pma101.lapmarket.Handel.Item_Handle;
import com.pma101.lapmarket.adapter.LaptopGmAdapter;
import com.pma101.lapmarket.models.Laptop;

import java.util.ArrayList;

public class LaptopGamingFragment extends Fragment implements Item_Handle {

    private RecyclerView recyclerView;
    private LaptopGmAdapter adapter;
    private ArrayList<Laptop> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>(LaptopRepository.getInstance().getLaptops());
        LaptopRepository.getInstance().registerListener(dataChangeListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LaptopRepository.getInstance().unregisterListener(dataChangeListener);
    }

    @Override
    public void AddToCart(Laptop laptop) {
        CartRepository.getInstance().addToCart(laptop);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_laptop_gaming, container, false);
        recyclerView = view.findViewById(R.id.recyclerView_gaming);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LaptopGmAdapter(getContext(), list, this);  // Truyền 'this' như tham số thứ ba
        recyclerView.setAdapter(adapter);

        return view;
    }

    private final LaptopRepository.DataChangeListener dataChangeListener = new LaptopRepository.DataChangeListener() {
        @Override
        public void onDataChanged() {
            list.clear();
            list.addAll(LaptopRepository.getInstance().getLaptops());
            adapter.notifyDataSetChanged();
        }
    };





    @Override
    public void Delete(String id) {

    }

    @Override
    public void Update(String id, Laptop laptops) {

    }
}
