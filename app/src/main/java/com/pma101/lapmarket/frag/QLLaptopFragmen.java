package com.pma101.lapmarket.frag;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pma101.lapmarket.Handel.Item_Handle;
import com.pma101.lapmarket.LaptopRepository;
import com.pma101.lapmarket.R;
import com.pma101.lapmarket.adapter.LaptopAdapter;
import com.pma101.lapmarket.models.Laptop;
import com.pma101.lapmarket.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QLLaptopFragmen extends Fragment implements Item_Handle {

    private HttpRequest httpRequest;
    private RecyclerView recycle_laptop;
    private LaptopAdapter adapter;
    private FloatingActionButton fltAdd;
    private ArrayList<Laptop> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpRequest = new HttpRequest();
        list = new ArrayList<>();
        LaptopRepository.getInstance().registerListener(dataChangeListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LaptopRepository.getInstance().unregisterListener(dataChangeListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_q_l_lap_top, container, false);
        recycle_laptop = view.findViewById(R.id.recycle_laptop);
        fltAdd = view.findViewById(R.id.fltadd);

        recycle_laptop.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new LaptopAdapter(getContext(), list, this);
        recycle_laptop.setAdapter(adapter);

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogthem();
            }
        });

        httpRequest.callAPI()
                .getListLaptop()
                .enqueue(getLaptopAPI);

        return view;
    }

    private void opendialogthem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAnh_ad = view.findViewById(R.id.edtAnh_ad);
        EditText edtTen_ad = view.findViewById(R.id.edtTen_ad);
        EditText edtGia_ad = view.findViewById(R.id.edtGia_ad);
        EditText edtthuonghieu_ad = view.findViewById(R.id.edtThuongHieu_ad);
        EditText edtxuatXu_ad = view.findViewById(R.id.edtXuatXu_ad);
        Button btnadd = view.findViewById(R.id.btnThem);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Anh = edtAnh_ad.getText().toString();
                String Ten = edtTen_ad.getText().toString();
                String Gia = edtGia_ad.getText().toString();
                String ThuongHieu = edtthuonghieu_ad.getText().toString();
                String XuatXu = edtxuatXu_ad.getText().toString();
                Laptop laptops = new Laptop();

                if (Ten.isEmpty() || Gia.isEmpty() || ThuongHieu.isEmpty() || XuatXu.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    laptops.setHinhanh(Anh);
                    laptops.setTen(Ten);
                    laptops.setGia(Gia);
                    laptops.setThuonghieu(ThuongHieu);
                    laptops.setXuatxu(XuatXu);
                    httpRequest.callAPI().addLaptop(laptops).enqueue(new Callback<com.pma101.lapmarket.Response<Laptop>>() {
                        @Override
                        public void onResponse(Call<com.pma101.lapmarket.Response<Laptop>> call, Response<com.pma101.lapmarket.Response<Laptop>> response) {
                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                LaptopRepository.getInstance().addLaptop(response.body().getData());
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<com.pma101.lapmarket.Response<Laptop>> call, Throwable t) {
                            Log.d(">>> AddLaptop", "onFailure: " + t.getMessage());
                        }
                    });
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Trạng thái phải là một số nguyên", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData(ArrayList<Laptop> list) {
        adapter = new LaptopAdapter(getContext(), list, this);
        recycle_laptop.setLayoutManager(new LinearLayoutManager(getContext()));
        recycle_laptop.setAdapter(adapter);
    }

    Callback<com.pma101.lapmarket.Response<ArrayList<Laptop>>> getLaptopAPI = new Callback<com.pma101.lapmarket.Response<ArrayList<Laptop>>>() {
        @Override
        public void onResponse(Call<com.pma101.lapmarket.Response<ArrayList<Laptop>>> call, Response<com.pma101.lapmarket.Response<ArrayList<Laptop>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Laptop> list = response.body().getData();
                    LaptopRepository.getInstance().setLaptops(list);
                    getData(list);
                    Toast.makeText(getContext(), response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<com.pma101.lapmarket.Response<ArrayList<Laptop>>> call, Throwable t) {
            Log.d(">>> GetListLaptop", "onFailure: " + t.getMessage());
        }
    };
    Callback<com.pma101.lapmarket.Response<Laptop>> responseLaptopAPI = new Callback<com.pma101.lapmarket.Response<Laptop>>() {
        @Override
        public void onResponse(Call<com.pma101.lapmarket.Response<Laptop>> call, Response<com.pma101.lapmarket.Response<Laptop>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    httpRequest.callAPI()
                            .getListLaptop()
                            .enqueue(getLaptopAPI);
                    Toast.makeText(getContext(), response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<com.pma101.lapmarket.Response<Laptop>> call, Throwable t) {
            Log.d(">>> GetListLaptop", "onFailure: " + t.getMessage());
        }
    };

    private final LaptopRepository.DataChangeListener dataChangeListener = new LaptopRepository.DataChangeListener() {
        @Override
        public void onDataChanged() {
            adapter.notifyDataSetChanged();
        }
    };

    @Override
    public void AddToCart(Laptop laptop) {

    }

    @Override
    public void Delete(String id) {
        httpRequest.callAPI().deleteLaptopById(id).enqueue(responseLaptopAPI);
    }

    @Override
    public void Update(String id, Laptop laptops) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAnh_up = view.findViewById(R.id.edtAnh_up);
        EditText edtTen_up = view.findViewById(R.id.edtTen_up);
        EditText edtGia_up = view.findViewById(R.id.edtGia_up);
        EditText edtthuonghieu_up = view.findViewById(R.id.edtThuongHieu_up);
        EditText edtxuatXu_up = view.findViewById(R.id.edtXuatXu_up);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);

        edtAnh_up.setText(laptops.getHinhanh());
        edtTen_up.setText(laptops.getTen());
        edtGia_up.setText(laptops.getGia());
        edtthuonghieu_up.setText(laptops.getThuonghieu());
        edtxuatXu_up.setText(laptops.getXuatxu());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Anh = edtAnh_up.getText().toString();
                String Ten = edtTen_up.getText().toString();
                String Gia = edtGia_up.getText().toString();
                String ThuongHieu = edtthuonghieu_up.getText().toString();
                String XuatXu = edtxuatXu_up.getText().toString();
                if (Ten.isEmpty() || Gia.isEmpty() || ThuongHieu.isEmpty() || XuatXu.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    laptops.setHinhanh(Anh);
                    laptops.setTen(Ten);
                    laptops.setGia(Gia);
                    laptops.setThuonghieu(ThuongHieu);
                    laptops.setXuatxu(XuatXu);

                    httpRequest.callAPI().updateLaptopById(id, laptops).enqueue(new Callback<com.pma101.lapmarket.Response<Laptop>>() {
                        @Override
                        public void onResponse(Call<com.pma101.lapmarket.Response<Laptop>> call, Response<com.pma101.lapmarket.Response<Laptop>> response) {
                            if (response.isSuccessful()) {
                                if (response.body().getStatus() == 200) {
                                    httpRequest.callAPI()
                                            .getListLaptop()
                                            .enqueue(getLaptopAPI);
                                    Toast.makeText(getContext(), response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<com.pma101.lapmarket.Response<Laptop>> call, Throwable t) {
                            Log.d(">>> AddLaptop", "onFailure: " + t.getMessage());
                        }
                    });
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Trạng thái phải là một số nguyên", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
