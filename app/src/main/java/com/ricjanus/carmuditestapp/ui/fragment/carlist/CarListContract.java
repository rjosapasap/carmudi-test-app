package com.ricjanus.carmuditestapp.ui.fragment.carlist;

import com.ricjanus.carmuditestapp.model.Car;
import com.ricjanus.carmuditestapp.model.SortOption;
import com.ricjanus.carmuditestapp.mvp.IBasePresenter;
import com.ricjanus.carmuditestapp.mvp.IBaseView;

import java.util.List;

public interface CarListContract {

    interface View extends IBaseView<Presenter> {

        void showCars(List<Car> carList);

    }

    interface Presenter extends IBasePresenter<View> {

        void loadNextPage();

        void setPage(int page);

        int getPage();

        void setMaxItems(int maxItems);

        int getMaxItems();

        void setSortOption(SortOption sortOption);

        SortOption getSortOption();

        void resetPagination();

//        void loadCars(int page, int maxItems);
//
//        void loadCars(int page, int maxItems, SortOption sortOptions);

    }

}
