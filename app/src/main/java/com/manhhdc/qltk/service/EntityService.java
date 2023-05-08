package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface EntityService {
    @GET("api/entity/getall")
    public Call<ArrayList<Entity>> getAll();

    @GET("api/entity/get/{id}")
    public Call<Entity> get(@Path("id") int id);

    @POST("api/entity/create/{doc}")
    public void create(@Path("doc") Entity doc);

    @POST("api/entity/delete/{id}")
    public void delete(@Path("id") int id);

    @POST("api/entity/update/{doc}")
    public void update(@Path("doc") Entity doc);
}
