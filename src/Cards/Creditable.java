package Cards;

import Exception.RentException;
import Exception.SubCardException;
import KAL2000.Rent;

/**
 * Allow a Card to have it own credit
 */
public interface Creditable {

    /**
     * Add money
     * @param money the money to add
     * @throws SubCardException
     */
    void refuelMoney(float money) throws SubCardException;

    /**
     * Pay a rent
     * @param rent the rent to pay
     * @throws RentException
     */
    void payRent(Rent rent) throws RentException;

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
