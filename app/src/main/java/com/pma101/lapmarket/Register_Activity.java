package com.pma101.lapmarket;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register_Activity extends AppCompatActivity {

    AppCompatButton btn_res_dk;
    EditText edt_email_dk, edt_pass_dk, edt_loatk_dk;
    private FirebaseAuth auth;

    String email;
    String pass;
    String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        edt_email_dk = findViewById(R.id.edt_email_dk);
        edt_pass_dk = findViewById(R.id.edt_pass_dk);
        edt_loatk_dk = findViewById(R.id.edt_loatk_dk);
        btn_res_dk = findViewById(R.id.btn_res_dk);
        auth = FirebaseAuth.getInstance();

        btn_res_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_email_dk.getText().toString();
                pass = edt_pass_dk.getText().toString();
                role = edt_loatk_dk.getText().toString().toLowerCase();

                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                            // Lưu loại tài khoản vào SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("loaitaikhoan", role);
                            editor.apply();

                            Intent intent = new Intent(Register_Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng ký không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
