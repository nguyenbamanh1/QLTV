package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.XuLy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface XuLyService {
    @GET("api/xuly/getall")
    public Call<ArrayList<XuLy>> getAll();

    @GET("api/xuly/get/{id}")
    public Call<XuLy> get(@Path("id") int id);

    @POST("api/xuly/create/{doc}")
    public void create(@Path("doc") XuLy doc);

    @POST("api/xuly/delete/{id}")
    public void delete(@Path("id") int id);

    @POST("api/xuly/update/{doc}")
    public void update(@Path("doc") XuLy doc);
}
