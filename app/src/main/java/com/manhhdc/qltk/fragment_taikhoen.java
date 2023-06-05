package com.manhhdc.qltk;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.manhhdc.qltk.Moduls.DocGia;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TaiKhoan;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.adapter.DocGiaAdapter;
import com.manhhdc.qltk.adapter.TaiKhoanAdapter;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.TaiKhoanService;
import com.manhhdc.qltk.views.AddDocGia;
import com.manhhdc.qltk.views.AddTaiKhoan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_taikhoen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_taikhoen extends Fragment {

    public ArrayList<TaiKhoan> taiKhoans = new ArrayList<>();

    public TaiKhoanAdapter adapter;

    private FloatingActionButton delBtn , btnAdd;

    private ListView lView;
    private TaiKhoanService service;

    private TaiKhoan saveDG;

    public fragment_taikhoen() {
        // Required empty public constructor
    }

    public static fragment_taikhoen newInstance() {
        fragment_taikhoen fragment = new fragment_taikhoen();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(service != null){
            onConnect();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_taikhoen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lView = this.getView().findViewById(R.id.lvTK);
        delBtn = this.getView().findViewById(R.id.btnXoaTK);
        delBtn.setEnabled(false);

        service =  ApiService.createService(TaiKhoanService.class);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i >= 0 && i < taiKhoans.size() ){
                    delBtn.setEnabled(true);
                    fragment_taikhoen.this.saveDG = taiKhoans.get(i);
                }else{
                    delBtn.setEnabled(false);
                }
            }
        });

        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int i, long l) {
                Intent intent = new Intent(view.getContext(), AddTaiKhoan.class);
                intent.putExtra("id", taiKhoans.get(i).TAIKHOAN);
                startActivityForResult(intent, 10);

                return true;
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveDG != null){
                    removeObj();
                }
            }
        });

        btnAdd = getView().findViewById(R.id.btnAddTK);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_taikhoen.this.getContext(), AddTaiKhoan.class);
                startActivityForResult(intent, 10);
            }
        });

        onConnect();
    }

    private void onConnect(){

        try{
            Call<ArrayList<TaiKhoan>> docgia = service.getAll();
            docgia.enqueue(new Callback<ArrayList<TaiKhoan>>() {
                @Override
                public void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response) {
                    fragment_taikhoen.this.onResponse(call, response);
                }
                @Override
                public void onFailure(Call<ArrayList<TaiKhoan>> call, Throwable t) {

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void removeObj(){
        try{
            Call<Message> call = service.delete(saveDG.TAIKHOAN);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Log.i("error", response.toString());
                    Message msg = response.body();
                    Util.Alert(fragment_taikhoen.this.getView().getContext(), "Thông báo", msg.MSG);
                    if(msg.TYPE == 0){
                        onConnect();
                    }
                }

                @Override
                public void onFailure(Call<Message> call, Throwable t) {

                }
            });
        }catch (Exception ex){

        }
    }

    private void onResponse(Call<ArrayList<TaiKhoan>> call, Response<ArrayList<TaiKhoan>> response){
        taiKhoans.clear();
        taiKhoans.addAll(response.body());
        if(this.lView.getAdapter() == null){
            adapter = new TaiKhoanAdapter(this.getContext(), R.layout.item_taikhoan, taiKhoans);
            this.lView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }

    }
}