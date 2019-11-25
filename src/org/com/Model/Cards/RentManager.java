package org.com.Model.Cards;

import org.com.Model.Exception.*;
import org.com.Model.Exception.*;
import org.com.Model.Movies.DvD;
import org.com.Model.Rents.Rent;

import java.util.ArrayList;

public interface RentManager {

    Rent getRent(int iDdvd) throws RentException;
    Rent rentDvd(DvD dvd) throws RentException, StatusDvdException, SlaveCardException;
    void checkRentDate() throws RentException;
    void returnDvd(Rent rent) throws RentException, CardException, SubCardException;
    ArrayList<Rent> getOnGoingRent();
    void setMaximumRent(int maximumRent);
}
