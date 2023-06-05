package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TacGia;
import com.manhhdc.qltk.Moduls.TaiLieu;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.TaiLieuService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTaiLieu extends AppCompatActivity {

    Button btnOk, btnBack;
    EditText edtmatl, edttentl, edtmathel, edtmatg, edtmanxb, edtnamxb, edtgiabia, edtsoluong;

    TaiLieuService service;

    private byte TYPE = Util.CREATE;

    TaiLieu taiLieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_tailieu);

        service = ApiService.createService(TaiLieuService.class);

        edtmatl = findViewById(R.id.edtmatl);
        edttentl = findViewById(R.id.edttentl);
        edtmathel = findViewById(R.id.edtmatheloai);
        edtmatg = findViewById(R.id.edtmatg);
        edtmanxb = findViewById(R.id.edtmanxb);
        edtnamxb = findViewById(R.id.edtnamxb);
        edtgiabia = findViewById(R.id.edtgiabia);
        edtsoluong = findViewById(R.id.edtsoluong);
        edtmatl.setEnabled(true);
        load();

        btnOk = findViewById(R.id.btnOK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taiLieu = new TaiLieu();
                taiLieu.MATL = edtmatl.getText().toString();
                taiLieu.TENTL = edttentl.getText().toString();
                taiLieu.MATHELOAI = edtmathel.getText().toString();
                taiLieu.MATG = edtmatg.getText().toString();
                taiLieu.MANXB = edtmanxb.getText().toString();
                taiLieu.NAMXB = edtnamxb.getText().toString();
                taiLieu.GIA = Integer.parseInt( edtgiabia.getText().toString());
                taiLieu.SOLUONG = Integer.parseInt(edtsoluong.getText().toString());
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
                tV.setText("SỬA TÀI LIỆU");
                Call<TaiLieu> call = service.get(bundle.getString("id"));
                edtmatl.setEnabled(false);
                call.enqueue(new Callback<TaiLieu>() {
                    @Override
                    public void onResponse(Call<TaiLieu> call, Response<TaiLieu> response) {
                        taiLieu = response.body();
                        if(taiLieu != null){
                            edtmatl.setText(taiLieu.MATL);
                            edttentl.setText(taiLieu.TENTL);
                            edtmathel.setText(taiLieu.MATHELOAI);
                            edtmatg.setText(taiLieu.MATG);
                            edtmanxb.setText(taiLieu.MANXB);
                            edtnamxb.setText(taiLieu.NAMXB);
                            edtgiabia.setText(taiLieu.GIA + "");
                            edtsoluong.setText(taiLieu.SOLUONG + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<TaiLieu> call, Throwable t) {

                    }
                });
            }
        }catch (Exception ex){

        }
    }

    private void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(taiLieu) : service.update(taiLieu);
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
                    Util.Alert(AddTaiLieu.this, "Thông Báo", msg.MSG, onClick, onClick);
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
}