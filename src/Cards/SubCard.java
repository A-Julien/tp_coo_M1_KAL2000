package Cards;

import Errors.SubCardError;
import Errors.RentError;
import KAL2000.DvD;
import KAL2000.Rent;
import KAL2000.RentDiscountable;

/**
 * Define SubCard
 */
public abstract class SubCard extends Card implements Creditable{
    private float credit;
    protected CreditCard creditCard;
    private final static int priceRent = 4;

    /**
     * if -1 no regularization are in course
     */
    private int periodRegularization;

    /**
     * Maximum rent that can do a SubCard in same time
     */
    protected final static int nbMaxRent = 3;

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


    public SubCard(CreditCard creditCard) {
        super();
        this.credit = 0;
        this.creditCard = creditCard;
        this.periodRegularization = -1;
    }

    /**
     * @return periodRegularization
     */
    public int getPeriodRegularization() {
        return periodRegularization;
    }


    /**
     *
     * @param periodRegularization
     */
    public void setPeriodRegularization(int periodRegularization) {
        this.periodRegularization = periodRegularization;
    }

    /**
     * Add money on credit.
     * The minimu amount to add money are {@link SubCard#refuelPriceLimit}
     * @param money the money to add
     * @throws SubCardError Can't add less than {@link SubCard#refuelPriceLimit}
     */
    @Override
    public void refuelMoney(float money) throws SubCardError {
        if(money < refuelPriceLimit) throw new SubCardError(
                "Can't add less than "+ refuelPriceLimit  +
                            " euros, you add " + money + " euros");

        this.credit += money;
    }

    /**
     * pay amount fo a rent
     * @param rent the rent to pay
     * @throws RentError
     */
    @Override
    public void payRent(Rent rent) throws RentError {
        this.credit += this.calcPrice(rent, priceRent);
    }

    /**
     * rent a dvd
     * Ensure :
     *  - Can't rent more than {@link SubCard#nbMaxRent}
     *  - Can't rent a dvd with less than {@link SubCard#rentPriceLimit}
     * @param dvd the dvd to rent
     * @return the rent
     * @throws RentError
     */
    @Override
    protected Rent rentDvd(DvD dvd) throws RentError {
        if(this.onGoingRent.size() > nbMaxRent) throw new RentError(
                "Can't rent more than "+ nbMaxRent +" dvd in same time");

        if(this.credit < rentPriceLimit) throw new RentError(
                "Can't rent a dvd with less than "+ rentPriceLimit +
                        "credits, please refuel before " +
                        "credit : " + this.credit + " euros");

        if( this instanceof SlaveCard &&
            !((SlaveCard)this).categories.isEmpty() &&
            !((SlaveCard)this).canIwatch(dvd)) throw new RentError(
                    "Category not allowed for this sub Card");

        if( this instanceof SlaveCard &&
                this.onGoingRent.size() >= ((SlaveCard)this).getMaxRent() ) throw new RentError(
                "Can not rent more than "  + ((SlaveCard)this).getMaxRent());

        Rent rent = new RentDiscountable(dvd);
        this.onGoingRent.add(rent);
        return rent;
    }


    /**
     * check if discount are available
     */
    protected void updateDiscount(){
        int counter = 0;

        for (Rent rent:this.getHistory())
            if(((RentDiscountable)rent).isDiscount()) counter++;

        if(counter == discountCount){
            this.credit += discountPrice;
            for (Rent rent:this.getHistory())
                if(((RentDiscountable)rent).isDiscount()) ((RentDiscountable)rent).setDiscount();
        }
    }

    /**
     * return the amount of credit
     * @return credit
     * @throws SubCardError
     */
    protected float getCredit() throws SubCardError {
        return this.credit;
        //if( this instanceof SlaveCard)return this.credit;

        //throw new SubCardError("Can not access to credit of MainCard");
    }

    @Override
    public void addCredit(float credit){
        this.credit += credit;
    }
}
