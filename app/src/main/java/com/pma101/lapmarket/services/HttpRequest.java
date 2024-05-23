package com.pma101.lapmarket.services;


import static com.pma101.lapmarket.services.ApiServices.BASE_URL;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private ApiServices requestInterface;

    public HttpRequest() {
        requestInterface = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())//chuyển đổi đối tượng json sang java
                .build().create(ApiServices.class);
    }

    public ApiServices callAPI(){
        return requestInterface;
    }
}
