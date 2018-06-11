package com.ricjanus.carmuditestapp.model;

import java.io.Serializable;

// list of sort options available
public enum SortOption implements Serializable {
    OLDEST, NEWEST, PRICE_LOW, PRICE_HIGH, MILEAGE_LOW, MILEAGE_HIGH;

    @Override
    public String toString() {
        String str = super.toString();
        return str.toLowerCase().replace('_', '-');
    }
}
