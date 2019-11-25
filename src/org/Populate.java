package org;

import org.com.Model.Exception.RentException;
import org.com.Model.Movies.DvD;
import org.com.Controller.Kal2000;
import org.com.Utils.Populator;

import java.io.IOException;
import java.util.HashMap;


public class Populate {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
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