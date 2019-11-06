package Cards;

import Errors.CardError;
import Errors.RentError;
import KAL2000.DvD;
import KAL2000.Rent;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class Card which implement basic function of card in KALL2000
 */
public abstract class Card {
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

    /**
     * getHistory
     * @return the history
     */
    protected ArrayList<Rent> getHistory(){
        return this.history;
    }

    /**
     * Add a rent to the history
     * the rent must be finsh
     * @param rent the rent to add
     * @throws CardError can't add on going rent in history
     */
    public void addHistory(Rent rent) throws CardError {
        if(!rent.isRentFinish()) throw new CardError("can't add on going rent in history");
        this.history.add(rent);
    }

    /**
     * return all on going rent
     * @return ArrayList<Rent>
     */
    public ArrayList<Rent> getOnGoingRent() {
        return onGoingRent;
    }


    /**
     * calculate a rent price
     * @param rent the rent
     * @param priceRent the price of the rent/by day
     * @return the price
     * @throws RentError
     */
    protected float calcPrice(Rent rent, int priceRent) throws RentError{
        long dayOfRent = 0;
        dayOfRent = Card.getDateDiff(rent.getDateRent(), rent.getDateReturn(), TimeUnit.DAYS);

        return dayOfRent * priceRent;
    }

    /**
     * Allows to rent a dvd
     * @param dvd the dvd to rent
     * @return the generate rent
     * @throws RentError
     */
    protected abstract Rent rentDvd(DvD dvd) throws RentError;

    /**
     * Allow to return a dvd in KAL2000
     * @param rent the rent to return
     * @throws RentError
     */
    protected abstract void returnDvd(Rent rent) throws RentError, CardError;

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
