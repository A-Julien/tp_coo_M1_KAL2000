package com.Model.Rents;

import com.Model.Exception.RentException;
import com.Model.Movies.DvD;

import java.io.Serializable;
import java.util.Date;

/**
 * modelise a Rent
 */
public class Rent implements Serializable {
    /**
     * starting date
     */
    private Date dateRent;

    /**
     * return date
     */
    private Date dateReturn;

    private float price;

    private DvD dvd;

    public Rent(DvD dvd) {
        this.dvd = dvd;
        this.dateRent = new Date();
        this.dateReturn = null;
        this.price = 0;
    }

    public void setDateReturn() {
        this.dateReturn = new Date();
    }

    /**
     *
     * @return True if the dvd are already returned else False.
     */
    public boolean isRentFinish(){
        return this.dateReturn != null;
    }

    /**
     * Return the end date of the rent
     * @return
     * @throws RentException if dvd not returned yet
     */
    public Date getDateReturn() throws RentException {
        if(this.dateReturn == null) throw new RentException("Dvd not returned yet");
        return dateReturn;
    }

    public void setPrice(float price) throws RentException {
        if(this.price != 0) throw new  RentException("Rent already finished");
        this.price = price;
    }

    public float getPrice() throws RentException {
        if(this.price == 0) throw new RentException("Dvd not returned yet");
        return this.price;
    }

    public Date getDateRent() {
        return dateRent;
    }


    public DvD getDvd() {
        return dvd;
    }

    @Override
    public String toString() {
        return "Rent{" +
                "dateRent=" + dateRent +
                ", dateReturn=" + dateReturn +
                ", dvd=" + dvd +
                '}';
    }
}
