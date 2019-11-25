package org.com.Model.Cards;

import org.com.Model.Exception.CardException;
import org.com.Model.Exception.RentException;
import org.com.Model.Exception.SubCardException;
import org.com.Model.Rents.Rent;
import org.com.Model.Movies.Category;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * com.org.Main Card,
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

    /**
     * max period to return a dvd
     */
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
        slaveCredit = slaveCard.getCredit();

        if(slaveCredit < 0) this.creditCard.pay(-slaveCredit);
        this.addCredit(slaveCredit);

        this.slaveCards.remove(slaveCard);
    }

    /**
     * Remove a SlaveCard
     * Ensure :
     *  - Can not delete sub Card with on going rent
     *  - Pay negative credit
     * @param id id of The SlaveCard to remove
     * @throws SubCardException Can not delete sub Card with on going rent
     */
    @Override
    public void deleteSlaveCardById(int id) throws SubCardException {
        for (SlaveCard slaveCard : this.slaveCards){
            if(slaveCard.getId() == id){
                this.deleteSlaveCard(slaveCard);
                return;
            }
        }

        throw new SubCardException("Can not find subCard with id : " + id);
    }

    /**
     * return a SlaveCard
     * Ensure :
     *  - Can not delete sub Card with on going rent
     *  - Pay negative credit
     * @param id The SlaveCard to remove
     * @throws SubCardException Can not delete sub Card with on going rent
     */
    @Override
    public SlaveCard getSlaveCardById(int id) throws SubCardException {
        for (SlaveCard slaveCard : this.slaveCards){
            if(slaveCard.getId() == id){
                return slaveCard;
            }
        }

        throw new SubCardException("Can not find subCard with id : " + id);
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
    public void returnDvd(Rent rent) throws RentException, CardException{
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

    /**
     * Check is a main card have to be regularized.
     * @return <code>true</code> if it has to be, <code>false</code> otherwise
     */
    private boolean haveToBeRegularized() {
        return this.getCredit() < 0 && this.periodRegularization != -1;
    }

    /**
     * Check if client's maincard has to be regularized, if  <code>true, make him pay.
     */
    public void regularize() {
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

    /**
     * Add one forbidden categorie to a slave card which allow it to see them.
     */
    @Override
    public void addCategoriesToSlaveCard(SlaveCard slaveCard, Category category){
        this.slaveCards.get(slaveCard.getId()).categories.add(category);
    }

    /**
     * Add forbidden categories to a slave card which allow it to see them.
     */
    @Override
    public void addCategoriesToSlaveCard(SlaveCard slaveCard, ArrayList<Category> categories){
        for(Category category:categories) this.slaveCards.get(slaveCard.getId()).categories.add(category);
    }

    /**
     * Set the max rent number a slave card has.
     */
    @Override
    public void setMaxRentToSlaveCard(SlaveCard slaveCard, int maxRentToSlaveCard) throws SubCardException {
        slaveCard.setMaxRent(maxRentToSlaveCard);
    }

    /**
     * Get history of a slave card.
     */
    @Override
    public ArrayList<Rent> getSlaveCardHistory(SlaveCard slaveCard) {
        return slaveCards.get(slaveCard.getMaxRent()).getHistory();
    }

    /**
     *  Get history of all slaves cards.
     */
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
