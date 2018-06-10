package com.ricjanus.carmuditestapp.service;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

@Module
public class CarServiceModule {

    @Provides
    CarService provideCarService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.carmudi.co.id")
                .build();

        return retrofit.create(CarService.class);
    }
}
