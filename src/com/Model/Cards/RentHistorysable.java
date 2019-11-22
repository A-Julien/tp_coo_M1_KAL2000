package com.Model.Cards;

import com.Model.Rents.Rent;

import java.util.ArrayList;
import com.Model.Exception.CardException;

public interface RentHistorysable {

    ArrayList<Rent> getHistory();
    void addHistory(Rent rent) throws CardException;
}
