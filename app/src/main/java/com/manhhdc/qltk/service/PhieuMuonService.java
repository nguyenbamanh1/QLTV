package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.PhieuMuon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface PhieuMuonService {
    @GET("api/phieumuon/getall")
    public Call<ArrayList<PhieuMuon>> getAll();

    @GET("api/phieumuon/get/{id}")
    public Call<PhieuMuon> get(@Path("id") int id);

    @POST("api/phieumuon/create/{doc}")
    public void create(@Path("doc") PhieuMuon doc);

    @POST("api/phieumuon/delete/{id}")
    public void delete(@Path("id") int id);

    @POST("api/phieumuon/update/{doc}")
    public void update(@Path("doc") PhieuMuon doc);
}
