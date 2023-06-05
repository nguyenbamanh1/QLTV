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
import com.manhhdc.qltk.Moduls.TaiLieu;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.adapter.PhieuMuonAdapter;
import com.manhhdc.qltk.adapter.TaiLieuAdapter;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.TaiLieuService;
import com.manhhdc.qltk.views.AddKhoa;
import com.manhhdc.qltk.views.AddTaiLieu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_tailieu#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_tailieu extends Fragment {

    public ArrayList<TaiLieu> khoas = new ArrayList<>();

    public TaiLieuAdapter adapter;

    private ListView lView;
    private  TaiLieuService service;
    
    
    public fragment_tailieu() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static fragment_tailieu newInstance() {
        fragment_tailieu fragment = new fragment_tailieu();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(service != null){
            onConnect();
        }
    }



    private void onConnect(){
        service =  ApiService.createService(TaiLieuService.class);
        try{
            Call<ArrayList<TaiLieu>> TaiLieu = service.getAll();
            TaiLieu.enqueue(new Callback<ArrayList<TaiLieu>>() {
                @Override
                public void onResponse(Call<ArrayList<TaiLieu>> call, Response<ArrayList<TaiLieu>> response) {
                    fragment_tailieu.this.onResponse(call, response);
                }
                @Override
                public void onFailure(Call<ArrayList<TaiLieu>> call, Throwable t) {

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void onResponse(Call<ArrayList<TaiLieu>> call, Response<ArrayList<TaiLieu>> response){
        if(adapter == null){
            khoas = response.body();
            adapter = new TaiLieuAdapter(this.getContext(), R.layout.item_tailieu, khoas);
            this.lView.setAdapter(adapter);
        }else{
            khoas.clear();
            khoas.addAll(response.body());
            adapter.notifyDataSetChanged();
        }
    }
    FloatingActionButton delBtn, addBtn;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lView = this.getView().findViewById(R.id.lvTaiLieu);
        delBtn = this.getView().findViewById(R.id.btnXoaTL);
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

                Intent intent = new Intent(fragment_tailieu.this.getContext(), AddTaiLieu.class);
                intent.putExtra("id", khoas.get(i).MATL);
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

        addBtn = getView().findViewById(R.id.btnAddTL);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_tailieu.this.getContext(), AddTaiLieu.class);
                startActivityForResult(intent, 10);
            }
        });
        onConnect();
    }

    private TaiLieu saveDG;
    private void removeObj(){
        try{
            Call<Message> call = service.delete(saveDG.MATL);
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
        return inflater.inflate(R.layout.fragment_tailieu, container, false);
    }
}