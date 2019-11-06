package Cards;

import Errors.SubCardError;
import KAL2000.Rent;
import util.Category;

import java.util.ArrayList;
import java.util.HashMap;

public interface SlaveCardManager {

    void addCategoriesToSlaveCard(SlaveCard slaveCard, Category category);
    void addCategoriesToSlaveCard(SlaveCard slaveCard, ArrayList<Category> categories);
    void setMaxRentToSlaveCard(SlaveCard slaveCard, int maxRentToSlaveCard) throws SubCardError;

    SlaveCard createSlaveCard();
    void deleteSlaveCard(SlaveCard slaveCard) throws SubCardError;

    ArrayList<Rent> getSlaveCardHistory(SlaveCard slaveCard);
    HashMap<SlaveCard,ArrayList<Rent>> getSlaveCardHistory();

    ArrayList<SlaveCard> getSlaveCards();
}
