package com.pma101.lapmarket;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pma101.lapmarket.Handel.Item_Handle;
import com.pma101.lapmarket.adapter.LaptopAdapter;
import com.pma101.lapmarket.models.Laptop;
import com.pma101.lapmarket.services.HttpRequest;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements Item_Handle {

    private HttpRequest httpRequest;
    private RecyclerView recycle_laptop;
    private LaptopAdapter adapter;
    FloatingActionButton fltAdd;
    private ArrayList<Laptop> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        recycle_laptop = findViewById(R.id.recycle_laptop);
        fltAdd = findViewById(R.id.fltadd);
        httpRequest = new HttpRequest();

        httpRequest.callAPI()
                .getListLaptop()
                .enqueue(getStudentAPI);
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

                if (Ten.isEmpty() || Gia.isEmpty() || ThuongHieu.isEmpty()|| XuatXu.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {


                    laptops.setHinhanh(Anh);
                    laptops.setTen(Ten);
                    laptops.setGia(Gia);
                    laptops.setThuonghieu(ThuongHieu);
                    laptops.setXuatxu(XuatXu);
                    httpRequest.callAPI().addLaptop(laptops).enqueue(responseDistributorAPI);
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Trạng thái phải là một số nguyên", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    ;

    private void getData(ArrayList<Laptop> list) {
        adapter = new LaptopAdapter(this, list, this);
        recycle_laptop.setLayoutManager(new LinearLayoutManager(this));
        recycle_laptop.setAdapter(adapter);
    }

    Callback<Response<ArrayList<Laptop>>> getListStudentAPI =new Callback<Response<ArrayList<Laptop>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Laptop>>> call, retrofit2.Response<Response<ArrayList<Laptop>>> response) {
            //khi call thành công
            if (response.isSuccessful()){
                if (response.body().getStatus() == 200){ //check status code
                    list= response.body().getData();// gán dữ liệu trả về từ phản hồi vào biến ds
                    getData(list);
                    Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Laptop>>> call, Throwable t) {
            Log.d(">>>> Student", "onFailure: " + t.getMessage());
        }
    };

    Callback<Response<ArrayList<Laptop>>> getStudentAPI = new Callback<Response<ArrayList<Laptop>>>() {
        @Override
        public void onResponse(Call<Response<ArrayList<Laptop>>> call, retrofit2.Response<Response<ArrayList<Laptop>>> response) {
            if (response.isSuccessful()) {
                if (response.body().getStatus() == 200) {
                    ArrayList<Laptop> list = response.body().getData();
                    getData(list);
                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<ArrayList<Laptop>>> call, Throwable t) {
            Log.d(">>>GetListDistributor", "onFailure: " + t.getMessage());
        }
    };
    Callback<Response<Laptop>> responseDistributorAPI = new Callback<Response<Laptop>>() {
        @Override
        public void onResponse(Call<Response<Laptop>> call, retrofit2.Response<Response<Laptop>> response) {
            if (response.isSuccessful()) {
                //check status code
                if (response.body().getStatus() == 200) {
                    //Call lại API danh sách
                    httpRequest.callAPI()
                            .getListLaptop() //Phương thức API cần thực thi
                            .enqueue(getStudentAPI);
                    //Toast ra thông tin từ Messenger
                    Toast.makeText(MainActivity.this, response.body().getMessenger(), Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onFailure(Call<Response<Laptop>> call, Throwable t) {
            Log.d(">>> GetListDistributor", "onFailure: " + t.getMessage());
        }
    };


    @Override
    public void Delete(String id) {
        httpRequest.callAPI().deleteLaptopById(id).enqueue(responseDistributorAPI);
    }

    @Override
    public void Update(String id, Laptop laptops) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
                String id = laptops.get_id();

                if (Ten.isEmpty() || Gia.isEmpty() || ThuongHieu.isEmpty() || XuatXu.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }
                try {


                    Laptop laptops1 = new Laptop();
                    laptops1.setHinhanh(Anh);
                    laptops1.setTen(Ten);
                    laptops1.setGia(Gia);
                    laptops1.setThuonghieu(ThuongHieu);
                    laptops1.setXuatxu(XuatXu);
                    httpRequest.callAPI().updateLaptopById(id, laptops1).enqueue(responseDistributorAPI);
                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Chỉnh sửa thành công", Toast.LENGTH_SHORT).show();
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Trạng thái phải là một số nguyên", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}