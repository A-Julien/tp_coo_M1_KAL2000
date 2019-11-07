package Cards;

import KAL2000.Rent;

import java.util.ArrayList;
import Exception.CardException;

public interface RentHistorysable {

    ArrayList<Rent> getHistory();
    void addHistory(Rent rent) throws CardException;
}
