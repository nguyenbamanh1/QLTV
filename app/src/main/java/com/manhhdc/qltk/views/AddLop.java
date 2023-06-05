package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.manhhdc.qltk.Moduls.Lop;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.LopService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLop extends AppCompatActivity {

    Button btnOk, btnBack;

    EditText edtMaLop, edtmak, edttenlop;

    LopService service;

    private byte TYPE = Util.CREATE;

    Lop lopSv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.them_lop);

        service = ApiService.createService(LopService.class);

        edtMaLop = findViewById(R.id.edtmalop);
        edtmak = findViewById(R.id.edtmak);
        edttenlop = findViewById(R.id.edttenlop);
        edtMaLop.setEnabled(true);
        load();
        btnOk = findViewById(R.id.btnOK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lopSv = new Lop();
                lopSv.ID = edtMaLop.getText().toString();
                lopSv.MAKHOA = edtmak.getText().toString();
                lopSv.NAME = edttenlop.getText().toString();
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
        try{
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if(bundle != null && bundle.containsKey("id")){
                TYPE = Util.UPDATE;
                TextView tV = findViewById(R.id.titleView);
                tV.setText("SỬA LỚP");
                Call<Lop> call = service.get(bundle.getString("id"));
                edtMaLop.setEnabled(false);
                call.enqueue(new Callback<Lop>() {
                    @Override
                    public void onResponse(Call<Lop> call, Response<Lop> response) {
                        lopSv = response.body();
                        if(lopSv != null){
                            edtMaLop.setText(lopSv.ID);
                            edtmak.setText(lopSv.MAKHOA);
                            edttenlop.setText(lopSv.NAME);
                        }
                    }

                    @Override
                    public void onFailure(Call<Lop> call, Throwable t) {

                    }
                });
            }
        }catch (Exception ex){

        }
    }

    private void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(lopSv) : service.update(lopSv);

            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Message msg = response.body();
                    Util.Alert(AddLop.this, "Thông Báo", msg.MSG, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(msg.TYPE == 0){
                                setResult(10);
                                finish();
                            }
                        }
                    }, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(msg.TYPE == 0){
                                setResult(10);
                                finish();
                            }
                        }
                    });
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception ex){

        }
    }
}