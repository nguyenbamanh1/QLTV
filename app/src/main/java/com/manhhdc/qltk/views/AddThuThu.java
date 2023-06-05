package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TacGia;
import com.manhhdc.qltk.Moduls.ThuThu;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.adapter.ThuThuAdapter;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.ThuThuService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddThuThu extends AppCompatActivity {

    Button btnOk, btnBack;

    EditText edtMatt, edtTenTT, edtSdt, edtDiaChi, edtEmail;
    Spinner edtGioiTinh;
    ArrayAdapter<String> adapter;

    ThuThuService service;
    ThuThu thuThu;
    byte TYPE = Util.CREATE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.them_thuthu);

        service = ApiService.createService(ThuThuService.class);

        adapter = new ArrayAdapter<>(this, R.layout.list_drop_down, getResources().getStringArray(R.array.gioitinh));

        edtGioiTinh = findViewById(R.id.edtgioitinhtt);

        edtGioiTinh.setAdapter(adapter);

        edtMatt = findViewById(R.id.edtmatt);

        edtTenTT = findViewById(R.id.edttentt);
        
        edtSdt = findViewById(R.id.edtsdt);

        edtDiaChi = findViewById(R.id.edtdiachi);

        edtEmail = findViewById(R.id.edtemail);

        load();

        btnOk = findViewById(R.id.btnOK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                thuThu = new ThuThu();
                thuThu.MATT = edtMatt.getText().toString();
                thuThu.TENTT = edtTenTT.getText().toString();
                thuThu.GT = (byte) edtGioiTinh.getSelectedItemPosition();
                thuThu.SDT = edtSdt.getText().toString();
                thuThu.DIACHI = edtDiaChi.getText().toString();
                thuThu.EMAIL = edtEmail.getText().toString();
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
                tV.setText("SỬA THỦ THƯ");
                Call<ThuThu> call = service.get(bundle.getString("id"));
                edtMatt.setEnabled(false);
                call.enqueue(new Callback<ThuThu>() {
                    @Override
                    public void onResponse(Call<ThuThu> call, Response<ThuThu> response) {
                        thuThu = response.body();
                        if(thuThu != null){
                            edtMatt.setText(thuThu.MATT);
                            edtTenTT.setText(thuThu.TENTT);
                            edtSdt.setText(thuThu.SDT);
                            edtDiaChi.setText(thuThu.DIACHI);
                            edtEmail.setText(thuThu.EMAIL);
                            edtGioiTinh.setSelection(thuThu.GT);
                        }
                    }

                    @Override
                    public void onFailure(Call<ThuThu> call, Throwable t) {

                    }
                });
            }
        }catch (Exception ex){

        }
    }

    private void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(thuThu) : service.update(thuThu);
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
                    Util.Alert(AddThuThu.this, "Thông Báo", msg.MSG, onClick, onClick);
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
}