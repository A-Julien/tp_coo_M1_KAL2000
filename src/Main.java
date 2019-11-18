import Cards.Card;
import Exception.RentException;
import KAL2000.DvD;
import KAL2000.Kal2000;
import KAL2000.Rent;
import Ui.UserInterface;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {
	*;lol

    public static void main(String[] args) throws RentException, InterruptedException, IOException, ClassNotFoundException {
    	boolean session  = false;
    

    	Kal2000 systeme = new Kal2000();
    	systeme.boot();
    	UserInterface ui = new UserInterface();
    	System.out.println("Êtes vous déja client ?\n Se logger : l \n Créer un compte : c\n Quitter : q");
    	
    	Scanner sc = new Scanner(System.in);
    	String command = sc.nextLine();
    	int id;
    	String pw,firstName,name;
    	switch(command) {
    		case "l":
    			System.out.println("Entrez votre identifiant :");
    			id = Integer.parseInt(sc.nextLine());
    			System.out.println("Entrez votre mot de passe :");
    			pw = sc.nextLine();
    			ui.connect(id,pw,systeme);
    			session= true;
    		break;
    		
    		case "c":
    			System.out.println("Entrez votre prénom :");
    			firstName = sc.nextLine();
    			System.out.println("Entrez votre nom :");
    			name = sc.nextLine();
    			System.out.println("Entrez votre mot de passe :");
    			pw = sc.nextLine();
    			System.out.println("Entrez le numéro de votre carte :");
    			id = Integer.parseInt(sc.nextLine());
    			ui.createClient(firstName,name,pw,id,systeme);
    			
    			ui.connect(id,pw,systeme);
    			session= true;
    			
    		break;
    		
    		case "q":
    			System.out.println("Déconnexion...");
    			System.exit(0);
    		break;
    	}
    	
    	while(session) {
    		
    		
    		command = sc.nextLine();
    		switch(command) {
    		
    		}
    	}
    	
    	
    }
}