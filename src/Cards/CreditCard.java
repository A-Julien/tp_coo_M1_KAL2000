package Cards;

import Exception.CardException;
import Exception.RentException;
import KAL2000.DvD;
import KAL2000.Rent;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Credit Card, allow to generate anonymous rent
 */
public class CreditCard extends Card implements Serializable {
    private int nbMaxRent;
    private final static int priceRent = 5;
    private final static int maxRentDay = 30;
    private final static int timeRentOver = 30;

    private int numCreditCard;

    public CreditCard(int numCreditCard) {
        this.numCreditCard = numCreditCard;
        this.nbMaxRent = 1;
    }

    public int getNumCreditCard() {
        return numCreditCard;
    }

    /**
     * Anonymous rent a dvd
     * @param dvd the dvd to rent
     * @return the generate rent
     * @throws RentException
     */
    @Override
    public Rent rentDvd(DvD dvd) throws RentException {
        if(this.onGoingRent.size() > nbMaxRent) throw new RentException(
                "Can't rent more than "+ nbMaxRent +" dvd in same time");

        Rent rent = new Rent(dvd);
        this.onGoingRent.add(rent);
        return rent;
    }

    @Override
    public void checkRentDate() throws RentException {
        for (Rent rent: this.onGoingRent) {
            if(getDateDiff(new Date(), rent.getDateRent(), TimeUnit.DAYS) > maxRentDay){
                this.pay(this.calcPrice(rent, priceRent) + timeRentOver);
            }
        }
    }

    /**
     * Return the dvd, and pay
     * @param rent the rent to return
     * @throws RentException
     * @throws CardException
     */
    @Override
    public void returnDvd(Rent rent) throws RentException, CardException {
        /*if(rent.isRentFinish()) throw new RentException(
                "Error when return the " + rent.getDvd().getFilm().getTitle() +
                            "ask to the boss");*/
        this.onGoingRent.remove(rent);
        this.addHistory(rent);
        this.pay(this.calcPrice(rent, priceRent));
    }

    @Override
    public void setMaximumRent(int maximumRent) {
        this.nbMaxRent = maximumRent;
    }

    /**
     * Simulate CB payment
     * @param money money to pay
     */
    public void pay(float money){}
}
