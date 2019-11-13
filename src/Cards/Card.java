package Cards;

import Exception.CardException;
import Exception.RentException;
import Exception.SubCardException;
import Exception.StatusDvdException;

import KAL2000.DvD;
import KAL2000.Rent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class Card which implement basic function of card in KAL2000
 */

public abstract class Card implements Serializable, RentManager, RentHistorysable {
    /**
     * history of old rent
     */
    private ArrayList<Rent> history;

    /**
     * rent on going
     */
    protected ArrayList<Rent> onGoingRent;


    public Card() {
        this.history = new ArrayList<>();
        this.onGoingRent = new ArrayList<>();
    }

    @Override
    public Rent getRent(DvD dvd) throws RentException {
        for(Rent rent : this.onGoingRent) if(rent.getDvd() == dvd) return rent;
        throw new RentException("No rent found for the dvd : " + dvd.getFilm().getTitle());
    }

    /**
     * getHistory
     * @return the history
     */
    @Override
    public ArrayList<Rent> getHistory(){
        return this.history;
    }

    /**
     * Add a rent to the history
     * the rent must be finsh
     * @param rent the rent to add
     * @throws CardException can't add on going rent in history
     */
    @Override
    public void addHistory(Rent rent) throws CardException {
        if(!rent.isRentFinish()) throw new CardException("can't add on going rent in history");
        this.history.add(rent);
    }

    /**
     * return all on going rent
     * @return ArrayList<Rent>
     */
    @Override
    public ArrayList<Rent> getOnGoingRent() {
        return onGoingRent;
    }


    /**
     * calculate a rent price
     * @param rent the rent
     * @param priceRent the price of the rent/by day
     * @return the price
     * @throws RentException
     */
    protected float calcPrice(Rent rent, int priceRent) throws RentException {
        return  Card.getDateDiff(rent.getDateRent(), rent.getDateReturn(), TimeUnit.DAYS) * priceRent;
    }

    /**
     * Allows to rent a dvd
     * @param dvd the dvd to rent
     * @return the generate rent
     * @throws RentException
     */
    @Override
    public abstract Rent rentDvd(DvD dvd) throws RentException, StatusDvdException;

    /**
     * Check if the time rent is over.
     * @throws RentException
     */
    @Override
    public abstract void checkRentDate() throws RentException;

    /**
     * Allow to return a dvd in KAL2000
     * @param rent the rent to return
     * @throws RentException
     */
    @Override
    public abstract void returnDvd(Rent rent) throws RentException, CardException, SubCardException;

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

    @Override
    public abstract void setMaximumRent(int maximumRent);

}
