package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.NXB;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.NXBService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddNXB extends AppCompatActivity {

    Button btnOk, btnBack;

    EditText edtmanhaxb, edttennxb, edtdiachi, edtsdt;
    private byte TYPE = Util.CREATE;
    NXBService service;

    NXB nxbSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.them_nhaxuatban);

        service = ApiService.createService(NXBService.class);

        edtmanhaxb = findViewById(R.id.edtmanhaxb);

        edttennxb = findViewById(R.id.edttennxb);

        edtdiachi = findViewById(R.id.edtdiachi);
        edtmanhaxb.setEnabled(true);



        edtsdt = findViewById(R.id.edtsdt);
        load();
        btnOk = findViewById(R.id.btnOK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nxbSave = new NXB();
                nxbSave.ID = edtmanhaxb.getText().toString();
                nxbSave.NAME = edttennxb.getText().toString();
                nxbSave.DIACHI = edtdiachi.getText().toString();
                nxbSave.SDT = edtsdt.getText().toString();
                actionOK();
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

    private void load(){
        try {
            Bundle bundle = getIntent().getExtras();
            if(bundle != null && bundle.containsKey("id")){
                TYPE = Util.UPDATE;
                TextView tV = findViewById(R.id.titleView);
                tV.setText("SỬA NHÀ XUẤT BẢN");
                Call<NXB> call = service.get(bundle.getString("id"));
                edtmanhaxb.setEnabled(false);
                call.enqueue(new Callback<NXB>() {
                    @Override
                    public void onResponse(Call<NXB> call, Response<NXB> response) {
                        nxbSave = response.body();
                        if(nxbSave != null){
                            edtmanhaxb.setText(nxbSave.ID);
                            edttennxb.setText(nxbSave.NAME);
                            edtdiachi.setText(nxbSave.DIACHI);
                            edtsdt.setText(nxbSave.SDT);
                        }
                    }

                    @Override
                    public void onFailure(Call<NXB> call, Throwable t) {

                    }
                });
            }
        }catch (Exception ex){

        }
    }
    private void actionOK(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(nxbSave) : service.update(nxbSave);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Message msg = response.body();
                    Util.Alert(AddNXB.this, "Thông Báo", msg.MSG, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(msg.TYPE == 0){
                                setResult(10);
                                finish();
                            }
                        }
                    },new DialogInterface.OnClickListener() {
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