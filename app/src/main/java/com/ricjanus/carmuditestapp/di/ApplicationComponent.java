package com.ricjanus.carmuditestapp.di;

import com.ricjanus.carmuditestapp.CarmudiApplication;
import com.ricjanus.carmuditestapp.service.CarServiceModule;
import com.ricjanus.carmuditestapp.ui.fragment.carlist.CarListModule;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Component(modules = {AndroidInjectionModule.class, ApplicationModule.class, CarListModule.class, CarServiceModule.class})
public interface ApplicationComponent extends AndroidInjector<CarmudiApplication> {
}
