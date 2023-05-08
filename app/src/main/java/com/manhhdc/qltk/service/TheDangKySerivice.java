package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.TheDangKy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TheDangKySerivice {
    @GET("api/thedangky/getall")
    public Call<ArrayList<TheDangKy>> getAll();

    @GET("api/thedangky/get/{id}")
    public Call<TheDangKy> get(@Path("id") int id);

    @POST("api/thedangky/create/{doc}")
    public void create(@Path("doc") TheDangKy doc);

    @POST("api/thedangky/delete/{id}")
    public void delete(@Path("id") int id);

    @POST("api/thedangky/update/{doc}")
    public void update(@Path("doc") TheDangKy doc);
}
