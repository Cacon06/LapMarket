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
import com.pma101.lapmarket.Handel.Item_Handle_KH;
import com.pma101.lapmarket.R;
import com.pma101.lapmarket.adapter.KhachHangAdapter;
import com.pma101.lapmarket.models.Khachhang;
import com.pma101.lapmarket.models.Laptop;
import com.pma101.lapmarket.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_QL_KhachHang extends Fragment implements Item_Handle_KH {

    private HttpRequest httpRequest;
    private RecyclerView recycle_khachhang;
    private KhachHangAdapter adapter;
    private FloatingActionButton fltAdd;
    private ArrayList<Khachhang> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        httpRequest = new HttpRequest();
        list = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__q_l__khach_hang, container, false);
        recycle_khachhang = view.findViewById(R.id.recycle_khachang);
        fltAdd = view.findViewById(R.id.fltadd);

        // Initialize the RecyclerView and adapter here
        adapter = new KhachHangAdapter(getContext(), list, this);
        recycle_khachhang.setLayoutManager(new LinearLayoutManager(getContext()));
        recycle_khachhang.setAdapter(adapter);


        httpRequest.callAPI().getListKH().enqueue(getListKhachHangAPI);

        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialogThem();
            }
        });

        return view;
    }

    private void openDialogThem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_add_kh, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAnh_ad = view.findViewById(R.id.edtAnh_ad_kh);
        EditText edtTen_ad = view.findViewById(R.id.edtTen_ad_kh);
        EditText edtTuoi_ad = view.findViewById(R.id.edtTuoi_ad);
        EditText edtDiachi_ad = view.findViewById(R.id.edtDiachi_ad);
        EditText edtGioitinh = view.findViewById(R.id.edtGioitinh_ad);
        Button btnAdd = view.findViewById(R.id.btnThem);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Anh = edtAnh_ad.getText().toString();
                String Ten = edtTen_ad.getText().toString();
                String Tuoi = edtTuoi_ad.getText().toString();
                String Diachi = edtDiachi_ad.getText().toString();
                String Gioitinh = edtGioitinh.getText().toString();
                Khachhang khachhangs = new Khachhang();

                if (Ten.isEmpty() || Tuoi.isEmpty() || Diachi.isEmpty() || Gioitinh.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    khachhangs.setAnhDD(Anh);
                    khachhangs.setTenKH(Ten);
                    khachhangs.setTuoiKH(Tuoi);
                    khachhangs.setDiachi(Diachi);
                    khachhangs.setGioitinh(Gioitinh);
                    httpRequest.callAPI().addKH(khachhangs).enqueue(responseDistributorAPI);
                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Trạng thái phải là một số nguyên", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData(ArrayList<Khachhang> list) {
        adapter = new KhachHangAdapter(getContext(), list, this);
        recycle_khachhang.setLayoutManager(new LinearLayoutManager(getContext()));
        recycle_khachhang.setAdapter(adapter);
    }

    Callback<com.pma101.lapmarket.Response<ArrayList<Khachhang>>> getListKhachHangAPI = new Callback<com.pma101.lapmarket.Response<ArrayList<Khachhang>>>() {
        @Override
        public void onResponse(Call<com.pma101.lapmarket.Response<ArrayList<Khachhang>>> call, Response<com.pma101.lapmarket.Response<ArrayList<Khachhang>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Khachhang> list = response.body().getData();
                    getData(list);
                    Toast.makeText(getContext(), response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<com.pma101.lapmarket.Response<ArrayList<Khachhang>>> call, Throwable t) {
            Log.d(">>> GetListLaptop", "onFailure: " + t.getMessage());
        }
    };

    Callback<com.pma101.lapmarket.Response<Khachhang>> responseDistributorAPI = new Callback<com.pma101.lapmarket.Response<Khachhang>>() {
        @Override
        public void onResponse(Call<com.pma101.lapmarket.Response<Khachhang>> call, Response<com.pma101.lapmarket.Response<Khachhang>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    // Gọi lại API để lấy danh sách khách hàng sau khi thêm mới
                    httpRequest.callAPI().getListKH().enqueue(getListKhachHangAPI);
                    Toast.makeText(getContext(), response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<com.pma101.lapmarket.Response<Khachhang>> call, Throwable t) {
            Log.d(">>> AddKhachHang", "onFailure: " + t.getMessage());
        }
    };


    @Override
    public void Delete(String id) {
        httpRequest.callAPI().deleteKHById(id).enqueue(responseDistributorAPI);
    }

    @Override
    public void Update(String id, Khachhang khachhangs) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.item_update_kh, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText edtAnh_up = view.findViewById(R.id.edtAnh_up);
        EditText edtTen_up = view.findViewById(R.id.edtTen_up);
        EditText edtTuoi_up = view.findViewById(R.id.edtTuoi_up);
        EditText edtDiachi_up = view.findViewById(R.id.edtDiachi_up);
        EditText edtGioitinh_up = view.findViewById(R.id.edtGioitinh_up);
        Button btnUpdate = view.findViewById(R.id.btnUpdate);

        edtAnh_up.setText(khachhangs.getAnhDD());
        edtTen_up.setText(khachhangs.getTenKH());
        edtTuoi_up.setText(khachhangs.getTuoiKH());
        edtDiachi_up.setText(khachhangs.getDiachi());
        edtGioitinh_up.setText(khachhangs.getGioitinh());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Anh = edtAnh_up.getText().toString().trim();
                String Ten = edtTen_up.getText().toString().trim();
                String Tuoi = edtTuoi_up.getText().toString().trim();
                String Diachi = edtDiachi_up.getText().toString().trim();
                String Gioitinh = edtGioitinh_up.getText().toString().trim();
                if (Ten.isEmpty() || Tuoi.isEmpty() || Diachi.isEmpty() || Gioitinh.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {
                    Khachhang updatedKhachhang = new Khachhang();
                    updatedKhachhang.setAnhDD(Anh);
                    updatedKhachhang.setTenKH(Ten);
                    updatedKhachhang.setTuoiKH(Tuoi);
                    updatedKhachhang.setDiachi(Diachi);
                    updatedKhachhang.setGioitinh(Gioitinh);
                    httpRequest.callAPI().updateKHById(id, updatedKhachhang).enqueue(new Callback<com.pma101.lapmarket.Response<Khachhang>>() {
                        @Override
                        public void onResponse(Call<com.pma101.lapmarket.Response<Khachhang>> call, Response<com.pma101.lapmarket.Response<Khachhang>> response) {
                            if (response.isSuccessful() && response.body().getStatus() == 200) {
                                httpRequest.callAPI().getListKH().enqueue(getListKhachHangAPI);
                                Toast.makeText(getContext(), response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<com.pma101.lapmarket.Response<Khachhang>> call, Throwable t) {
                            Log.d(">>> UpdateLaptop", "onFailure: " + t.getMessage());
                        }
                    });

                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Trạng thái phải là một số nguyên", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
