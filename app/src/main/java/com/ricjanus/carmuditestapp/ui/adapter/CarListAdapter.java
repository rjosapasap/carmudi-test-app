package com.ricjanus.carmuditestapp.ui.adapter;

import android.content.res.Configuration;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.ricjanus.carmuditestapp.R;
import com.ricjanus.carmuditestapp.io.GlideApp;
import com.ricjanus.carmuditestapp.model.Car;

import java.util.List;

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.ViewHolder> {

    private List<Car> carList;
    private int orientation;

    public CarListAdapter(List<Car> carList, int orientation) {
        this.carList = carList;
        this.orientation = orientation;
    }

    @Override
    public CarListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (this.orientation == Configuration.ORIENTATION_PORTRAIT) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_car_row_portrait, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_car_row_landscape, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarListAdapter.ViewHolder holder, int position) {
        Car car = carList.get(position);
        View view = holder.getView();

        TextView nameTextView = view.findViewById(R.id.name_text_view);
        TextView brandTextView = view.findViewById(R.id.brand_text_view);
        TextView priceTextView = view.findViewById(R.id.price_text_view);
        ImageView carImageView = view.findViewById(R.id.car_image_view);

        CircularProgressDrawable progressDrawable = new CircularProgressDrawable(view.getContext());
        progressDrawable.setStrokeWidth(5);
        progressDrawable.setCenterRadius(30);
        progressDrawable.start();

        GlideApp
            .with(view)
            .load(car.getImageURL())
            .placeholder(progressDrawable)
            .into(carImageView);

        nameTextView.setText(car.getName());
        brandTextView.setText(car.getCompleteBrand());
        priceTextView.setText(car.getPriceString());
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
