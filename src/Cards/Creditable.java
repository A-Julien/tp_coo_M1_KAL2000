package Cards;

import Errors.RentError;
import Errors.SubCardError;
import KAL2000.Rent;

public interface Creditable {

    void refuelMoney(float money) throws SubCardError;
    void payRent(Rent rent) throws RentError;
    void addCredit(float credit);

}
