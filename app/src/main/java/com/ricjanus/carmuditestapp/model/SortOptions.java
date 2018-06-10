package com.ricjanus.carmuditestapp.model;

public enum SortOptions {
    OLDEST, NEWEST, PRICE_LOW, PRICE_HIGH, MILEAGE_LOW, MILEAGE_HIGH;

    @Override
    public String toString() {
        String str = super.toString();
        return str.toLowerCase().replace('_', '-');
    }
}
