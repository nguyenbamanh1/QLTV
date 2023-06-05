package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.PhieuMuon;
import com.manhhdc.qltk.Moduls.TacGia;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TacGiaService {
    @GET("api/tacgia/getall")
    public Call<ArrayList<TacGia>> getAll();

    @GET("api/tacgia/get/{id}")
    public Call<TacGia> get(@Path("id") String id);

    @POST("api/tacgia/create/")
    public Call<Message> create(@Body TacGia doc);

    @DELETE("api/tacgia/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/tacgia/update/")
    public Call<Message> update(@Body TacGia doc);
}
