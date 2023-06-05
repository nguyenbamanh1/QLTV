package com.manhhdc.qltk.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TacGia;
import com.manhhdc.qltk.Moduls.TaiKhoan;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.TaiKhoanService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaiKhoan extends AppCompatActivity {

    Button btnOk, btnBack;

    byte TYPE = Util.CREATE;

    TaiKhoan taikhoan;

    EditText edtTK, edtMTT, edtMK;

    TaiKhoanService service;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_taikhoa);
        service = ApiService.createService(TaiKhoanService.class);

        edtMK = findViewById(R.id.edtmk);
        edtMTT = findViewById(R.id.edtmatt);
        edtTK = findViewById(R.id.edttk);
        load();
        btnOk = findViewById(R.id.btnOK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taikhoan = new TaiKhoan();
                taikhoan.MATT = edtMTT.getText().toString();
                taikhoan.TAIKHOAN = edtTK.getText().toString();
                taikhoan.MATKHAU = edtMK.getText().toString();
                actionOk();
            }
        });

        btnBack = findViewById(R.id.btnQuayVe);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }


    private void load(){
        try {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null && bundle.containsKey("id")){
                TYPE = Util.UPDATE;
                TextView tV = findViewById(R.id.titleView);
                tV.setText("SỬA TÀI KHOẢN");
                Call<TaiKhoan> call = service.get(bundle.getString("id"));
                edtMTT.setEnabled(false);
                edtTK.setEnabled(false);
                call.enqueue(new Callback<TaiKhoan>() {
                    @Override
                    public void onResponse(Call<TaiKhoan> call, Response<TaiKhoan> response) {
                        taikhoan = response.body();
                        if(taikhoan != null){
                            edtMTT.setText(taikhoan.MATT);
                            edtTK.setText(taikhoan.TAIKHOAN);
                            edtMK.setText(taikhoan.MATKHAU);
                        }
                    }

                    @Override
                    public void onFailure(Call<TaiKhoan> call, Throwable t) {

                    }
                });
            }
        }catch (Exception ex){

        }
    }

    private void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(taikhoan) : service.update(taikhoan);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Message msg = response.body();
                    DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(msg.TYPE == 0){
                                setResult(10);
                                finish();
                            }
                        }
                    };
                    Util.Alert(AddTaiKhoan.this, "Thông Báo", msg.MSG, onClick, onClick);
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
}
