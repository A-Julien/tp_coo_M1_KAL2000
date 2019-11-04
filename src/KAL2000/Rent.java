package KAL2000;

import Cards.Card;
import Errors.RentError;

import java.util.Date;

public class Rent {
    private Date dateRent;
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

    public boolean isRentFinish(){
        return this.dateReturn != null;
    }

    public Date getDateReturn() throws RentError {
        if(this.dateReturn == null) throw new RentError("Dvd not returned yet");
        return dateReturn;
    }

    public Date getDateRent() {
        return dateRent;
    }
}
