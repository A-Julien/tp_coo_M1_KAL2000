package Cards;

import KAL2000.Rent;

import java.util.ArrayList;

public class MainCard extends SubCard{

    private ArrayList<SlaveCard> slaveCards;

    public MainCard() {
        this.slaveCards = new ArrayList<>();
    }

    public SlaveCard createSlaveCard(){
        SlaveCard slaveCard = new SlaveCard();
        this.slaveCards.add(slaveCard);
        return slaveCard;
    }

    @Override
    protected void returnDvd(Rent rent) {

    }
}
