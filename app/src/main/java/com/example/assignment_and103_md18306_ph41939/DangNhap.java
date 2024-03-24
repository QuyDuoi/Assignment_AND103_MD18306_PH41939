package com.example.assignment_and103_md18306_ph41939;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DangNhap extends AppCompatActivity {
    TextInputEditText edtEmail, edtPass;
    TextInputLayout tilEmail, tilPass;
    Button btnDangNhap;
    TextView goToDangKy, forgotPass, dangNhapPhone;
    private FirebaseAuth mAuth;
    String email, pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);
        mAuth = FirebaseAuth.getInstance();
        edtEmail = findViewById(R.id.edtEmailDn);
        edtPass = findViewById(R.id.edtPassDn);
        tilEmail = findViewById(R.id.tilEmailDn);
        tilPass = findViewById(R.id.tilPassDn);
        btnDangNhap = findViewById(R.id.btnDangNhap);
        goToDangKy = findViewById(R.id.goToDangKy);
        forgotPass = findViewById(R.id.goToQuenMatKhau);
        dangNhapPhone = findViewById(R.id.dangNhapPhone);
        dangNhapPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            String autoEmail = intent.getStringExtra("email");
            String autoPassword = intent.getStringExtra("password");

            // Điền email và mật khẩu vào các trường tương ứng
            edtEmail.setText(autoEmail);
            edtPass.setText(autoPassword);
        }
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateForm();
            }
        });
        goToDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DangNhap.this, DangKy.class));
            }
        });
        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startActivity(new Intent(DangNhap.this, QuenMatKhau.class));
            }
        });
        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilEmail.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tilPass.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
    private void validateForm() {
        email = edtEmail.getText().toString().trim();
        pass = edtPass.getText().toString().trim();
        boolean check = false;
        if (email.isEmpty()){
            tilEmail.setError("Vui lòng nhập Email!");
            check = true;
        } else {
            tilEmail.setError(null);
        }
        if (pass.isEmpty()){
            tilPass.setError("Vui lòng nhập mật khẩu!");
            check = true;
        } else {
            tilPass.setError(null);
        }
        if (check) {
            return;
        }
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    Toast.makeText(DangNhap.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DangNhap.this, MainActivity.class));
                    finish();
                } else {
                    Log.w("Main", "Đăng nhập thất bại", task.getException());
                    Toast.makeText(DangNhap.this, "Tài khoản hoặc mật khẩu không chính xác!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}