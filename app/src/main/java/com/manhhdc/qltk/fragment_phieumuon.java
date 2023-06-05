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
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.PhieuMuon;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.adapter.PhieuMuonAdapter;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.PhieuMuonService;
import com.manhhdc.qltk.views.AddKhoa;
import com.manhhdc.qltk.views.AddPhieuMuon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_phieumuon#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_phieumuon extends Fragment {

    private ListView lView;
    private PhieuMuonService service;
    private PhieuMuonAdapter adapter;
    private ArrayList<PhieuMuon> khoas = new ArrayList<>();
    public fragment_phieumuon() {
        // Required empty public constructor
    }

    public static fragment_phieumuon newInstance() {
        fragment_phieumuon fragment = new fragment_phieumuon();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    private void onConnect(){
        service = ApiService.createService(PhieuMuonService.class);
        try{
            Call<ArrayList<PhieuMuon>> docgia = service.getAll();
            docgia.enqueue(new Callback<ArrayList<PhieuMuon>>() {
                @Override
                public void onResponse(Call<ArrayList<PhieuMuon>> call, Response<ArrayList<PhieuMuon>> response) {
                    fragment_phieumuon.this.onResponse(call, response);
                }
                @Override
                public void onFailure(Call<ArrayList<PhieuMuon>> call, Throwable t) {

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void onResponse(Call<ArrayList<PhieuMuon>> call, Response<ArrayList<PhieuMuon>> response){
        if(adapter == null){
            khoas = response.body();
            adapter = new PhieuMuonAdapter(this.getContext(), R.layout.item_phieumuon, khoas);
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
        lView = this.getView().findViewById(R.id.lvPhieuMuon);
        delBtn = this.getView().findViewById(R.id.btnXoaPhieuMuon);
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

                Intent intent = new Intent(fragment_phieumuon.this.getContext(), AddPhieuMuon.class);
                intent.putExtra("id", khoas.get(i).MAMT);
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

        addBtn = getView().findViewById(R.id.btnAddPhieuMuon);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_phieumuon.this.getContext(), AddPhieuMuon.class);
                startActivityForResult(intent, 10);
            }
        });
        onConnect();
    }

    private PhieuMuon saveDG;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(service != null){
            onConnect();
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
        return inflater.inflate(R.layout.fragment_phieumuon, container, false);
    }
}