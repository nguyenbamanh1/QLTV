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
import com.manhhdc.qltk.Moduls.TheDangKy;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.adapter.PhieuMuonAdapter;
import com.manhhdc.qltk.adapter.TheDangKyAdapter;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.TheDangKyService;
import com.manhhdc.qltk.views.AddDangKy;
import com.manhhdc.qltk.views.AddKhoa;
import com.manhhdc.qltk.views.AddTheLoai;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_thedangky#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_thedangky extends Fragment {

    public ArrayList<TheDangKy> khoas = new ArrayList<>();

    public TheDangKyAdapter adapter;

    private ListView lView;
    private TheDangKyService service;

    public fragment_thedangky() {
        // Required empty public constructor
    }

    public static fragment_thedangky newInstance() {
        fragment_thedangky fragment = new fragment_thedangky();

        return fragment;
    }



    private void onConnect(){
        service =  ApiService.createService(TheDangKyService.class);
        try{
            Call<ArrayList<TheDangKy>> TheDangKy = service.getAll();
            TheDangKy.enqueue(new Callback<ArrayList<TheDangKy>>() {
                @Override
                public void onResponse(Call<ArrayList<TheDangKy>> call, Response<ArrayList<TheDangKy>> response) {
                    fragment_thedangky.this.onResponse(call, response);
                }
                @Override
                public void onFailure(Call<ArrayList<TheDangKy>> call, Throwable t) {

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void onResponse(Call<ArrayList<TheDangKy>> call, Response<ArrayList<TheDangKy>> response){
        if(adapter == null){
            khoas = response.body();
            adapter = new TheDangKyAdapter(this.getContext(), R.layout.item_thedangky, khoas);
            this.lView.setAdapter(adapter);
        }else{
            khoas.clear();
            khoas.addAll(response.body());
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
        lView = this.getView().findViewById(R.id.lvTheDangKy);
        delBtn = this.getView().findViewById(R.id.btnXoaTDK);
        delBtn.setEnabled(false);


        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i >= 0 && i < khoas.size() ){
                    delBtn.setEnabled(true);
                    saveDG = khoas.get(i);
                }else{
                    delBtn.setEnabled(false);
                }
            }
        });

        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(fragment_thedangky.this.getContext(), AddDangKy.class);
                intent.putExtra("id", khoas.get(i).MADK);
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

        addBtn = getView().findViewById(R.id.btnAddTDK);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_thedangky.this.getContext(), AddDangKy.class);
                startActivityForResult(intent, 10);
            }
        });
        onConnect();
    }

    private TheDangKy saveDG;
    private void removeObj(){
        try{
            Call<Message> call = service.delete(saveDG.MADK);
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
        return inflater.inflate(R.layout.fragment_thedangky, container, false);
    }
}