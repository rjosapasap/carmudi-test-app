package com.ricjanus.carmuditestapp.ui.fragment.carlist;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ricjanus.carmuditestapp.R;
import com.ricjanus.carmuditestapp.model.Car;
import com.ricjanus.carmuditestapp.model.SortOption;
import com.ricjanus.carmuditestapp.ui.adapter.CarListAdapter;
import dagger.android.AndroidInjection;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CarListFragment extends Fragment implements CarListContract.View {

    private static final String CAR_LIST_BUNDLE_KEY = "com.ricjanus.car_list_key";
    private static final String CAR_LIST_ADAPTER_STATE_KEY = "com.ricjanus.car_list_adapter_state";
    private static final String CAR_LIST_PAGE_KEY= "com.ricjanus.page";
    private static final String CAR_LIST_MAX_ITEM_KEY = "com.ricjanus.max_item";
    private static final String CAR_LIST_SORT_KEY = "com.ricjanus.sort";

    @Inject
    CarListContract.Presenter presenter;

    private RecyclerView carRecyclerView;
    private LinearLayoutManager carRecyclerViewLayoutManager;
    private CarListAdapter carListAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private boolean loading;
    private int pastItemCount;
    private int itemCount;
    private int totalItemCount;

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
        outState.putSerializable(CAR_LIST_BUNDLE_KEY, (ArrayList<Car>) carListAdapter.getCarList());
        outState.putParcelable(CAR_LIST_ADAPTER_STATE_KEY, carRecyclerViewLayoutManager.onSaveInstanceState());
        outState.putInt(CAR_LIST_PAGE_KEY, presenter.getPage());
        outState.putInt(CAR_LIST_MAX_ITEM_KEY, presenter.getMaxItems());
        outState.putSerializable(CAR_LIST_SORT_KEY, presenter.getSortOption());
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_car_list, container, false);

        presenter.takeView(this);
        loading = false;

        carRecyclerViewLayoutManager = new LinearLayoutManager(getContext());

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this::doRefresh);

        List<Car> carList;

        if (savedInstanceState != null) {
            //noinspection unchecked
            carList = (ArrayList<Car>) savedInstanceState.getSerializable(CAR_LIST_BUNDLE_KEY);
            carListAdapter = new CarListAdapter(carList, getResources().getConfiguration().orientation);
            Parcelable layoutManagerParcelable = savedInstanceState.getParcelable(CAR_LIST_ADAPTER_STATE_KEY);
            carRecyclerViewLayoutManager.onRestoreInstanceState(layoutManagerParcelable);

            int page = savedInstanceState.getInt(CAR_LIST_PAGE_KEY);
            int maxItems = savedInstanceState.getInt(CAR_LIST_MAX_ITEM_KEY);
            SortOption sortOption = (SortOption) savedInstanceState.getSerializable(CAR_LIST_SORT_KEY);

            presenter.setPage(page);
            presenter.setMaxItems(maxItems);
            presenter.setSortOption(sortOption);
        } else {
            carList = new ArrayList<>();
            carListAdapter = new CarListAdapter(carList, getResources().getConfiguration().orientation);
            doRefresh();
        }

        carRecyclerView = view.findViewById(R.id.car_list_view);
        carRecyclerView.setLayoutManager(carRecyclerViewLayoutManager);
        carRecyclerView.setAdapter(carListAdapter);

        DividerItemDecoration dividerItemDecoration
                = new DividerItemDecoration(carRecyclerView.getContext(), DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.item_divider));
        carRecyclerView.addItemDecoration(dividerItemDecoration);

        carRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) {
                    pastItemCount = carRecyclerViewLayoutManager.getChildCount();
                    totalItemCount = carRecyclerViewLayoutManager.getItemCount();
                    itemCount = carRecyclerViewLayoutManager.findFirstVisibleItemPosition();

                    if (!loading) {
                        if (totalItemCount <= pastItemCount + itemCount) {
                            loading = true;
                            loadMoreCars();
                        }
                    }
                }
            }
        });

        return view;
    }

    private void doRefresh() {
        presenter.resetPagination();
        this.carListAdapter.clearCarList();
        loadMoreCars();
    }

    private void loadMoreCars() {
        swipeRefreshLayout.setRefreshing(true);
        AsyncTask.execute(() -> presenter.loadNextPage());
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
            carListAdapter.addCars(carList);
            swipeRefreshLayout.setRefreshing(false);
            loading = false;
        });
    }
}
