package com.manhhdc.qltk;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.manhhdc.qltk.service.ApiService;
import com.manhhdc.qltk.service.DocGiaService;


public class fragment_docgia extends Fragment {

    DocGiaService service;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        service = ApiService.createService(DocGiaService.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_docgia, container, false);
    }
}