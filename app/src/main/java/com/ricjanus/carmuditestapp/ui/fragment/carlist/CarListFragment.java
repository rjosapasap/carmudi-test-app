package com.ricjanus.carmuditestapp.ui.fragment.carlist;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ricjanus.carmuditestapp.R;
import com.ricjanus.carmuditestapp.model.Car;
import com.ricjanus.carmuditestapp.ui.adapter.CarListAdapter;

import javax.inject.Inject;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarListFragment extends Fragment implements CarListContract.View {

    @Inject
    CarListContract.Presenter presenter;

    private RecyclerView carRecyclerView;
    private RecyclerView.LayoutManager carRecyclerViewLayoutManager;
    private RecyclerView.Adapter carRecyclerViewAdapter;

    @Inject
    public CarListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);

        carRecyclerView = view.findViewById(R.id.car_list_view);

        carRecyclerViewLayoutManager = new LinearLayoutManager(getContext());
        carRecyclerView.setLayoutManager(carRecyclerViewLayoutManager);

        carRecyclerViewAdapter = new CarListAdapter();
        carRecyclerView.setAdapter(carRecyclerViewAdapter);

        presenter.takeView(this);
        AsyncTask.execute(() ->
                presenter.loadCars(1, 10)
        );

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.takeView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.dropView();
    }

    @Override
    public void showCars(List<Car> carList) {
        getActivity().runOnUiThread(() -> {
            Log.d("CarList", carList.toString());
        });
    }
}
