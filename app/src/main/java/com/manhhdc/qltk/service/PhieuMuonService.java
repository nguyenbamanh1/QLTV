package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.PhieuMuon;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PhieuMuonService {
    @GET("api/phieumuon/getall")
    public Call<ArrayList<PhieuMuon>> getAll();

    @GET("api/phieumuon/get/{id}")
    public Call<PhieuMuon> get(@Path("id") String id);

    @POST("api/phieumuon/create/")
    public Call<Message> create(@Body PhieuMuon doc);

    @DELETE("api/phieumuon/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/phieumuon/update/")
    public Call<Message> update(@Body PhieuMuon doc);
}
