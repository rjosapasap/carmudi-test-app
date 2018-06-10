package com.ricjanus.carmuditestapp.ui.fragment.carlist;

import com.ricjanus.carmuditestapp.model.APIResponse;
import com.ricjanus.carmuditestapp.model.SortOption;
import com.ricjanus.carmuditestapp.service.CarService;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Optional;

public class CarListPresenter implements CarListContract.Presenter {

    private static final int DEFAULT_MAX_ITEMS = 10;

    private CarListContract.View view;

    @Inject
    CarService carService;

    private int currentPage;
    private int maxItems;
    private SortOption sortOption;

    @Inject
    public CarListPresenter() {
        currentPage = 0;
        maxItems = DEFAULT_MAX_ITEMS;
        sortOption = null;
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
    public void loadNextPage() {
        currentPage++;
        Optional
            .ofNullable(view)
            .ifPresent(
                    view -> {
                        Call<APIResponse> call;
                        if (sortOption == null) {
                            call = carService.listCars(currentPage, maxItems);
                        } else {
                            call = carService.listCars(currentPage, maxItems, sortOption.toString());
                        }
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
    public void setPage(int page) {
        currentPage = page;
    }

    @Override
    public int getPage() {
        return currentPage;
    }

    @Override
    public void setMaxItems(int maxItems) {
        this.maxItems = maxItems;
    }

    @Override
    public int getMaxItems() {
        return maxItems;
    }

    @Override
    public void setSortOption(String sortOption) {
        String reformattedSortOption = sortOption.toUpperCase().replace('-', '_');
        try{
            this.sortOption = SortOption.valueOf(reformattedSortOption);
        }catch (IllegalArgumentException iae) {
            this.sortOption = null;
        }
    }

    @Override
    public SortOption getSortOption() {
        return sortOption;
    }

    @Override
    public void resetPagination() {
        this.currentPage = 0;
    }
}
