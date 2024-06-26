package com.pma101.lapmarket.services;

import com.pma101.lapmarket.Response;
import com.pma101.lapmarket.models.Khachhang;
import com.pma101.lapmarket.models.Laptop;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiServices {
    public static String BASE_URL = "http://192.168.1.16:3000/api/";

    @GET("get-list-laptop")
    Call<Response<ArrayList<Laptop>>> getListLaptop();

    @POST("add-laptop")
    Call<Response<Laptop>> addLaptop(@Body Laptop laptops);

    @DELETE("delete-laptop-by-id/{id}")
    Call<Response<Laptop>> deleteLaptopById(@Path("id") String id);

    @PUT("update-laptop-by-id/{id}")
    Call<Response<Laptop>> updateLaptopById(@Path("id") String id, @Body Laptop students);

    @GET("get-list-kh")
    Call<Response<ArrayList<Khachhang>>> getListKH();

    @POST("add-kh")
    Call<Response<Khachhang>> addKH(@Body Khachhang khachhangs);

    @DELETE("delete-kh-by-id/{id}")
    Call<Response<Khachhang>> deleteKHById(@Path("id") String id);

    @PUT("update-kh-by-id/{id}")
    Call<Response<Khachhang>> updateKHById(@Path("id") String id, @Body Khachhang khachhangs);
}
