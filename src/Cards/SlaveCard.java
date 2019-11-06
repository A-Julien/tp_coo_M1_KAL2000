package Cards;

import Errors.CardError;
import Errors.SubCardError;
import KAL2000.DvD;
import KAL2000.Rent;
import util.Category;

import java.util.ArrayList;

/**
 * Slave Card are the second type of Sub Card.
 */
public class SlaveCard extends SubCard {
    /**
     * id of slave Card
     */
    protected int id;

    /**
     * Categories that the SlaveCard are allowed to rent
     * If empty, SlaveCard are allow to rent any type of dvd
     */
    protected ArrayList<Category> categories;

    /**
     * Maximum rent that a SlaveCard can do.
     * By default set at nbMaxRent
     */
    private int maxRent;

    public SlaveCard(int id, CreditCard creditCard) {
        super(creditCard);
        this.id = id;
        this.categories = new ArrayList<>();
        this.maxRent = nbMaxRent;
    }

    /**
     * Return for a dvd if the SlaveCard can rent this type of dvd
     * @param dvd the dvd
     * @return True if can rent else False
     */
    protected boolean canIwatch(DvD dvd){
        for (Category category: dvd.getCategory()) if(this.categories.contains(category)) return false;
        return true;
    }

    /**
     * Set a maximum rent that a SlaveCard can do.
     * @param maxRent the number of rent
     * @throws SubCardError the number of rent can be higher than nbMaxRent
     */
    public void setMaxRent(int maxRent) throws SubCardError {
        if (maxRent < 0) throw new NumberFormatException("Error number of rent can not be negative");
        if (maxRent > nbMaxRent) throw new SubCardError(
                "can't rent more than " + nbMaxRent + " dvd in same time");

        this.maxRent = maxRent;
    }

    /**
     * Return the number of maximum rent that can do the SlaveCard
     * @return
     */
    public int getMaxRent() {
        return maxRent;
    }

    /**
     * ALlow a SlaveCard to rent a dvd
     * @param rent the rent to return
     * @throws CardError Error when return
     */
    @Override
    protected void returnDvd(Rent rent) throws CardError {
         /*if(rent.isRentFinish()) throw new RentError(
                "Error when return the " + rent.getDvd().getFilm().getTitre() +
                            "ask to the boss");*/
        this.onGoingRent.remove(rent);
        this.addHistory(rent);
        this.updateDiscount();
    }

}
