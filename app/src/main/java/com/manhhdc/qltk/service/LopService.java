package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Lop;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LopService {
    @GET("api/lop/getall")
    public Call<ArrayList<Lop>> getAll();

    @GET("api/lop/get/{id}")
    public Call<Lop> get(@Path("id") int id);

    @POST("api/lop/create/{doc}")
    public void create(@Path("doc") Lop doc);

    @POST("api/lop/delete/{id}")
    public void delete(@Path("id") int id);

    @POST("api/lop/update/{doc}")
    public void update(@Path("doc") Lop doc);
}
