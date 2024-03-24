package com.example.assignment_and103_md18306_ph41939.Services;

import retrofit2.Call;

import com.example.assignment_and103_md18306_ph41939.model.MonAn;
import com.example.assignment_and103_md18306_ph41939.model.Response;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.ArrayList;

public interface ApiServices {
    String link = "http://192.168.1.31:3000/";
    @GET("/api/layDanhSachMon")
    Call<ArrayList<MonAn>> layDanhSachMon();

    @POST("/api/themMon")
    Call<MonAn> themMon(@Body MonAn monAn);

    @PUT("/api/capNhatMonAn/{id}")
    Call<MonAn> capNhatMonAn(@Path("id") String id, @Body MonAn monAn);

    @DELETE("/api/xoaMonAn/{id}")
    Call<Void> xoaMonAn(@Path("id") String id);

//    @GET("/api/timKiemMon")
//    Call<ArrayList<MonAn>> timKiemMon(@Query("key" String key);
}
