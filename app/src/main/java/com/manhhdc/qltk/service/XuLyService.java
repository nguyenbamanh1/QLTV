package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.XuLy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface XuLyService {
    @GET("api/xuly/getall")
    public Call<ArrayList<XuLy>> getAll();

    @GET("api/xuly/get/{id}")
    public Call<XuLy> get(@Path("id") String id);

    @POST("api/xuly/create/")
    public Call<Message> create(@Body XuLy doc);

    @DELETE("api/xuly/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/xuly/update/")
    public Call<Message> update(@Body XuLy doc);
}
