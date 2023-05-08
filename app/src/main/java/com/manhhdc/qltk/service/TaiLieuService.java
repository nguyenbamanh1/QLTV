package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.TaiLieu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TaiLieuService {
    @GET("api/tailieu/getall")
    public Call<ArrayList<TaiLieu>> getAll();

    @GET("api/tailieu/get/{id}")
    public Call<TaiLieu> get(@Path("id") int id);

    @POST("api/tailieu/create/{doc}")
    public void create(@Path("doc") TaiLieu doc);

    @POST("api/tailieu/delete/{id}")
    public void delete(@Path("id") int id);

    @POST("api/tailieu/update/{doc}")
    public void update(@Path("doc") TaiLieu doc);
}
