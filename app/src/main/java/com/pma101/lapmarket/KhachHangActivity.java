package com.pma101.lapmarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pma101.lapmarket.Handel.Item_Handle;
import com.pma101.lapmarket.Handel.Item_Handle_KH;
import com.pma101.lapmarket.adapter.KhachHangAdapter;
import com.pma101.lapmarket.adapter.LaptopAdapter;
import com.pma101.lapmarket.models.Khachhang;
import com.pma101.lapmarket.models.Laptop;
import com.pma101.lapmarket.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class KhachHangActivity extends AppCompatActivity implements Item_Handle_KH {

    private HttpRequest httpRequest;
    private RecyclerView recycle_khachhang;
    private KhachHangAdapter adapter;
    FloatingActionButton fltAdd;
    private ArrayList<Khachhang> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_khach_hang);

        recycle_khachhang = findViewById(R.id.recycle_khachang);
        fltAdd = findViewById(R.id.fltadd);
        httpRequest = new HttpRequest();

        httpRequest.callAPI()
                .getListKH()
                .enqueue(getListKhachHangAPI);
        fltAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opendialogthem();
            }
        });

    }

    private void opendialogthem() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
        Button btnadd = view.findViewById(R.id.btnThem);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Anh = edtAnh_ad.getText().toString();
                String Ten = edtTen_ad.getText().toString();
                String Tuoi = edtTuoi_ad.getText().toString();
                String Diachi = edtDiachi_ad.getText().toString();
                String Gioitinh = edtGioitinh.getText().toString();
                Khachhang khachhangs = new Khachhang();

                if (Ten.isEmpty() || Tuoi.isEmpty() || Diachi.isEmpty()|| Gioitinh.isEmpty()) {
                    Toast.makeText(KhachHangActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {


                    khachhangs.setAnhDD(Anh);
                    khachhangs.setTenKH(Ten);
                    khachhangs.setTuoiKH(Tuoi);
                    khachhangs.setDiachi(Diachi);
                    khachhangs.setGioitinh(Gioitinh);
                    httpRequest.callAPI().addKH(khachhangs).enqueue(responseDistributorAPI);
                    Toast.makeText(KhachHangActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(KhachHangActivity.this, "Trạng thái phải là một số nguyên", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void getData(ArrayList<Khachhang> list) {
        adapter = new KhachHangAdapter(this, list, this);
        recycle_khachhang.setLayoutManager(new LinearLayoutManager(this));
        recycle_khachhang.setAdapter(adapter);
    }

    Callback<Response<ArrayList<Khachhang>>> getListKhachHangAPI =new Callback<Response<ArrayList<Khachhang>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Khachhang>>> call, retrofit2.Response<Response<ArrayList<Khachhang>>> response) {
            //khi call thành công
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){ //check status code
                    list= response.body().getData();// gán dữ liệu trả về từ phản hồi vào biến ds
                    getData(list);
                    Toast.makeText(KhachHangActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Khachhang>>> call, Throwable t) {
            Log.d(">>>> Student", "onFailure: " + t.getMessage());
        }
    };

    Callback<Response<ArrayList<Khachhang>>> getKhachHangAPI = new Callback<Response<ArrayList<Khachhang>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Khachhang>>> call, retrofit2.Response<Response<ArrayList<Khachhang>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Khachhang> list = response.body().getData();
                    getData(list);
                    Toast.makeText(KhachHangActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Khachhang>>> call, Throwable t) {
            Log.d(">>>GetListDistributor", "onFailure: " + t.getMessage());
        }
    };
    Callback<Response<Khachhang>> responseDistributorAPI = new Callback<Response<Khachhang>>() {
        @Override
        public void onResponse(Call<Response<Khachhang>> call, retrofit2.Response<Response<Khachhang>> response) {
            if (response.isSuccessful()) {
                //check status code
                if (response.body().getStatus() == 200) {
                    //Call lại API danh sách
                    httpRequest.callAPI()
                            .getListKH() //Phương thức API cần thực thi
                            .enqueue(getKhachHangAPI);
                    //Toast ra thông tin từ Messenger
                    Toast.makeText(KhachHangActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Khachhang>> call, Throwable t) {
            Log.d(">>> GetListDistributor", "onFailure: " + t.getMessage());
        }
    };




    @Override
    public void Delete(String id) {
        httpRequest.callAPI().deleteKHById(id).enqueue(responseDistributorAPI);
    }

    @Override
    public void Update(String id, Khachhang khachhangs) {
        AlertDialog.Builder builder = new AlertDialog.Builder(KhachHangActivity.this);
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
                String Anh = edtAnh_up.getText().toString();
                String Ten = edtTen_up.getText().toString();
                String Tuoi = edtTuoi_up.getText().toString();
                String Diachi = edtDiachi_up.getText().toString();
                String Gioitinh = edtGioitinh_up.getText().toString();
                String id = khachhangs.get_id();

                if (Ten.isEmpty() || Tuoi.isEmpty() || Diachi.isEmpty() || Gioitinh.isEmpty()) {
                    Toast.makeText(KhachHangActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {


                    Khachhang khachhangs1 = new Khachhang();
                    khachhangs1.setAnhDD(Anh);
                    khachhangs1.setTenKH(Ten);
                    khachhangs1.setTuoiKH(Tuoi);
                    khachhangs1.setDiachi(Diachi);
                    khachhangs1.setGioitinh(Gioitinh);
                    httpRequest.callAPI().updateKHById(id, khachhangs1).enqueue(responseDistributorAPI);
                    dialog.dismiss();
                    Toast.makeText(KhachHangActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(KhachHangActivity.this, "Trạng thái phải là một số nguyên", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


}