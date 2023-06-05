package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.ThuThu;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.Moduls.XuLy;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.XuLyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddViPham extends AppCompatActivity {

    Button btnBack, btnOk;

    EditText edtMaMT, edtHinhThuc, edtNgayXL, edtNgayMT, edtTien;

    XuLyService service;

    byte TYPE = Util.CREATE;

    XuLy viPham;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_xulyvipham);
        service = ApiService.createService(XuLyService.class);

        edtMaMT = findViewById(R.id.edtmamt);

        edtHinhThuc = findViewById(R.id.edthinhthuc);

        edtNgayXL = findViewById(R.id.edtngayxl);

        edtNgayMT = findViewById(R.id.edtngaymt);

        edtTien = findViewById(R.id.edttienphat);

        edtNgayXL.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Util.DatePick(AddViPham.this, edtNgayXL.getText().toString(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            edtNgayXL.setText(Util.getDate(i, i1, i2));
                        }
                    });
                }
            }
        });

        edtNgayMT.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Util.DatePick(AddViPham.this, edtNgayMT.getText().toString(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            edtNgayMT.setText(Util.getDate(i, i1, i2));
                        }
                    });
                }
            }
        });
        load();

        btnOk = findViewById(R.id.btnOK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viPham = new XuLy();
                viPham.MAMT = edtMaMT.getText().toString();
                viPham.HINHTHUCXL = edtHinhThuc.getText().toString();
                viPham.NGAYXL = edtNgayXL.getText().toString();
                viPham.NGAYMOTHE = edtNgayMT.getText().toString();

                viPham.PHAT = Integer.parseInt(edtTien.getText().toString());

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
                tV.setText("SỬA XỬ LÝ VI PHẠM");
                Call<XuLy> call = service.get(bundle.getString("id"));
                edtMaMT.setEnabled(false);
                call.enqueue(new Callback<XuLy>() {
                    @Override
                    public void onResponse(Call<XuLy> call, Response<XuLy> response) {
                        viPham = response.body();
                        if(viPham != null){
                            edtMaMT.setText(viPham.MAMT);
                            edtHinhThuc.setText(viPham.HINHTHUCXL);
                            edtNgayXL.setText(viPham.NGAYXL);
                            edtNgayMT.setText(viPham.NGAYMOTHE);
                            edtTien.setText(viPham.PHAT + "");
                        }
                    }

                    @Override
                    public void onFailure(Call<XuLy> call, Throwable t) {

                    }
                });
            }
        }catch (Exception ex){

        }
    }

    private void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(viPham) : service.update(viPham);
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
                    Util.Alert(AddViPham.this, "Thông Báo", msg.MSG, onClick, onClick);
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
}
