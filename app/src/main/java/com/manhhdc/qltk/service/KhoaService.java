package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.Khoa;
import com.manhhdc.qltk.Moduls.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface KhoaService {
    @GET("api/khoa/getall")
    public Call<ArrayList<Khoa>> getAll();

    @GET("api/khoa/get/{id}")
    public Call<Khoa> get(@Path("id") String id);

    @POST("api/khoa/create/")
    public Call<Message> create(@Body Khoa doc);

    @DELETE("api/khoa/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/khoa/update/")
    public Call<Message> update(@Body Khoa doc);
}
