package com.ricjanus.carmuditestapp.ui.fragment.carlist;

import com.ricjanus.carmuditestapp.model.Car;
import com.ricjanus.carmuditestapp.model.SortOptions;
import com.ricjanus.carmuditestapp.mvp.IBasePresenter;
import com.ricjanus.carmuditestapp.mvp.IBaseView;

import java.util.List;

public interface CarListContract {

    interface View extends IBaseView<Presenter> {

        void showCars(List<Car> carList);

    }

    interface Presenter extends IBasePresenter<View> {

        void loadCars(int page, int maxItems);

        void loadCars(int page, int maxItems, SortOptions sortOptions);

    }

}
