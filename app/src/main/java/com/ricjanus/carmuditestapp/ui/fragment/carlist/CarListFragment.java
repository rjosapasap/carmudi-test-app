package com.ricjanus.carmuditestapp.ui.fragment.carlist;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ricjanus.carmuditestapp.R;
import com.ricjanus.carmuditestapp.model.Car;
import com.ricjanus.carmuditestapp.ui.adapter.CarListAdapter;
import dagger.android.AndroidInjection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarListFragment extends Fragment implements CarListContract.View {

    private static final String CAR_LIST_BUNDLE_KEY = "com.ricjanus.car_list_key";
    private static final String CAR_LIST_ADAPTER_STATE = "com.ricjanus.car_list_adapter_state";

    @Inject
    CarListContract.Presenter presenter;

    private RecyclerView carRecyclerView;
    private RecyclerView.LayoutManager carRecyclerViewLayoutManager;
    private RecyclerView.Adapter carRecyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private List<Car> carList;

    @Inject
    public CarListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(CAR_LIST_BUNDLE_KEY, (ArrayList<Car>) carList);
        outState.putParcelable(CAR_LIST_ADAPTER_STATE, carRecyclerViewLayoutManager.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);

        carRecyclerViewLayoutManager = new LinearLayoutManager(getContext());

        presenter.takeView(this);

        if (savedInstanceState != null) {
            System.out.println("INSTANCE STATE " + this);
            //noinspection unchecked
            carList = (ArrayList<Car>) savedInstanceState.getSerializable(CAR_LIST_BUNDLE_KEY);
            Parcelable layoutManagerParcelable = savedInstanceState.getParcelable(CAR_LIST_ADAPTER_STATE);
            System.out.println("~~~~~~~~~~~~~~~" + layoutManagerParcelable.toString());
            carRecyclerViewLayoutManager.onRestoreInstanceState(layoutManagerParcelable);
        } else {
            System.out.println("NO INSTANCE STATE " + this);
            carList = new ArrayList<>();
            doRefresh();
        }

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::doRefresh);

        carRecyclerView = view.findViewById(R.id.car_list_view);
        carRecyclerView.setLayoutManager(carRecyclerViewLayoutManager);

        carRecyclerViewAdapter = new CarListAdapter(carList);
        carRecyclerView.setAdapter(carRecyclerViewAdapter);

        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(carRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        carRecyclerView.addItemDecoration(dividerItemDecoration);

        return view;
    }

    private void doRefresh() {
        carList.clear();
        AsyncTask.execute(() ->
                presenter.loadCars(1, 10)
        );
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
            this.carList.addAll(carList);
            carRecyclerViewAdapter.notifyDataSetChanged();
            swipeRefreshLayout.setRefreshing(false);
        });
    }
}
