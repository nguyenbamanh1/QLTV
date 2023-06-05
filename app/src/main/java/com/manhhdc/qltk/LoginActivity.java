package com.manhhdc.qltk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TaiKhoan;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.Moduls.XuLy;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.TaiKhoanService;
import com.manhhdc.qltk.service.XuLyService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText userName, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button btnLogin = findViewById(R.id.btnLogin);

        userName = findViewById(R.id.edtUserName);
        password = findViewById(R.id.edtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        Log.i("back", "backback");
    }


    public void login(){
        TaiKhoanService service =  ApiService.createService(TaiKhoanService.class);
        try{
            TaiKhoan tk = new TaiKhoan();
            tk.TAIKHOAN = userName.getText().toString();
            tk.MATKHAU = password.getText().toString();
            Call<Message> TheDangKy = service.login(tk);
            TheDangKy.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Log.i("error", response.toString());
                    Message msg = response.body();
                    if(msg != null && msg.TYPE == 0){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    //Toast.makeText(LoginActivity.this, msg.MSG, Toast.LENGTH_LONG).show();
                    Util.Alert(LoginActivity.this, "Thông báo", msg.MSG);
                }
                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }


}