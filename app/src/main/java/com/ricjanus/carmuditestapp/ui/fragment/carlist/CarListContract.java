package com.ricjanus.carmuditestapp.ui.fragment.carlist;

import com.ricjanus.carmuditestapp.mvp.IBasePresenter;
import com.ricjanus.carmuditestapp.mvp.IBaseView;

public interface CarListContract {

    interface View extends IBaseView<Presenter> {

    }

    interface Presenter extends IBasePresenter<View> {

    }

}
