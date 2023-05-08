package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.ThuThu;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ThuThuService {
    @GET("api/thuthu/getall")
    public Call<ArrayList<ThuThu>> getAll();

    @GET("api/thuthu/get/{id}")
    public Call<ThuThu> get(@Path("id") int id);

    @POST("api/thuthu/create/{doc}")
    public void create(@Path("doc") ThuThu doc);

    @POST("api/thuthu/delete/{id}")
    public void delete(@Path("id") int id);

    @POST("api/thuthu/update/{doc}")
    public void update(@Path("doc") ThuThu doc);
}
