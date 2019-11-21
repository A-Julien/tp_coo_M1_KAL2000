package Cards;

import KAL2000.DvD;
import KAL2000.Rent;
import Exception.RentException;
import Exception.CardException;
import Exception.SubCardException;
import Exception.StatusDvdException;
import Util.State;

import java.util.ArrayList;

public interface RentManager {

    Rent getRent(int iDdvd) throws RentException;
    Rent rentDvd(DvD dvd) throws RentException, StatusDvdException;
    void checkRentDate() throws RentException;
    void returnDvd(Rent rent) throws RentException, CardException, SubCardException;
    ArrayList<Rent> getOnGoingRent();
    void setMaximumRent(int maximumRent);
}
