package org.com.Model.Cards;

import org.com.Model.Rents.Rent;

import java.util.ArrayList;
import org.com.Model.Exception.CardException;

public interface RentHistorysable {

    ArrayList<Rent> getHistory();
    void addHistory(Rent rent) throws CardException;
}
