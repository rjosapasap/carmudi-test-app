package com.ricjanus.carmuditestapp.mvp;

public interface IBasePresenter<V> {

    void takeView(V view);

    void dropView();

}
