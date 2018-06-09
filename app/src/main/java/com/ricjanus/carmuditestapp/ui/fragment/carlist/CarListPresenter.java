package com.ricjanus.carmuditestapp.ui.fragment.carlist;

import com.ricjanus.carmuditestapp.di.ActivityScoped;

import javax.inject.Inject;

public class CarListPresenter implements CarListContract.Presenter {

    private CarListContract.View view;

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
}
