package com.manhhdc.qltk;

import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.manhhdc.qltk.Moduls.DocGia;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.adapter.DocGiaAdapter;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.DocGiaService;
import com.manhhdc.qltk.views.AddDocGia;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragment_docgia extends Fragment {


    public fragment_docgia() {
        // Required empty public constructor
    }

    public static fragment_docgia newInstance() {
        fragment_docgia fragment = new fragment_docgia();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public ArrayList<DocGia> docGias = new ArrayList<>();

    public DocGiaAdapter adapter;

    private FloatingActionButton delBtn , btnAdd;

    private ListView lView;
    private DocGiaService service;

    private DocGia saveDG;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(service != null){
            onConnect();
        }
    }

    private void onConnect(){

        try{
            Call<ArrayList<DocGia>> docgia = service.getAll();
            docgia.enqueue(new Callback<ArrayList<DocGia>>() {
                @Override
                public void onResponse(Call<ArrayList<DocGia>> call, Response<ArrayList<DocGia>> response) {
                    fragment_docgia.this.onResponse(call, response);
                }
                @Override
                public void onFailure(Call<ArrayList<DocGia>> call, Throwable t) {

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void onResponse(Call<ArrayList<DocGia>> call, Response<ArrayList<DocGia>> response){
        docGias.clear();
        docGias.addAll(response.body());
        if(this.lView.getAdapter() == null){
            adapter = new DocGiaAdapter(this.getContext(), R.layout.item_docgia, docGias);
            this.lView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }

    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        lView = this.getView().findViewById(R.id.lvDocGia);
        delBtn = this.getView().findViewById(R.id.btnXoaDG);
        delBtn.setEnabled(false);

        service =  ApiService.createService(DocGiaService.class);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i >= 0 && i < docGias.size() ){
                    delBtn.setEnabled(true);
                    fragment_docgia.this.saveDG = docGias.get(i);
                }else{
                    delBtn.setEnabled(false);
                }
            }
        });

        lView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View v, int i, long l) {
                Intent intent = new Intent(view.getContext(), AddDocGia.class);
                intent.putExtra("id", docGias.get(i).MADG);
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

        btnAdd = getView().findViewById(R.id.btnAddDocGia);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_docgia.this.getContext(), AddDocGia.class);
                startActivityForResult(intent, 10);
            }
        });

        onConnect();

    }

    private void removeObj(){
        try{
            Call<Message> call = service.delete(saveDG.MADG);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Log.i("error", response.toString());
                    Message msg = response.body();
                    Util.Alert(fragment_docgia.this.getView().getContext(), "Thông báo", msg.MSG);
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
        return inflater.inflate(R.layout.fragment_docgia, container, false);
    }
}