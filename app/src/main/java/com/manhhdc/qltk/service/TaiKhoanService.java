package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Entity;
import com.manhhdc.qltk.Moduls.TaiKhoan;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TaiKhoanService {
    @GET("api/taikhoan/getall")
    public Call<ArrayList<TaiKhoan>> getAll();

    @GET("api/taikhoan/get/{id}")
    public Call<TaiKhoan> get(@Path("id") int id);

    @POST("api/taikhoan/create/{doc}")
    public void create(@Path("doc") TaiKhoan doc);

    @POST("api/taikhoan/delete/{id}")
    public void delete(@Path("id") int id);

    @POST("api/taikhoan/update/{doc}")
    public void update(@Path("doc") TaiKhoan doc);

    @POST("api/taikhoan/login/{doc}")
    public void login(@Path("doc") TaiKhoan doc);
}
