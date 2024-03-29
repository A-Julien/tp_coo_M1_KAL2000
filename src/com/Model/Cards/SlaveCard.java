package com.Model.Cards;

import com.Model.Exception.CardException;
import com.Model.Exception.RentException;
import com.Model.Exception.SubCardException;
import com.Model.Movies.DvD;
import com.Model.Rents.Rent;
import com.Model.Movies.Category;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Slave Card are the second type of Sub Card.
 */
public class SlaveCard extends SubCard implements Serializable {

    /**
     * Categories that the SlaveCard are allowed to rent
     * If empty, SlaveCard are allow to rent any type of dvd
     */
    protected ArrayList<Category> categories;

    /**
     * Main card which create this slave card.
     */
    private MainCard mainCard;

    /**
     * Maximum rent that a SlaveCard can do.
     * By default set at nbMaxRent
     */
    private int maxRent;


    public SlaveCard(MainCard mainCard) {
        super(mainCard.getCreditCard());
        this.categories = new ArrayList<>();
        this.maxRent = nbMaxRent;
    }

    public MainCard getMainCard() {
        return this.mainCard;
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
     * @throws SubCardException the number of rent can be higher than nbMaxRent
     */
    public void setMaxRent(int maxRent) throws SubCardException {
        if (maxRent < 0) throw new NumberFormatException("Error number of rent can not be negative");
        if (maxRent > nbMaxRent) throw new SubCardException(
                "can't rent more than " + nbMaxRent + " dvd in same time");

        this.maxRent = maxRent;
    }
    /**
     * Return the number of maximum rent that can do the SlaveCard
     * @return the number of maximum rent
     */
    public int getMaxRent() {
        return maxRent;
    }

    /**
     * Return the authorized categories of the SlaveCard
     * @return ArrayList<> of Category
     */
    public ArrayList<Category> getCategories() {
        return categories;
    }

    /**
     * Allow a SlaveCard to rent a dvd
     * A SlaveCard can not have a negative credit.
     * If credit are not enough, the creditCard are use to regularized.
     * @param rent the rent to return
     * @throws CardException Error when return
     */
    @Override
    public void returnDvd(Rent rent) throws CardException, SubCardException, RentException {
        if(rent.isRentFinish()) throw new RentException(
                "Error when return the " + rent.getDvd().getFilm().getTitle() +
                            "ask to the boss");
        rent.setDateReturn();
        this.onGoingRent.remove(rent);
        rent.setPrice(this.calcPrice(rent, priceRent));
        this.addHistory(rent);
        this.updateDiscount();
        this.payRent(rent);

        if(this.getCredit() < 0){
            this.creditCard.pay(-this.getCredit());
            this.addCredit(-this.getCredit());
        }
    }

    /**
     * Limit authorized categories on a slave card
     * All categories are authorized if cats is null
     * @param cats the categories allowed
     */
    public void limitCategories(ArrayList<Category> cats){
        this.categories= cats;
    }

    @Override
    public String toString() {
        return "Id : "+ this.id +
                " | Nombre de locations en cours : " + this.onGoingRent.size() +
                " | Nombre de locations autorisées : " + this.getMaxRent();

    }
}
