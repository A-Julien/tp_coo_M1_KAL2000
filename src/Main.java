import Cards.Card;
import Errors.RentError;
import KAL2000.DvD;
import KAL2000.Rent;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws RentError, InterruptedException {
        DvD dvD = new DvD();
        Rent rent = new Rent(dvD);
        Thread.sleep(400);
        rent.setDateReturn();

        int i =  -2;

        System.out.println(Card.getDateDiff(rent.getDateRent(), rent.getDateReturn(), TimeUnit.DAYS));
        System.out.println(-i);
    }
}
