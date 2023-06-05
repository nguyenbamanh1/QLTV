package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.DocGia;
import com.manhhdc.qltk.Moduls.Message;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DocGiaService {
    @GET("api/docgia/getall")
    public Call<ArrayList<DocGia>> getAll();

    @GET("api/docgia/get/{id}")
    public Call<DocGia> get(@Path("id") String id);

    @POST("api/docgia/create/")
    public Call<Message>  create(@Body DocGia doc);

    @DELETE("api/docgia/delete/{id}")
    public Call<Message>  delete(@Path("id") String id);

    @PUT("api/docgia/update/")
    public Call<Message> update(@Body DocGia doc);
}
