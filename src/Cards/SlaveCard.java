package Cards;

import Errors.SubCardError;
import KAL2000.DvD;
import KAL2000.Rent;
import util.Category;

import java.util.ArrayList;

public class SlaveCard extends SubCard {
    protected int id;
    protected ArrayList<Category> categories;
    private int maxRent;

    public SlaveCard(int id, CreditCard creditCard) {
        super(creditCard);
        this.id = id;
        this.categories = new ArrayList<>();
        this.maxRent = nbMaxRent;
    }

    protected boolean canIwatch(DvD dvd){
        for (Category category: dvd.getCategory()) if(this.categories.contains(category)) return false;
        return true;
    }

    public void setMaxRent(int maxRent) throws SubCardError {
        if (maxRent < 0) throw new NumberFormatException("Error number of rent can not be negative");
        if (maxRent > nbMaxRent) throw new SubCardError(
                "can't rent more than " + nbMaxRent + " dvd in same time");

        this.maxRent = maxRent;
    }

    public int getMaxRent() {
        return maxRent;
    }

    @Override
    protected void returnDvd(Rent rent) {
         /*if(rent.isRentFinish()) throw new RentError(
                "Error when return the " + rent.getDvd().getFilm().getTitre() +
                            "ask to the boss");*/
        this.onGoingRent.remove(rent);
        this.addHistory(rent);
        this.updateDiscount();
    }

}
