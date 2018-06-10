package com.ricjanus.carmuditestapp.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ricjanus.carmuditestapp.io.CarDeserializer;
import com.ricjanus.carmuditestapp.model.Car;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class CarServiceModule {

    @Provides
    CarDeserializer provideCarDeserializer() {
        return new CarDeserializer();
    }

    @Provides
    GsonConverterFactory provideGsonConverterFactory(CarDeserializer carDeserializer) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Car.class, carDeserializer);

        Gson gson = gsonBuilder.create();

        return GsonConverterFactory.create(gson);
    }

    @Provides
    CarService provideCarService(GsonConverterFactory gsonConverterFactory) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.carmudi.co.id")
                .addConverterFactory(gsonConverterFactory)
                .build();

        return retrofit.create(CarService.class);
    }
}
