package com.ricjanus.carmuditestapp.service;

import com.ricjanus.carmuditestapp.model.APIResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CarService {

    @GET("/api/cars/page:{page}/maxitems:{itemCount}/")
    Call<APIResponse> listCars(@Path("page") int pageNum, @Path("itemCount") int itemCount);

    @GET("/api/cars/page:{page}/maxitems:{itemCount}/sort:{sort_key}")
    Call<APIResponse> listCars(@Path("page") int pageNum, @Path("itemCount") int itemCount, @Path("sort_key") String sortKey);

}
