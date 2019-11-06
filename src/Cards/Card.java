package Cards;

import Errors.RentError;
import KAL2000.DvD;
import KAL2000.Rent;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public abstract class Card {

    private ArrayList<Rent> history;
    protected ArrayList<Rent> onGoingRent;

    public Card() {
        this.history = new ArrayList<>();
        this.onGoingRent = new ArrayList<>();
    }

    protected ArrayList<Rent> getHistory(){
        return this.history;
    }
    public void addHistory(Rent rent) {
        this.history.add(rent);
    }
    public ArrayList<Rent> getOnGoingRent() {
        return onGoingRent;
    }


    protected abstract float calcPrice(Rent rent) throws RentError;
    protected abstract Rent rentDvd(DvD dvd) throws RentError;
    protected abstract void returnDvd(Rent rent) throws RentError;

    /**
     * Get a diff between two dates
     * @param date1 the oldest date
     * @param date2 the newest date
     * @param timeUnit the unit in which you want the diff
     * @return the diff value, in the provided unit
     */
    public static long getDateDiff(Date date1, Date date2, TimeUnit timeUnit) {
        long diffInMillies = date2.getTime() - date1.getTime();
        return timeUnit.convert(diffInMillies,TimeUnit.MILLISECONDS);
    }


}
