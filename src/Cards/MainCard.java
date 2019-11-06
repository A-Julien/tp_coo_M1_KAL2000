package Cards;

import Errors.RentError;
import Errors.SubCardError;
import KAL2000.Rent;
import util.Category;

import java.util.ArrayList;
import java.util.HashMap;

public class MainCard extends SubCard implements SlaveCardManager{

    private ArrayList<SlaveCard> slaveCards;


    public MainCard(CreditCard creditCard) {
        super(creditCard);
        this.slaveCards = new ArrayList<>();
    }

    @Override
    public SlaveCard createSlaveCard(){
        SlaveCard slaveCard = new SlaveCard(this.slaveCards.size(), this.creditCard);
        this.slaveCards.add(slaveCard);
        return slaveCard;
    }

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

    public void destroyMe() throws SubCardError {
        if(this.onGoingRent.size() > 0) throw new SubCardError(
                "Can not delete sub card with on going rent");

        for(SlaveCard slaveCard: this.slaveCards) this.deleteSlaveCard(slaveCard);

        if(this.getCredit() < 0) this.creditCard.pay(-this.getCredit());

    }

    @Override
    protected void returnDvd(Rent rent) throws RentError {
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
