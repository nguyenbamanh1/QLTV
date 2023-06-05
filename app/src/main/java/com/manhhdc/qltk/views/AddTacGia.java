package com.manhhdc.qltk.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TacGia;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.R;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.TacGiaService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddTacGia extends AppCompatActivity {

    ArrayAdapter<String> adapter;

    Button btnOk, btnBack;

    Spinner edtgt;
    EditText edtmaTg, edtTen, edtquequan;

    byte TYPE = Util.CREATE;

    TacGia tacGia;

    TacGiaService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.them_tacgia);

        service = ApiService.createService(TacGiaService.class);

        adapter = new ArrayAdapter<>(this, R.layout.list_drop_down, getResources().getStringArray(R.array.gioitinh));
        edtgt = findViewById(R.id.edtgioitinhtg);

        edtgt.setAdapter(adapter);

        edtmaTg = findViewById(R.id.edtmatacgia);

        edtTen = findViewById(R.id.edttentacgia);

        edtquequan = findViewById(R.id.edtquequan);
        edtmaTg.setEnabled(true);
        load();

        btnOk = findViewById(R.id.btnOK);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tacGia = new TacGia();
                tacGia.ID = edtmaTg.getText().toString();
                tacGia.NAME = edtTen.getText().toString();
                tacGia.GT = (byte) edtgt.getSelectedItemPosition();
                tacGia.QUEQUAN = edtquequan.getText().toString();
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
                tV.setText("SỬA TÁC GIẢ");
                Call<TacGia> call = service.get(bundle.getString("id"));
                edtmaTg.setEnabled(false);
                call.enqueue(new Callback<TacGia>() {
                    @Override
                    public void onResponse(Call<TacGia> call, Response<TacGia> response) {
                        tacGia = response.body();
                        if(tacGia != null){
                            edtgt.setSelection(tacGia.GT);
                            edtTen.setText(tacGia.NAME);
                            edtquequan.setText(tacGia.QUEQUAN);
                            edtmaTg.setText(tacGia.ID);
                        }
                    }

                    @Override
                    public void onFailure(Call<TacGia> call, Throwable t) {

                    }
                });
            }
        }catch (Exception ex){

        }
    }

    private void actionOk(){
        try {
            Call<Message> call = TYPE == Util.CREATE ? service.create(tacGia) : service.update(tacGia);
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
                    Util.Alert(AddTacGia.this, "Thông Báo", msg.MSG, onClick, onClick);
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception e){

        }
    }
}