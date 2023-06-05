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
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.PhieuMuon;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.Moduls.XuLy;
import com.manhhdc.qltk.adapter.TheLoaiAdapter;
import com.manhhdc.qltk.adapter.XuLyAdapter;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.XuLyService;
import com.manhhdc.qltk.views.AddKhoa;
import com.manhhdc.qltk.views.AddViPham;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_vipham#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_vipham extends Fragment {

    public ArrayList<XuLy> TheDangKys = new ArrayList<>();

    public XuLyAdapter adapter;

    private ListView lView;
    private XuLyService service;
    public fragment_vipham() {
        // Required empty public constructor
    }

    public static fragment_vipham newInstance(String param1, String param2) {
        fragment_vipham fragment = new fragment_vipham();
        return fragment;
    }

    private void onConnect(){
        service =  ApiService.createService(XuLyService.class);
        try{
            Call<ArrayList<XuLy>> TheDangKy = service.getAll();
            TheDangKy.enqueue(new Callback<ArrayList<XuLy>>() {
                @Override
                public void onResponse(Call<ArrayList<XuLy>> call, Response<ArrayList<XuLy>> response) {
                    fragment_vipham.this.onResponse(call, response);
                }
                @Override
                public void onFailure(Call<ArrayList<XuLy>> call, Throwable t) {

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    private void onResponse(Call<ArrayList<XuLy>> call, Response<ArrayList<XuLy>> response){
        if(adapter == null){
            TheDangKys = response.body();
            adapter = new XuLyAdapter(this.getContext(), R.layout.item_xulyvipham, TheDangKys);
            this.lView.setAdapter(adapter);
        }else{
            TheDangKys.clear();
            TheDangKys.addAll(response.body());
            adapter.notifyDataSetChanged();
        }
    }
    FloatingActionButton delBtn, addBtn;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(service != null){
            onConnect();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lView = this.getView().findViewById(R.id.lvVP);
        delBtn = this.getView().findViewById(R.id.btnXoaViPham);
        delBtn.setEnabled(false);


        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i >= 0 && i < TheDangKys.size() ){
                    delBtn.setEnabled(true);
                    saveDG = TheDangKys.get(i);
                }else{
                    delBtn.setEnabled(false);
                }
            }
        });

        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(fragment_vipham.this.getContext(), AddViPham.class);
                intent.putExtra("id", TheDangKys.get(i).MAMT);
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

        addBtn = getView().findViewById(R.id.btnAddViPham);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_vipham.this.getContext(), AddViPham.class);
                startActivityForResult(intent, 10);
            }
        });
        onConnect();
    }

    private XuLy saveDG;
    private void removeObj(){
        try{
            Call<Message> call = service.delete(saveDG.MAMT);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Log.i("error", response.toString());
                    Message msg = response.body();
                    Util.Alert(getView().getContext(), "Thông báo", msg.MSG);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 10){
            onConnect();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_vipham, container, false);
    }
}