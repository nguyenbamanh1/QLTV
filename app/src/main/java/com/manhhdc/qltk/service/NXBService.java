package com.manhhdc.qltk.service;

import com.manhhdc.qltk.Moduls.Lop;
import com.manhhdc.qltk.Moduls.Message;
import com.manhhdc.qltk.Moduls.NXB;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NXBService {
    @GET("api/nxb/getall")
    public Call<ArrayList<NXB>> getAll();

    @GET("api/nxb/get/{id}")
    public Call<NXB> get(@Path("id") String id);

    @POST("api/nxb/create/")
    public Call<Message> create(@Body NXB doc);

    @DELETE("api/nxb/delete/{id}")
    public Call<Message> delete(@Path("id") String id);

    @PUT("api/nxb/update/")
    public Call<Message> update(@Body NXB doc);
}
