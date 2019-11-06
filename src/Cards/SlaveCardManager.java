package Cards;

import Errors.SubCardError;
import KAL2000.Rent;
import util.Category;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * API for manage SlavesCards
 */
public interface SlaveCardManager {

    /**
     * Allow to add a forbidden category to a slave card.
     * If the number of category are 0, the slave card are allow to rent all type
     * of dvd.
     * @param slaveCard the slave card
     * @param category the category to forbidden
     */
    void addCategoriesToSlaveCard(SlaveCard slaveCard, Category category);

    /**
     * Allow to add multiple forbidden category to a slave card.
     * If the number of category are 0, the slave card are allow to rent all type
     * of dvd.
     * @param slaveCard slaveCard the slave card
     * @param categories ArrayList<> of categories to add
     */
    void addCategoriesToSlaveCard(SlaveCard slaveCard, ArrayList<Category> categories);

    /**
     * Allow to manage number of maximal rent that can do a slave card
     * @param slaveCard  the slave card
     * @param maxRentToSlaveCard max rent that can do a slave card
     * @throws SubCardError
     */
    void setMaxRentToSlaveCard(SlaveCard slaveCard, int maxRentToSlaveCard) throws SubCardError;

    /**
     * Create a Slave Card
     * @return The created SlaveCard
     */
    SlaveCard createSlaveCard();

    /**
     * Remove a SlaveCard
     * @param slaveCard The SlaveCard to remove
     * @throws SubCardError
     */
    void deleteSlaveCard(SlaveCard slaveCard) throws SubCardError;

    /**
     * Get the history of specific SlaveCard
     * @param slaveCard the slaveCard
     * @return ArrayList of Rent
     */
    ArrayList<Rent> getSlaveCardHistory(SlaveCard slaveCard);

    /**
     * Get all history of all SlaveCard
     * @return HashMap<SlaveCard,ArrayList<Rent>>
     */
    HashMap<SlaveCard,ArrayList<Rent>> getSlaveCardHistory();

    /**
     * Return all SlaveCard
     * @return ArrayList of SlaveCard
     */
    ArrayList<SlaveCard> getSlaveCards();
}
