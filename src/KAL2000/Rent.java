package KAL2000;

import Exception.RentException;

import java.util.Date;

/**
 * modelise a Rent
 */
public class Rent {
    /**
     * starting date
     */
    private Date dateRent;

    /**
     * return date
     */
    private Date dateReturn;

    private DvD dvd;

    public Rent(DvD dvd) {
        this.dvd = dvd;
        this.dateRent = new Date();
        this.dateReturn = null;
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


    public Date getDateRent() {
        return dateRent;
    }


    public DvD getDvd() {
        return dvd;
    }
}
