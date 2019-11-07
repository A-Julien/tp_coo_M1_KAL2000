import Cards.Card;
import Exception.RentException;
import KAL2000.DvD;
import KAL2000.Kal2000;
import KAL2000.Rent;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws RentException, InterruptedException, IOException, ClassNotFoundException {
        DvD dvD = new DvD();
        Rent rent = new Rent(dvD);
        Thread.sleep(400);
        rent.setDateReturn();

        int i =  -2;

        System.out.println(Card.getDateDiff(rent.getDateRent(), rent.getDateReturn(), TimeUnit.DAYS));
        System.out.println(-i);

        Kal2000 kal2000 = new Kal2000();
        kal2000.boot();
        kal2000.powerOff();
    }
}
