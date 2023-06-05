package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Lop;
import com.manhhdc.qltk.Moduls.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface LopService {
    @GET("api/lop/getall")
    public Call<ArrayList<Lop>> getAll();

    @GET("api/lop/get/{id}")
    public Call<Lop> get(@Path("id") String id);

    @POST("api/lop/create/")
    public Call<Message> create(@Body Lop doc);

    @DELETE("api/lop/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/lop/update/")
    public Call<Message> update(@Body Lop doc);
}
