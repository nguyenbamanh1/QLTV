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
import com.manhhdc.qltk.Moduls.NXB;
import com.manhhdc.qltk.Moduls.Util;
import com.manhhdc.qltk.adapter.NXBAdapter;
import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.NXBService;
import com.manhhdc.qltk.views.AddKhoa;
import com.manhhdc.qltk.views.AddNXB;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_nxb#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_nxb extends Fragment {

    private ListView lView;
    private NXBService service;
    private NXBAdapter adapter;
    private ArrayList<NXB> khoas = new ArrayList<>();
    public fragment_nxb() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_nxb.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_nxb newInstance(String param1, String param2) {
        fragment_nxb fragment = new fragment_nxb();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    private void onConnect(){
        service = ApiService.createService(NXBService.class);
        try{
            Call<ArrayList<NXB>> docgia = service.getAll();
            docgia.enqueue(new Callback<ArrayList<NXB>>() {
                @Override
                public void onResponse(Call<ArrayList<NXB>> call, Response<ArrayList<NXB>> response) {
                    fragment_nxb.this.onResponse(call, response);
                }
                @Override
                public void onFailure(Call<ArrayList<NXB>> call, Throwable t) {

                }
            });

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void onResponse(Call<ArrayList<NXB>> call, Response<ArrayList<NXB>> response){
        if(adapter == null){
            khoas = response.body();
            adapter = new NXBAdapter(this.getContext(), R.layout.item_nhaxuatban, khoas);
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
        lView = this.getView().findViewById(R.id.lvNxb);
        delBtn = this.getView().findViewById(R.id.btnXoaNXB);
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

                Intent intent = new Intent(fragment_nxb.this.getContext(), AddNXB.class);
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

        addBtn = getView().findViewById(R.id.btnAddNxb);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(fragment_nxb.this.getContext(), AddNXB.class);
                startActivityForResult(intent, 10);
            }
        });
        onConnect();
    }

    private NXB saveDG;
    private void removeObj(){
        try{
            Call<Message> call = service.delete(saveDG.ID);
            call.enqueue(new Callback<Message>() {
                @Override
                public void onResponse(Call<Message> call, Response<Message> response) {
                    Log.i("error", response.toString());
                    Message msg = response.body();
                    Util.Alert(fragment_nxb.this.getView().getContext(), "Thông báo", msg.MSG);
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
        return inflater.inflate(R.layout.fragment_nxb, container, false);
    }
}