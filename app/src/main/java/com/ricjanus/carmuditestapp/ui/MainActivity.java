package com.ricjanus.carmuditestapp.ui;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import com.ricjanus.carmuditestapp.R;
import com.ricjanus.carmuditestapp.ui.fragment.carlist.CarListFragment;
import com.ricjanus.carmuditestapp.ui.fragment.carlist.CarListPresenter;
import dagger.android.AndroidInjection;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    @Inject
    CarListFragment carListFragment;

    @Inject
    CarListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        // detect if the orientation changed
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_frame, carListFragment);
            transaction.commit();
        }
    }

}
