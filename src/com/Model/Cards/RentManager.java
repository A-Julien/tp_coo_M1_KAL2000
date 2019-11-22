package com.Model.Cards;

import com.Model.Movies.DvD;
import com.Model.Rents.Rent;
import com.Model.Exception.RentException;
import com.Model.Exception.CardException;
import com.Model.Exception.SubCardException;
import com.Model.Exception.StatusDvdException;

import java.util.ArrayList;

public interface RentManager {

    Rent getRent(int iDdvd) throws RentException;
    Rent rentDvd(DvD dvd) throws RentException, StatusDvdException;
    void checkRentDate() throws RentException;
    void returnDvd(Rent rent) throws RentException, CardException, SubCardException;
    ArrayList<Rent> getOnGoingRent();
    void setMaximumRent(int maximumRent);
}
