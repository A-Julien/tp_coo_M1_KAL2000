import Cards.CreditCard;
import Cards.MainCard;
import Exception.*;
import KAL2000.DvD;
import KAL2000.Film;
import KAL2000.Kal2000;
import KAL2000.Rent;
import Ui.UserInterface;
import Util.Category;
import Util.Human;
import Util.Populator;
import Util.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;


public class Populate {

    public static void main(String[] args) throws RentException, InterruptedException, IOException, ClassNotFoundException {
    	boolean session  = false;
    	boolean admin = false;
    
    	//Initialisation
    	Kal2000 systeme = new Kal2000();
    	systeme.boot();

		systeme.addCLients(Populator.populateClient(300));
		HashMap<DvD, Integer> dvds = Populator.populateDvd(90);
		systeme.addDvds(dvds);
		systeme.addFilms(Populator.extractFilms(dvds));
		systeme.powerOff();
    }
}