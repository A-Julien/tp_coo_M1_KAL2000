package org.com.Model.Cards;

import org.com.Model.Exception.CardException;
import org.com.Model.Exception.RentException;
import org.com.Model.Movies.DvD;
import org.com.Model.Rents.Rent;
import org.com.Model.Movies.State;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Class CreditCard which implement basic function of card in com.KAL2000
 */
public class CreditCard extends Card implements Serializable {
    /**
     * The the maximum rents that can do a CreditCard
     */
    private int nbMaxRent;
    /**
     * The creditCard number
     */
    private int numCard;
    /**
     * price of a rent by day
     */
    private final static int priceRent = 5;

    /**
     * Maximum day that creditCard can rent a dvd
     */
    private final static int maxRentDay = 30;

    private final static int timeRentOver = 30;

    public CreditCard(int numCard) {
        super();
        this.numCard = numCard;
        this.nbMaxRent = 1;
    }

    /**
     * Rent a dvd
     * @param dvd the dvd to rent
     * @return the generate rent
     * @throws RentException when max of rent number reached
     */
    @Override
    public Rent rentDvd(DvD dvd) throws RentException {
        if(this.onGoingRent.size() > nbMaxRent) throw new RentException(
                "Can't rent more than "+ nbMaxRent +" dvd in same time");

        Rent rent = new Rent(dvd);
        this.onGoingRent.add(rent);
        dvd.getFilm().rented(); //increment the views number of rented film
        return rent;
    }

    /**
     *  Check if the time rent is over and, if  <code>true, make client pay.
     * @throws RentException
     */
    @Override
    public void checkRentDate() throws RentException {
        for (Rent rent: this.onGoingRent) {
            if(getDateDiff(new Date(), rent.getDateRent(), TimeUnit.DAYS) > maxRentDay){
                this.pay(this.calcPrice(rent, priceRent) + timeRentOver);
            }
        }
    }

    /**
     * 
     * @return numCard
     */
    public int getNumCard() {
        return numCard;
    }

    /**
     * Return the dvd, and pay
     * @param rent the rent to return
     * @throws RentException
     * @throws CardException
     */
    @Override
    public void returnDvd(Rent rent) throws RentException, CardException {
        if(rent.isRentFinish()) throw new RentException(
                "Error when return the " + rent.getDvd().getFilm().getTitle() +
                            "ask to the boss"); //if dvd already returned
        this.onGoingRent.remove(rent); // remove the rent from the card
        rent.setDateReturn(); // set the return date
        rent.setPrice(this.calcPrice(rent, priceRent)); // calcul the price and save it in rent for history
        this.addHistory(rent); // add this rent to the history
        this.pay( //pay the rent, if dvd borken/missing pay extra charge (the price of the dvd)
                this.calcPrice(rent, priceRent) +
                        (rent.getDvd().getState() != State.Good ? rent.getDvd().getDvdPrice():0));
    }


    /**
     * Set the max rent number allowed by card.
     */
    @Override
    public void setMaximumRent(int maximumRent) {
        this.nbMaxRent = maximumRent;
    }

    /**
     * Simulate CB payment
     * @param money money to pay
     */
    public void pay(float money){}

    /**
     * check Equal between two CrediCard
     *
     * @param o the object to compare
     * @return <code>true</code> if object are equal, <code>false</code> otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreditCard)) return false;
        CreditCard that = (CreditCard) o;
        return getNumCard() == that.getNumCard();
    }

}
