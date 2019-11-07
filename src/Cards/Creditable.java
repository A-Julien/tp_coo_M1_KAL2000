package Cards;

import Errors.RentError;
import Errors.SubCardError;
import KAL2000.Rent;

/**
 * Allow a Card to have it own credit
 */
public interface Creditable {

    /**
     * Add money
     * @param money the money to add
     * @throws SubCardError
     */
    void refuelMoney(float money) throws SubCardError;

    /**
     * Pay a rent
     * @param rent the rent to pay
     * @throws RentError
     */
    void payRent(Rent rent) throws RentError;

    /**
     * This method are used for add negative credit
     * @param credit credit to add
     */
    void addCredit(float credit);

    /**
     * Set credit to 0
     */
    void initCredit();

    /**
     *
     * @param money
     */
    void pay(float money);
}
