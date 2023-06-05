package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TaiLieu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaiLieuService {
    @GET("api/tailieu/getall")
    public Call<ArrayList<TaiLieu>> getAll();

    @GET("api/tailieu/get/{id}")
    public Call<TaiLieu> get(@Path("id") String id);

    @POST("api/tailieu/create/")
    public Call<Message> create(@Body TaiLieu doc);

    @DELETE("api/tailieu/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/tailieu/update/")
    public Call<Message> update(@Body TaiLieu doc);
}
