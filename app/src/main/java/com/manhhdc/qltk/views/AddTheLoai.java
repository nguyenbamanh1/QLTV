package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.TheLoaiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTheLoai extends AppCompatActivity {

    Button btnBack, btnOk;
    EditText edtMaTheLoai, edtTenTheLoai;
    TheLoaiService service;
    byte TYPE = Util.CREATE;
    Entity theLoai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.them_theloai);
        service = ApiService.createService(TheLoaiService.class);
        edtMaTheLoai = findViewById(R.id.edtmatheloai);
        edtTenTheLoai = findViewById(R.id.edttentheloai);
        edtMaTheLoai.setEnabled(true);
        load();
        btnOk = findViewById(R.id.btnOK);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theLoai = new Entity();
                theLoai.ID = edtMaTheLoai.getText().toString();
                theLoai.NAME = edtTenTheLoai.getText().toString();
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
                tV.setText("SỬA THỂ LOẠI");
                Call<Entity> call = service.get(bundle.getString("id"));
                edtMaTheLoai.setEnabled(false);
                call.enqueue(new Callback<Entity>() {
                    @Override
                    public void onResponse(Call<Entity> call, Response<Entity> response) {
                        theLoai = response.body();
                        if(theLoai != null){
                            edtMaTheLoai.setText(theLoai.ID);
                            edtTenTheLoai.setText(theLoai.NAME);
                        }
                    }

                    @Override
                    public void onFailure(Call<Entity> call, Throwable t) {

                    }
                });
            }
        }catch (Exception ex){

        }
    }

    private void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(theLoai) : service.update(theLoai);

            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Message msg = response.body();
                    Util.Alert(AddTheLoai.this, "Thông Báo", msg.MSG, new DialogInterface.OnClickListener() {
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