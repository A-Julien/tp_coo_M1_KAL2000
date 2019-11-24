package com.Model.Cards;

import com.Model.Exception.RentException;
import com.Model.Exception.SlaveCardException;
import com.Model.Exception.SubCardException;
import com.Model.Exception.StatusDvdException;
import com.Model.Exception.SlaveCardException;

import com.Model.Movies.DvD;
import com.Model.Rents.Rent;
import com.Model.Rents.RentDiscountable;
import com.Model.Movies.State;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Define SubCard
 */
public abstract class SubCard extends Card implements Creditable, Serializable {
    private float credit;
    protected CreditCard creditCard;
    protected final static int priceRent = 4;

    /**
     * Maximum rent that can do a SubCard in same time
     */
    protected int nbMaxRent;

    /**
     * minimum amount fot refuel credit
     */
    private final static int refuelPriceLimit = 10;

    /**
     * minimum amount of credit to do a rent
     */
    private final static int rentPriceLimit = 15;

    /**
     * each discountCount days, earn amount of discountPrice
     */
    private final static int discountPrice = 10;

    /**
     * each discountCount days, earn amount of discountPrice
     */
    private final static int discountCount = 20;

    /**
     * Maximum day that subcard can rent a dvd
     */
    private final static int maxRentDay = 30;

    private final static int timeRentOver = 30;



    public SubCard(CreditCard creditCard) {
        super();
        this.credit = 0;
        this.creditCard = creditCard;
        this.nbMaxRent = 3;
    }
    
    /**
     *  Check if the time rent is over and, if  <code>true</code>, make client pay.
     * @throws RentException if rent is not over
     */
    public void checkRentDate() throws RentException {
        for (Rent rent: this.onGoingRent) {
            if(getDateDiff(new Date(), rent.getDateRent(), TimeUnit.DAYS) > maxRentDay){
                this.payRent(rent);
                this.pay(timeRentOver);
            }
        }
    }

    /**
     * Add money on credit.
     * The minimu amount to add money are {@link SubCard#refuelPriceLimit}
     * @param money the money to add
     * @throws SubCardException Can't add less than {@link SubCard#refuelPriceLimit}
     */
    @Override
    public void refuelMoney(float money) throws SubCardException {
        if(money < refuelPriceLimit) throw new SubCardException( //check the limit
                "Can't add less than "+ refuelPriceLimit  +
                            " euros, you add " + money + " euros");

        this.credit += money; // add money
    }

    /**
     * pay amount fo a rent
     * @param rent the rent to pay
     * @throws RentException if rent is not over
     */
    @Override
    public void payRent(Rent rent) throws RentException {
        this.credit -= this.calcPrice(rent, priceRent);
        if(rent.getDvd().getState() != State.Good) this.credit -= rent.getDvd().getDvdPrice();
    }

    /**
     * rent a dvd
     * Ensure :
     *  - Can't rent more than {@link SubCard#nbMaxRent}
     *  - Can't rent a dvd with less than {@link SubCard#rentPriceLimit}
     * @param dvd the dvd to rent
     * @return the rent
     * @throws RentException is rent is not over
     * @throws StatusDvdException if dvd are broken or missing
     */
    @Override
    public Rent rentDvd(DvD dvd) throws RentException, StatusDvdException, SlaveCardException {
        if(dvd.getState() != State.Good) throw new StatusDvdException("Dvd are broken or missing");

        if(this.onGoingRent.size() > nbMaxRent) throw new RentException( // check number of on going rent
                "Can't rent more than "+ nbMaxRent +" dvd in same time");

        if(this.credit < rentPriceLimit) throw new RentException( // check if enough money to rent a dvd
                "Can't rent a dvd with less than "+ rentPriceLimit +
                        "credits, please refuel before. " +
                        "Actual credit : " + this.credit + " euros");

        if( this instanceof SlaveCard && // if is a slave card, need to check if allow to rent dvd Category
            !((SlaveCard)this).categories.isEmpty() &&
            !((SlaveCard)this).canIwatch(dvd)) throw new SlaveCardException(
                    "Category not allowed for this sub Card");

        if( this instanceof SlaveCard && // if is a slave card, check is own rent limit
                this.onGoingRent.size() >= ((SlaveCard)this).getMaxRent() ) throw new RentException(
                "Can not rent more than "  + ((SlaveCard)this).getMaxRent());

        Rent rent = new RentDiscountable(dvd); // check if a discount are available
        this.onGoingRent.add(rent); // adding the rent to the card
        dvd.getFilm().rented(); //for history
        return rent;
    }


    /**
     * check if discount are available
     */
    protected void updateDiscount(){
        int counter = 0;

        for (Rent rent:this.getHistory()) if(((RentDiscountable)rent).isDiscount()) counter++;

        if(counter == discountCount){
            this.credit += discountPrice;
            for (Rent rent:this.getHistory())
                if(((RentDiscountable)rent).isDiscount()) ((RentDiscountable)rent).setDiscount();
        }
    }

    /**
     * return the amount of credit
     * @return credit
     */
    public float getCredit() {
        return this.credit;
    }

    @Override
    public void addCredit(float credit){
        this.credit += credit;
    }

    /**
     * Initiathe credit to 0.
     */
    @Override
    public void initCredit(){
        this.credit = 0;
    }

    /**
     * Simulate a payment.
     */
    @Override
    public void pay(float money){
        this.credit -=money;
    }

    /**
     * Set the max rent number.
     */
    @Override
    public void setMaximumRent(int maxRent){
        this.nbMaxRent = maxRent;
    }
}
