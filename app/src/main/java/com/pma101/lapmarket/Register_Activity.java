package com.pma101.lapmarket;

import android.content.Intent;
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
    EditText edt_email_dk, edt_pass_dk;

    private FirebaseAuth auth;

    String email ;
    String pass ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        FirebaseApp.initializeApp(this);
        edt_email_dk = (EditText) findViewById(R.id.edt_email_dk);
        edt_pass_dk = (EditText) findViewById(R.id.edt_pass_dk);
        btn_res_dk = (AppCompatButton) findViewById(R.id.btn_res_dk);
        auth = FirebaseAuth.getInstance();

        btn_res_dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edt_email_dk.getText().toString();
                pass = edt_pass_dk.getText().toString();
                auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(Register_Activity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Dang ky thanh cong", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(Register_Activity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Dang ky ko thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}