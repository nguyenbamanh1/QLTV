package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.manhhdc.qltk.Moduls.Khoa;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.KhoaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddKhoa extends AppCompatActivity {

    Button btnOk, btnBack;
    EditText edtMak, edtTenk, edtDiachi;

    KhoaService service;
    Khoa khoaSave;
    private byte TYPE = Util.CREATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_khoa);

        service = ApiService.createService(KhoaService.class);

        edtMak = findViewById(R.id.edtmakhoa);

        edtTenk = findViewById(R.id.edttenk);

        edtDiachi = findViewById(R.id.edtdiachi);

        edtMak.setEnabled(true);

        loadKhoa();

        btnOk = findViewById(R.id.btnOK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                khoaSave = new Khoa();
                khoaSave.ID = edtMak.getText().toString();
                khoaSave.NAME = edtTenk.getText().toString();
                khoaSave.DIACHI = edtDiachi.getText().toString();
                actionOk();
            }
        });


        btnBack = findViewById(R.id.btnQuayVe);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void loadKhoa(){
        try{

            Intent intent = getIntent();

            Bundle bundle = intent.getExtras();
            if(bundle != null && bundle.containsKey("id")){
                TextView tV = findViewById(R.id.titleView);
                tV.setText("SỬA KHOA");
                Call<Khoa> call = service.get(bundle.getString("id"));
                edtMak.setEnabled(false);
                TYPE = Util.UPDATE;
                call.enqueue(new Callback<Khoa>() {
                    @Override
                    public void onResponse(Call<Khoa> call, Response<Khoa> response) {
                        khoaSave = response.body();
                        if(khoaSave != null){
                            edtMak.setText(khoaSave.ID);
                            edtTenk.setText(khoaSave.NAME);
                            edtDiachi.setText(khoaSave.DIACHI);
                        }
                    }

                    @Override
                    public void onFailure(Call<Khoa> call, Throwable t) {

                    }
                });
            }



        }catch (Exception ex){

        }
    }

    public void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(khoaSave) : service.update(khoaSave);

            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Message msg = response.body();
                    DialogInterface.OnClickListener onClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(msg.TYPE == 0){
                                setResult(10);
                                finish();
                            }
                        }
                    };
                    Util.Alert(AddKhoa.this, "Thông Báo", msg.MSG, onClickListener, onClickListener);

                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception ex){

        }
    }
}