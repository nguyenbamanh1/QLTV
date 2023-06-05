package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EntityService {
    @GET("api/entity/getall")
    public Call<ArrayList<Entity>> getAll();

    @GET("api/entity/get/{id}")
    public Call<Entity> get(@Path("id") String id);

    @POST("api/entity/create/")
    public Call<Message> create(@Body Entity doc);

    @DELETE("api/entity/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/entity/update/")
    public Call<Message> update(@Body Entity doc);
}
