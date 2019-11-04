package Cards;

import KAL2000.DvD;
import KAL2000.Rent;

import java.util.ArrayList;

public abstract class Card {

    protected ArrayList<Rent> history;

    public Card() {
        this.history = new ArrayList<>();
    }

    protected ArrayList<Rent> getHistory(){
        return this.history;
    }

    protected abstract Rent rentDvd(DvD dvd);
    protected abstract void returnDvd(Rent rent);
}
