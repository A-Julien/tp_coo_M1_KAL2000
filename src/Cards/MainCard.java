package Cards;

import Errors.CardError;
import Errors.RentError;
import Errors.SubCardError;
import KAL2000.Rent;
import util.Category;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main Card,
 * Class that allow to create one type of subCard.
 * THis Sub Card are the master. She's allowed to create SlaveCard
 */
public class MainCard extends SubCard implements SlaveCardManager{

    /**
     * Slave Cards managed by MainCard
     */
    private ArrayList<SlaveCard> slaveCards;


    public MainCard(CreditCard creditCard) {
        super(creditCard);
        this.slaveCards = new ArrayList<>();
    }

    /**
     * Create a SlaveCard and add it into slaveCards
     * @return the created slaveCard
     */
    @Override
    public SlaveCard createSlaveCard(){
        SlaveCard slaveCard = new SlaveCard(this.slaveCards.size(), this.creditCard);
        this.slaveCards.add(slaveCard);
        return slaveCard;
    }

    /**
     * Remove a SlaveCard
     * Ensure :
     *  - Can not delete sub Card with on going rent
     *  - Pay negative credit
     * @param slaveCard The SlaveCard to remove
     * @throws SubCardError Can not delete sub Card with on going rent
     */
    @Override
    public void deleteSlaveCard(SlaveCard slaveCard) throws SubCardError {

        if(slaveCard.onGoingRent.size() > 0) throw new SubCardError(
                "Can not delete sub Card with on going rent");

        float slaveCredit;
        try {
            slaveCredit = slaveCard.getCredit();
        } catch (SubCardError subCardError) {
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
     * @throws SubCardError Can not delete sub card with on going rent
     */
    public void destroyMe() throws SubCardError {
        if(this.onGoingRent.size() > 0) throw new SubCardError(
                "Can not delete sub card with on going rent");

        for(SlaveCard slaveCard: this.slaveCards) this.deleteSlaveCard(slaveCard);

        if(this.getCredit() < 0) this.creditCard.pay(-this.getCredit());

    }

    /**
     * Allow MainCard to return a dvd
     *
     * @param rent the rent to return
     * @throws RentError Error when return
     * @throws CardError Can't add on going rent in history
     */
    @Override
    protected void returnDvd(Rent rent) throws RentError, CardError {
       /*if(rent.isRentFinish()) throw new RentError(
                "Error when return the " + rent.getDvd().getFilm().getTitre() +
                            "ask to the boss");*/
       this.onGoingRent.remove(rent);
       this.addHistory(rent);
       this.updateDiscount();
       this.payRent(rent);
    }

    @Override
    public void addCategoriesToSlaveCard(SlaveCard slaveCard, Category category){
        this.slaveCards.get(slaveCard.id).categories.add(category);
    }

    @Override
    public void addCategoriesToSlaveCard(SlaveCard slaveCard, ArrayList<Category> categories){
        for(Category category:categories) this.slaveCards.get(slaveCard.id).categories.add(category);
    }

    @Override
    public void setMaxRentToSlaveCard(SlaveCard slaveCard, int maxRentToSlaveCard) throws SubCardError {
        slaveCard.setMaxRent(maxRentToSlaveCard);
    }

    @Override
    public ArrayList<Rent> getSlaveCardHistory(SlaveCard slaveCard) {
        return slaveCards.get(slaveCard.id).getHistory();
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
}
