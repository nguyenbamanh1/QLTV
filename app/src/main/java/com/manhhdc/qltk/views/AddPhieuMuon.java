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
import com.manhhdc.qltk.Moduls.PhieuMuon;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.PhieuMuonService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPhieuMuon extends AppCompatActivity {

    Button btnOk, btnBack;

    EditText edtmamt, edtmatt, edtmadg, edtmatl, edtHinhThuc, edtNgayMuon, edtNgayTra, edtGia, edtGhichu, edtTinhTrang;

    PhieuMuon phieuMuon;

    PhieuMuonService service;

    private byte TYPE = Util.CREATE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.them_phieumuon);

        service = ApiService.createService(PhieuMuonService.class);

        edtmamt = findViewById(R.id.edtmamt);
        edtmatt = findViewById(R.id.edtmatt);
        edtmadg = findViewById(R.id.edtmadg);
        edtmatl = findViewById(R.id.edtmatl);
        edtHinhThuc = findViewById(R.id.edthinhthuc);
        edtNgayMuon = findViewById(R.id.edtngaymuon);
        edtTinhTrang = findViewById(R.id.edttinhtrang);
        edtNgayTra = findViewById(R.id.edtngaytra);
        edtGia = findViewById(R.id.edtgia);
        edtGhichu = findViewById(R.id.edtghichu);
        edtmamt.setEnabled(true);
        edtNgayTra.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Util.DatePick(AddPhieuMuon.this, edtNgayTra.getText().toString(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            edtNgayTra.setText(Util.getDate(i, i1, i2));
                        }
                    });
                }
            }
        });

        edtNgayMuon.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b){
                    Util.DatePick(AddPhieuMuon.this, edtNgayMuon.getText().toString(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                            edtNgayMuon.setText(Util.getDate(i, i1, i2));
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
                phieuMuon = new PhieuMuon();

                phieuMuon.MAMT = edtmamt.getText().toString();
                phieuMuon.GHICHU = edtGhichu.getText().toString();
                phieuMuon.GIA = Integer.parseInt(edtGia.getText().toString());
                phieuMuon.HINHTHUC = edtHinhThuc.getText().toString();
                phieuMuon.TINHTRANG = edtTinhTrang.getText().toString();
                phieuMuon.MADG = edtmadg.getText().toString();
                phieuMuon.MATL = edtmatl.getText().toString();
                phieuMuon.MATT = edtmatt.getText().toString();
                phieuMuon.NGAYTRA = edtNgayTra.getText().toString();
                phieuMuon.NGAYMUON = edtNgayMuon.getText().toString();

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
                tV.setText("SỬA PHIẾU MƯỢN");
                Call<PhieuMuon> call = service.get(bundle.getString("id"));
                edtmamt.setEnabled(false);
                call.enqueue(new Callback<PhieuMuon>() {
                    @Override
                    public void onResponse(Call<PhieuMuon> call, Response<PhieuMuon> response) {
                        phieuMuon = response.body();
                        if(phieuMuon != null){
                            edtGhichu.setText(phieuMuon.GHICHU);
                            edtHinhThuc.setText(phieuMuon.HINHTHUC);
                            edtGia.setText(phieuMuon.GIA + "");
                            edtmamt.setText(phieuMuon.MAMT);
                            edtmatl.setText(phieuMuon.MATL);
                            edtmatt.setText(phieuMuon.MATT);
                            edtmadg.setText(phieuMuon.MADG);
                            edtNgayMuon.setText(phieuMuon.NGAYMUON);
                            edtNgayTra.setText(phieuMuon.NGAYTRA);
                            edtTinhTrang.setText(phieuMuon.TINHTRANG);

                        }
                    }

                    @Override
                    public void onFailure(Call<PhieuMuon> call, Throwable t) {

                    }
                });
            }
        }catch (Exception exception){

        }
    }

    private void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(phieuMuon) : service.update(phieuMuon);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Message msg = response .body();
                    DialogInterface.OnClickListener action = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(msg.TYPE == 0){
                                setResult(10);
                                finish();
                            }
                        }
                    };
                    Util.Alert(AddPhieuMuon.this, "Thông Báo", msg.MSG, action, action);
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception ex){

        }
    }
}