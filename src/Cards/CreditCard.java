package Cards;

import Errors.RentError;
import KAL2000.DvD;
import KAL2000.Rent;

import java.util.concurrent.TimeUnit;

public class CreditCard extends Card {
    private final static int nbMaxRent = 1;
    private final static int priceRent = 5;

    @Override
    protected float calcPrice(Rent rent) throws RentError{
        long dayOfRent = 0;
        dayOfRent = Card.getDateDiff(rent.getDateRent(), rent.getDateReturn(), TimeUnit.DAYS);

        return dayOfRent * priceRent;
    }

    @Override
    protected Rent rentDvd(DvD dvd) throws RentError {
        if(this.onGoingRent.size() > nbMaxRent) throw new RentError(
                "Can't rent more than "+ nbMaxRent +" dvd in same time");

        Rent rent = new Rent(dvd);
        this.onGoingRent.add(rent);
        return rent;
    }

    @Override
    protected void returnDvd(Rent rent) throws RentError{
        /*if(rent.isRentFinish()) throw new RentError(
                "Error when return the " + rent.getDvd().getFilm().getTitre() +
                            "ask to the boss");*/
        this.onGoingRent.remove(rent);
        this.addHistory(rent);
        this.pay(this.calcPrice(rent));
    }

    public void pay(float money){}
}
