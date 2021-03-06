package com.ricjanus.carmuditestapp.ui;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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

        // set toolbar title
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        // detect if the orientation changed, if so retain original fragment
        if (savedInstanceState == null) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.fragment_frame, carListFragment);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // add sort action to toolbar
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }
}
