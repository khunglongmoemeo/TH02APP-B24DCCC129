package com.example.personalexpensemanagement2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private TextView tvError;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvError = findViewById(R.id.tvError);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String username = edtUsername.getText().toString().trim();
            String password = edtPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                tvError.setText("Vui lòng không để trống tên đăng nhập hoặc mật khẩu");
                tvError.setVisibility(View.VISIBLE);
                return;
            }

            // Tài khoản cố định theo yêu cầu
            if (username.equals("admin") && password.equals("123456")) {
                tvError.setVisibility(View.GONE);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Đóng LoginActivity để không quay lại bằng nút Back
            } else {
                tvError.setText("Sai tên đăng nhập hoặc mật khẩu");
                tvError.setVisibility(View.VISIBLE);
            }
        });
    }
}