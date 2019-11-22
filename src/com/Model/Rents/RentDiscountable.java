package com.Model.Rents;

import com.Model.Movies.DvD;

/**
 * A normal rent that implement the discount system
 */
public class RentDiscountable extends Rent {
    private boolean discount;

    public RentDiscountable(DvD dvd) {
        super(dvd);
        this.discount = false;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount() {
        this.discount = true;
    }
}
