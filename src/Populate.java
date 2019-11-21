import Exception.*;
import KAL2000.DvD;
import KAL2000.Kal2000;
import Util.Populator;

import java.io.IOException;
import java.util.HashMap;


public class Populate {

    public static void main(String[] args) throws RentException, InterruptedException, IOException, ClassNotFoundException {
    	boolean session  = false;
    	boolean admin = false;
    
    	//Initialisation
    	Kal2000 systeme = new Kal2000();
    	systeme.boot();

		systeme.addCLients(Populator.populateClient(10));
		HashMap<DvD, Integer> dvds = Populator.populateDvd(10);
		systeme.addDvds(dvds);
		systeme.addFilms(Populator.extractFilms(dvds));
		systeme.powerOff();
    }
}