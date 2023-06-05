package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.manhhdc.qltk.Moduls.DocGia;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.DocGiaService;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDocGia extends AppCompatActivity {


    Button btnOk, btnBack;
    DocGia docGia = new DocGia();
    DocGiaService service;
    EditText edtMaDG, edtMaDK, edtNgayCap, edtHsd, edtTinhTrang;

    private byte TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_docgia);
        service = ApiService.createService(DocGiaService.class);

        edtHsd = findViewById(R.id.edthsd);
        edtMaDG = findViewById(R.id.edtmadg);
        edtMaDK = findViewById(R.id.edtmadk);
        edtNgayCap = findViewById(R.id.edtngaycap);
        edtTinhTrang = findViewById(R.id.edttinhtrang);
        edtMaDG.setEnabled(true);
        TYPE = Util.CREATE;

        Intent intent = getIntent();
        if(intent != null){
            Bundle bundle = intent.getExtras();
            if(bundle != null && bundle.containsKey("id")){
                TextView tV = findViewById(R.id.titleView);
                tV.setText("SỬA ĐỘC GIẢ");
                String id = bundle.getString("id");
                TYPE = Util.UPDATE;
                try {
                    Call<DocGia> call = service.get(id);
                    call.enqueue(new Callback<DocGia>() {
                        @Override
                        public void onResponse(Call<DocGia> call, Response<DocGia> response) {
                            docGia = response.body();
                            edtHsd.setText(docGia.HSD);
                            edtMaDG.setText(docGia.MADG);
                            edtMaDG.setEnabled(false);
                            edtMaDK.setText(docGia.MADK);
                            edtNgayCap.setText(docGia.NGAYDK);
                            edtTinhTrang.setText(docGia.TINHTRANG);
                        }

                        @Override
                        public void onFailure(Call<DocGia> call, Throwable t) {

                        }
                    });
                }catch (Exception exception){
                }
            }

        }

        edtHsd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Util.DatePick(AddDocGia.this, edtHsd.getText().toString(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            edtHsd.setText(Util.getDate(i, i1, i2));
                        }
                    });
                }
            }
        });

        edtNgayCap.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Util.DatePick(AddDocGia.this, edtNgayCap.getText().toString(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            edtNgayCap.setText(Util.getDate(i, i1, i2));
                        }
                    });
                }
            }
        });


        btnOk = findViewById(R.id.btnOK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                docGia.HSD = edtHsd.getText().toString();
                docGia.MADK = edtMaDK.getText().toString();
                docGia.NGAYDK = edtNgayCap.getText().toString();
                docGia.TINHTRANG = edtTinhTrang.getText().toString();
                if(TYPE == Util.CREATE){
                    create();
                }else if(TYPE == Util.UPDATE){
                    update();
                }
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

    private void create(){
        try {
            Call<Message> call = service.create(docGia);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Message msg = response.body();
                    DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setResult(10);
                            finish();
                        }
                    };
                    Util.Alert(AddDocGia.this, "Thông Báo",  msg.MSG, onClick, onClick);

                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception exception){
        }
    }

    private void update(){
        try {
            Call<Message> call = service.update(docGia);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Message msg = response.body();

                    DialogInterface.OnClickListener onClick = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            setResult(10);
                            finish();
                        }
                    };
                    Util.Alert(AddDocGia.this, "Thông Báo",  msg.MSG, onClick, onClick);
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception exception){
        }
    }
}