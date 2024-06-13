package com.pma101.lapmarket;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.navigation.NavigationView;
import com.pma101.lapmarket.frag.Fragment_QL_KhachHang;
import com.pma101.lapmarket.frag.GioHangFragment;
import com.pma101.lapmarket.frag.QLLaptopFragmen;
import com.pma101.lapmarket.frag.QLLaptopFragmen;
import com.pma101.lapmarket.frag.frg_cskh;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView nav;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawerlayout);
        toolbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.nav);
        View headerLayout = nav.getHeaderView(0);
        TextView txt_ten = headerLayout.findViewById(R.id.txt_ten);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Trang Chủ");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.frmNav, new QLLaptopFragmen()).commit();

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.thoat) {
                    showDialogDangXuat();
                } else if (item.getItemId() == R.id.trangchu) {
                    QLLaptopFragmen frgTrangchu = new QLLaptopFragmen();
                    replaceFragment(frgTrangchu);
                }else if (item.getItemId() == R.id.cskh) {
                    frg_cskh frg_cskh = new frg_cskh();
                    replaceFragment(frg_cskh);
                }else if (item.getItemId() == R.id.lapVanPhong_macbook) {
                    LaptopGamingFragment frg_laptopgaming = new LaptopGamingFragment();
                    replaceFragment(frg_laptopgaming);
                }else if (item.getItemId() == R.id.giohang) {
                    GioHangFragment frg_giohang = new GioHangFragment();
                    replaceFragment(frg_giohang);
                }else if (item.getItemId() == R.id.lichsu) {
                    HoaDonFragment frg_hoadon = new HoaDonFragment();
                    replaceFragment(frg_hoadon);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                getSupportActionBar().setTitle(item.getTitle());
                return true;
            }
        });

        // Đọc loại tài khoản từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String loaitk = sharedPreferences.getString("loaitaikhoan", "");

        Menu menu = nav.getMenu();
        if (!loaitk.equals("admin")) {
            menu.findItem(R.id.quanli_sp_home).setVisible(false);
            menu.findItem(R.id.quanli_sp_gaming).setVisible(false);
            menu.findItem(R.id.quanli_sp_vanphong).setVisible(false);
            menu.findItem(R.id.thongKe_dt).setVisible(false);
        } else {
            menu.findItem(R.id.cskh).setVisible(false);
            menu.findItem(R.id.giohang).setVisible(false);
            menu.findItem(R.id.lichsu).setVisible(false);
            menu.findItem(R.id.lapVanPhong_macbook).setVisible(false);
            menu.findItem(R.id.trangchu).setVisible(false);
        }

        // Hiển thị tên người dùng
        String hoten = sharedPreferences.getString("hoten", "");
        txt_ten.setText(hoten);
    }

    public void replaceFragment(Fragment frg) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.frmNav, frg).commit();
    }

    private void showDialogDangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.canhbao);
        builder.setMessage("Bạn chắc chắn muốn thoát");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                Toast.makeText(MainActivity.this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
