package com.ricjanus.carmuditestapp.ui.fragment.carlist;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class CarListModule {

    @Binds
    abstract CarListContract.Presenter provideCarListContractPresenter(CarListPresenter carListPresenter);

}
