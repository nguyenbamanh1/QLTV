package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.DocGia;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DocGiaService {
    @GET("api/docgia/getall")
    public Call<ArrayList<DocGia>> getAll();

    @GET("api/docgia/get/{id}")
    public Call<DocGia> get(@Path("id") int id);

    @POST("api/docgia/create/{doc}")
    public void create(@Path("doc") DocGia doc);

    @POST("api/docgia/delete/{id}")
    public void delete(@Path("id") int id);

    @POST("api/docgia/update/{doc}")
    public void update(@Path("doc") DocGia doc);
}
