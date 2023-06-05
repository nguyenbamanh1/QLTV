package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.ThuThu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ThuThuService {
    @GET("api/thuthu/getall")
    public Call<ArrayList<ThuThu>> getAll();

    @GET("api/thuthu/get/{id}")
    public Call<ThuThu> get(@Path("id") String id);

    @POST("api/thuthu/create/")
    public Call<Message> create(@Body ThuThu doc);

    @DELETE("api/thuthu/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/thuthu/update/")
    public Call<Message> update(@Body ThuThu doc);
}
