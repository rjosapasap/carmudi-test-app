package com.ricjanus.carmuditestapp.di;

import com.ricjanus.carmuditestapp.ui.MainActivity;
import com.ricjanus.carmuditestapp.ui.fragment.carlist.CarListFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ApplicationModule {
    @ContributesAndroidInjector
    abstract MainActivity contributeMainActivityInjector();

    @ContributesAndroidInjector
    abstract CarListFragment contributeCarListFragmentInjector();
}
