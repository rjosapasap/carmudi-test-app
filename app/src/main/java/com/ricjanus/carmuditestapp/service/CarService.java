package com.ricjanus.carmuditestapp.service;

import com.ricjanus.carmuditestapp.model.Car;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface CarService {

    @GET("/api/cars/page:{page}/maxitems:{itemCount}/")
    Call<List<Car>> listCars(@Path("page") int pageNum, @Path("itemCount") int itemCount);

}
