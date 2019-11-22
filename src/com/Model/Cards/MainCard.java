package com.Model.Cards;

import com.Model.Exception.CardException;
import com.Model.Exception.RentException;
import com.Model.Exception.SubCardException;
import com.Model.Rents.Rent;
import com.Model.Movies.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * com.Main Card,
 * Class that allow to create one type of subCard.
 * THis Sub Card are the master. She's allowed to create SlaveCard
 */
public class MainCard extends SubCard implements SlaveCardManager, Serializable {

    /**
     * Slave com.Model.Cards managed by MainCard
     */
    private ArrayList<SlaveCard> slaveCards;

    /**
     * if -1 no regularization are in course
     */
    private int periodRegularization;

    private final static int periodMaxDays = 30;


    public MainCard(CreditCard creditCard) {
        super(creditCard);
        this.slaveCards = new ArrayList<>();
        this.periodRegularization = -1;
    }

    /**
     * Create a SlaveCard and add it into slaveCards
     * @return the created slaveCard
     */
    @Override
    public SlaveCard createSlaveCard(){
        SlaveCard slaveCard = new SlaveCard(this);
        this.slaveCards.add(slaveCard);
        return slaveCard;
    }

    /**
     * Remove a SlaveCard
     * Ensure :
     *  - Can not delete sub Card with on going rent
     *  - Pay negative credit
     * @param slaveCard The SlaveCard to remove
     * @throws SubCardException Can not delete sub Card with on going rent
     */
    @Override
    public void deleteSlaveCard(SlaveCard slaveCard) throws SubCardException {

        if(slaveCard.onGoingRent.size() > 0) throw new SubCardException(
                "Can not delete sub Card with on going rent");

        float slaveCredit;
        try {
            slaveCredit = slaveCard.getCredit();
        } catch (SubCardException subCardException) {
            return;
        }

        if(slaveCredit < 0) this.creditCard.pay(-slaveCredit);
        this.addCredit(slaveCredit);

        this.slaveCards.remove(slaveCard);
    }


    /**
     * Remove the MainCard
     * Ensure :
     *  - Can not delete sub Card with on going rent
     *  - Pay negative credit
     *  - remove all SlaveCard by calling deleteSlaveCard()
     * @throws SubCardException Can not delete sub card with on going rent
     */
    public void destroyMe() throws SubCardException {
        if(this.onGoingRent.size() > 0) throw new SubCardException(
                "Can not delete sub card with on going rent");

        for(SlaveCard slaveCard: this.slaveCards) this.deleteSlaveCard(slaveCard);

        if(this.getCredit() < 0) this.creditCard.pay(-this.getCredit());

    }

    /**
     * Allow MainCard to return a dvd
     *
     * @param rent the rent to return
     * @throws RentException Error when return
     * @throws CardException Can't add on going rent in history
     */
    @Override
    public void returnDvd(Rent rent) throws RentException, CardException, SubCardException {
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
           this.periodRegularization = periodMaxDays;
       }
    }

    private boolean haveToBeRegularized() throws SubCardException {
        return this.getCredit() < 0 && this.periodRegularization != -1;
    }

    public void regularize() throws SubCardException {
        if(this.haveToBeRegularized()){
            this.periodRegularization -= 1;
            if(this.periodRegularization == 0){
                this.creditCard.pay(-this.getCredit());
                this.periodRegularization = -1;
                this.initCredit();
            }
            return;
        }

        if(this.getCredit() > 0) this.periodRegularization = -1;
    }

    @Override
    public void addCategoriesToSlaveCard(SlaveCard slaveCard, Category category){
        this.slaveCards.get(slaveCard.getId()).categories.add(category);
    }

    @Override
    public void addCategoriesToSlaveCard(SlaveCard slaveCard, ArrayList<Category> categories){
        for(Category category:categories) this.slaveCards.get(slaveCard.getId()).categories.add(category);
    }

    @Override
    public void setMaxRentToSlaveCard(SlaveCard slaveCard, int maxRentToSlaveCard) throws SubCardException {
        slaveCard.setMaxRent(maxRentToSlaveCard);
    }

    @Override
    public ArrayList<Rent> getSlaveCardHistory(SlaveCard slaveCard) {
        return slaveCards.get(slaveCard.getMaxRent()).getHistory();
    }

    @Override
    public HashMap<SlaveCard, ArrayList<Rent>> getSlaveCardHistory() {
        HashMap<SlaveCard, ArrayList<Rent>> history = new HashMap<>();

        for (SlaveCard slaveCard:slaveCards) {
            history.put(slaveCard, slaveCard.getHistory());
        }
        return history;
    }

    @Override
    public ArrayList<SlaveCard> getSlaveCards() {
        return this.slaveCards;
    }

    protected CreditCard getCreditCard(){
        return this.creditCard;
    }
}
