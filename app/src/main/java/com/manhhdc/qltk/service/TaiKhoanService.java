package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.TaiKhoan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaiKhoanService {
    @GET("api/taikhoan/getall")
    public Call<ArrayList<TaiKhoan>> getAll();

    @GET("api/taikhoan/get/{id}")
    public Call<TaiKhoan> get(@Path("id") String id);

    @POST("api/taikhoan/create/")
    public Call<Message> create(@Body TaiKhoan doc);

    @DELETE("api/taikhoan/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/taikhoan/update/")
    public Call<Message> update(@Body TaiKhoan doc);

    @POST("api/taikhoan/login/")
    public Call<Message> login(@Body TaiKhoan doc);
}
