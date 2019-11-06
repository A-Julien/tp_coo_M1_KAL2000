package Cards;

import Errors.SubCardError;
import Errors.RentError;
import KAL2000.DvD;
import KAL2000.Rent;
import KAL2000.RentDiscountable;

import java.util.concurrent.TimeUnit;

public abstract class SubCard extends Card implements Creditable{
    private float credit;
    protected CreditCard creditCard;
    private final static int price = 4;
    private int periodRegularization;

    protected final static int nbMaxRent = 3;
    private final static int refuelPriceLimit = 10;
    private final static int rentPriceLimit = 15;
    private final static int discountPrice = 10;
    private final static int discountCount = 20;


    public SubCard(CreditCard creditCard) {
        super();
        this.credit = 0;
        this.creditCard = creditCard;
        this.periodRegularization = -1;
    }

    public int getPeriodRegularization() {
        return periodRegularization;
    }

    public void setPeriodRegularization(int periodRegularization) {
        this.periodRegularization = periodRegularization;
    }

    @Override
    public void refuelMoney(float money) throws SubCardError {
        if(money < refuelPriceLimit) throw new SubCardError(
                "Can't add less than "+ refuelPriceLimit  +
                            " euros, you add " + money + " euros");

        this.credit += money;
    }

    @Override
    public void payRent(Rent rent) throws RentError {
        this.credit += this.calcPrice(rent);
    }

    @Override
    protected float calcPrice(Rent rent) throws RentError {
        long dayOfRent = Card.getDateDiff(rent.getDateRent(), rent.getDateReturn(), TimeUnit.DAYS);
        return dayOfRent * price;
    }

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
