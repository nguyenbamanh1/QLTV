package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TheDangKy;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TheDangKyService {
    @GET("api/thedangky/getall")
    public Call<ArrayList<TheDangKy>> getAll();

    @GET("api/thedangky/get/{id}")
    public Call<TheDangKy> get(@Path("id") String id);

    @POST("api/thedangky/create/")
    public Call<Message> create(@Body TheDangKy doc);

    @DELETE("api/thedangky/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/thedangky/update/")
    public Call<Message> update(@Body TheDangKy doc);
}
