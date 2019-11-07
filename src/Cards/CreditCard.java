package Cards;

import Errors.CardError;
import Errors.RentError;
import KAL2000.DvD;
import KAL2000.Rent;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Credit Card, allow to generate anonymous rent
 */
public class CreditCard extends Card implements Serializable {
    private final static int nbMaxRent = 1;
    private final static int priceRent = 5;
    private final static int maxRentDay = 30;
    private final static int timeRentOver = 30;

    private int numCreditCard;

    public CreditCard(int numCreditCard) {
        this.numCreditCard = numCreditCard;
    }

    public int getNumCreditCard() {
        return numCreditCard;
    }

    /**
     * Anonymous rent a dvd
     * @param dvd the dvd to rent
     * @return the generate rent
     * @throws RentError
     */
    @Override
    protected Rent rentDvd(DvD dvd) throws RentError {
        if(this.onGoingRent.size() > nbMaxRent) throw new RentError(
                "Can't rent more than "+ nbMaxRent +" dvd in same time");

        Rent rent = new Rent(dvd);
        this.onGoingRent.add(rent);
        return rent;
    }

    @Override
    public void checkRentDate() throws RentError {
        for (Rent rent: this.onGoingRent) {
            if(getDateDiff(new Date(), rent.getDateRent(), TimeUnit.DAYS) > maxRentDay){
                this.pay(this.calcPrice(rent, priceRent) + timeRentOver);
            }
        }
    }

    /**
     * Return the dvd, and pay
     * @param rent the rent to return
     * @throws RentError
     * @throws CardError
     */
    @Override
    protected void returnDvd(Rent rent) throws RentError, CardError {
        /*if(rent.isRentFinish()) throw new RentError(
                "Error when return the " + rent.getDvd().getFilm().getTitre() +
                            "ask to the boss");*/
        this.onGoingRent.remove(rent);
        this.addHistory(rent);
        this.pay(this.calcPrice(rent, priceRent));
    }

    /**
     * Simulate CB payment
     * @param money money to pay
     */
    public void pay(float money){}
}
