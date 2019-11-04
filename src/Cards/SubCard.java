package Cards;

import KAL2000.DvD;
import KAL2000.Rent;

public abstract class SubCard extends Card{
    private float credit;

    public SubCard() {
        super();
        this.credit = 0;
    }

    @Override
    protected Rent rentDvd(DvD dvd) {
        Rent rent = new Rent(dvd);
        this.history.add(rent);
        return rent;
    }
}
