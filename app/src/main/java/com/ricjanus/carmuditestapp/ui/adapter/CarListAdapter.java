package com.ricjanus.carmuditestapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ricjanus.carmuditestapp.R;
import com.ricjanus.carmuditestapp.model.Car;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    private List<Car> carList;

    public CarListAdapter(List<Car> carList) {
        this.carList = carList;
    }

    @Override
    public CarListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_car_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarListAdapter.ViewHolder holder, int position) {
        Car car = carList.get(position);
        View view = holder.getView();

        TextView nameTextView = view.findViewById(R.id.name_text_view);
        nameTextView.setText(car.getName());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public List<Car> getCarList() {
        return carList;
    }

    public void clearCarList() {
        this.carList.clear();
        notifyDataSetChanged();
    }

    public void addCars(List<Car> additionalCars) {
        this.carList.addAll(additionalCars);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View view;

        ViewHolder(View itemView) {
            super(itemView);
            this.view = itemView;
        }

        public View getView() {
            return view;
        }
    }

}
