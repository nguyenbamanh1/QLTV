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
import com.manhhdc.qltk.Moduls.Khoa;
import com.manhhdc.qltk.Moduls.Lop;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.adapter.LopAdapter;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.LopService;
import com.manhhdc.qltk.views.AddKhoa;
import com.manhhdc.qltk.views.AddLop;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_lop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_lop extends Fragment {

    private ListView lView;
    private LopService service;
    private LopAdapter adapter;
    private  ArrayList<Lop> khoas = new ArrayList<>();

    public fragment_lop() {
        // Required empty public constructor
    }

    public static fragment_lop newInstance() {
        fragment_lop fragment = new fragment_lop();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    private void onConnect(){
        if(service ==null){
            service = ApiService.createService(LopService.class);
        }
        try{
            Call<ArrayList<Lop>> docgia = service.getAll();
            docgia.enqueue(new Callback<ArrayList<Lop>>() {
                @Override
                public void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response) {
                    fragment_lop.this.onResponse(call, response);
                }
                @Override
                public void onFailure(Call<ArrayList<Lop>> call, Throwable t) {

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void onResponse(Call<ArrayList<Lop>> call, Response<ArrayList<Lop>> response){
        if(this.lView.getAdapter() == null){
            khoas = response.body();
            adapter = new LopAdapter(this.getContext(), R.layout.item_lop, khoas);

            this.lView.setAdapter(adapter);
        }else{
            khoas.clear();
            khoas.addAll(response.body());
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(service != null){
            onConnect();
        }

    }
    FloatingActionButton delBtn, addBtn;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lView = this.getView().findViewById(R.id.lvLop);
        delBtn = this.getView().findViewById(R.id.btnXoaLop);
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

                Intent intent = new Intent(fragment_lop.this.getContext(), AddLop.class);
                intent.putExtra("id", khoas.get(i).ID);
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

        addBtn = getView().findViewById(R.id.btnAddLop);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_lop.this.getContext(), AddLop.class);
                startActivityForResult(intent, 10);
            }
        });
        onConnect();
    }

    private Lop saveDG;
    private void removeObj(){
        try{
            Call<Message> call = service.delete(saveDG.ID);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Log.i("error", response.toString());
                    Message msg = response.body();
                    Util.Alert(fragment_lop.this.getView().getContext(), "Thông báo", msg.MSG);
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
        return inflater.inflate(R.layout.fragment_lop, container, false);
    }
}