package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.manhhdc.qltk.Moduls.Lop;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TheDangKy;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.TheDangKyService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDangKy extends AppCompatActivity {

    ArrayAdapter<String> adapter, adapter2;

    EditText edtMaDK, edtMAL, edtHoTen, edtNamSinh, edtKhoaHoc, edtsdt, edtEmail, edtLoaiDK, edtNgayDK, edtLePhi;
    Spinner edtGT, edtChucDanh;
    byte TYPE = Util.CREATE;
    TheDangKyService service;
    TheDangKy theDangKy;
    Button btnOk, btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.them_thedangky);

        service = ApiService.createService(TheDangKyService.class);

        adapter = new ArrayAdapter<>(this, R.layout.list_drop_down, getResources().getStringArray(R.array.gioitinh));
        edtGT = findViewById(R.id.edtgioitinh);
        edtGT.setAdapter(adapter);

        edtMaDK = findViewById(R.id.edtmadk);
        edtMAL = findViewById(R.id.edtmal);
        edtHoTen = findViewById(R.id.edthoten);
        edtNamSinh = findViewById(R.id.edtnamsinh);
        edtChucDanh = findViewById(R.id.edtchucdanh);

        adapter2 = new ArrayAdapter<>(this, R.layout.list_drop_down, getResources().getStringArray(R.array.chucvu));
        edtChucDanh.setAdapter(adapter2);

        edtKhoaHoc = findViewById(R.id.edtkhoahoc);
        edtsdt = findViewById(R.id.edtsdt);
        edtEmail = findViewById(R.id.edtemail);
        edtLoaiDK = findViewById(R.id.edtloaidk);
        edtNgayDK = findViewById(R.id.edtngaydk);
        edtLePhi = findViewById(R.id.edtlephi);

        edtNgayDK.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Util.DatePick(AddDangKy.this, edtNgayDK.getText().toString(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            edtNgayDK.setText(Util.getDate(i, i1, i2));
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

                theDangKy = new TheDangKy();
                theDangKy.MADK = edtMaDK.getText().toString();
                theDangKy.MALOP = edtMAL.getText().toString();
                theDangKy.HOTEN = edtHoTen.getText().toString();
                theDangKy.NAMSINH = edtNamSinh.getText().toString();
                theDangKy.GT = (byte) edtGT.getSelectedItemPosition();
                theDangKy.CHUCDANH = (String) edtChucDanh.getSelectedItem();

                theDangKy.KHOAHOC = edtKhoaHoc.getText().toString();

                theDangKy.SDT = edtsdt.getText().toString();
                theDangKy.EMAIL = edtEmail.getText().toString();
                theDangKy.LOAIDK = edtLoaiDK.getText().toString();
                theDangKy.NGAYDK = edtNgayDK.getText().toString();

                theDangKy.LEPHI = Integer.parseInt(edtLePhi.getText().toString());

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
                tV.setText("SỬA ĐĂNG KÝ");
                Call<TheDangKy> call = service.get(bundle.getString("id"));
                edtMaDK.setEnabled(false);
                call.enqueue(new Callback<TheDangKy>() {
                    @Override
                    public void onResponse(Call<TheDangKy> call, Response<TheDangKy> response) {
                        theDangKy = response.body();
                        if(theDangKy != null){
                            edtMaDK.setText(theDangKy.MADK);
                            edtMAL.setText(theDangKy.MALOP);
                            edtHoTen.setText(theDangKy.HOTEN);
                            edtNamSinh.setText(theDangKy.NAMSINH);
                            edtChucDanh.setSelection(adapter2.getPosition(theDangKy.CHUCDANH));
                            edtKhoaHoc.setText(theDangKy.KHOAHOC);
                            edtsdt.setText(theDangKy.SDT);
                            edtEmail.setText(theDangKy.MADK);
                            edtLoaiDK.setText(theDangKy.LOAIDK);
                            edtNgayDK.setText(theDangKy.NGAYDK);
                            edtLePhi.setText(theDangKy.LEPHI + "");
                            edtGT.setSelection(theDangKy.GT);
                        }
                    }

                    @Override
                    public void onFailure(Call<TheDangKy> call, Throwable t) {

                    }
                });
            }
        }catch (Exception ex){

        }
    }

    private void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(theDangKy) : service.update(theDangKy);

            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Message msg = response.body();
                    Util.Alert(AddDangKy.this, "Thông Báo", msg.MSG, new DialogInterface.OnClickListener() {
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