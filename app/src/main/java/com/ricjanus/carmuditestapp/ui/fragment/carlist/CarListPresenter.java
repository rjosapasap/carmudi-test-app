package com.ricjanus.carmuditestapp.ui.fragment.carlist;

import com.ricjanus.carmuditestapp.model.APIResponse;
import com.ricjanus.carmuditestapp.model.SortOptions;
import com.ricjanus.carmuditestapp.service.CarService;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;

public class CarListPresenter implements CarListContract.Presenter {

    private CarListContract.View view;

    @Inject
    CarService carService;

    @Inject
    public CarListPresenter() {
    }

    @Override
    public void takeView(CarListContract.View view) {
        this.view = view;
    }

    @Override
    public void dropView() {
        this.view = null;
    }

    @Override
    public void loadCars(int page, int maxItems) {
        Optional
            .ofNullable(view)
            .ifPresent(
                view -> {
                    Call<APIResponse> call = carService.listCars(page, maxItems);
                    try {
                        Response<APIResponse> response = call.execute();
                        if (response.isSuccessful()) {
                            APIResponse apiResponse = response.body();
                            if (apiResponse.isSuccess()) {
                                view.showCars(apiResponse.getResult().getCarList());
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
    }

    @Override
    public void loadCars(int page, int maxItems, SortOptions sortOptions) {

    }
}
