import Cards.Card;
import Exception.RentException;
import KAL2000.DvD;
import KAL2000.Film;
import KAL2000.Kal2000;
import KAL2000.Rent;
import Ui.UserInterface;
import Util.Category;
import Util.Human;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {

    public static void main(String[] args) throws RentException, InterruptedException, IOException, ClassNotFoundException {
    	boolean session  = false;
    	boolean admin = false;
    
    	//Initialisation
    	Kal2000 systeme = new Kal2000();
    	systeme.boot();
    	UserInterface ui = new UserInterface();
    	System.out.println("Êtes vous déja client ?\n Se logger : l \n Créer un compte : c\n Quitter : q");
    	
    	Scanner sc = new Scanner(System.in);
    	String command = sc.nextLine();
    	int id;
    	String pw,firstName,name;
    	
    	//Traitement de la commande
    	switch(command) {
    		case "l":
    			System.out.println("Entrez votre identifiant :");
    			id = Integer.parseInt(sc.nextLine());
    			System.out.println("Entrez votre mot de passe :");
    			pw = sc.nextLine();
    			ui.connect(id,pw,systeme);
    			if(ui.isAdmin()) {
    				admin = true;
    			}
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
    			systeme.powerOff();
    			System.exit(0);
    		break;
    	}
    	
    	//Interface administrateur
    	if(admin) {
        	while(session) {
        		System.out.println("Vous êtes administrateur");
        		System.out.println("Actions disponibles : \n Ajouter un film : af \n Retirer un film : rf \n Ajouter un dvd : ad \n Retirer un dvd : rd  \n Quitter : q ");
        		command = sc.nextLine();
        		switch(command) {
	        		case "af":
	        				
	        			System.out.println("Entrez l'id du film :");
	        			int idFilm = Integer.parseInt(sc.nextLine());
	        			
	        			System.out.println("Entrez le synopsis du film :");
	        			String synopsis = sc.nextLine();
	        			
	        			System.out.println("Entrez les acteurs principaux du film : ( s pour arrêter )");
	        			ArrayList<Human> actorsFilms= new ArrayList();
	        			boolean entreeEnCours= true;
	        			while(entreeEnCours) {
	        				String acteurs = sc.nextLine();
	        				if(acteurs.equals("s")) {
	        					entreeEnCours = false;
	        				}else {
	        					String[] nom = acteurs.split(" ");
	        					Human acteur= new Human(nom[0],nom[1]);
	        					actorsFilms.add(acteur);
	        				}
	        			}
	        			
	        			System.out.println("Entrez le réalisateur du film :");
	        			String real = sc.nextLine();
	        			String[] nom = real.split(" ");
    					Human realisateur= new Human(nom[0],nom[1]);
    					
	        			
	        			System.out.println("Entrez la catégorie principale du film : action,x,sf,fantasy,comedy,tragedy,romance");
	        			String category = sc.nextLine();
	        			Film film;
	        			switch(category) {
	        				case "action":
	        					film = new Film(idFilm,synopsis,actorsFilms,realisateur,Category.Action);
	        				break;
	        				case "x":
	        					film = new Film(idFilm,synopsis,actorsFilms,realisateur,Category.XxX);
		        			break;
	        				case "sf":
	        					film = new Film(idFilm,synopsis,actorsFilms,realisateur,Category.SF);
		        			break;
	        				case "fantasy":
	        					film = new Film(idFilm,synopsis,actorsFilms,realisateur,Category.Fantasy);
		        			break;
	        				case "comedy":
	        					film = new Film(idFilm,synopsis,actorsFilms,realisateur,Category.Comedy);
		        			break;
	        				case "tragedy":
	        					film = new Film(idFilm,synopsis,actorsFilms,realisateur,Category.Tragedy);
		        			break;
	        				case "romance":
	        					film = new Film(idFilm,synopsis,actorsFilms,realisateur,Category.Romance);
		        			break;
	        			}
	        		break;
	        		case "rf":

	        		break;
	        		case "ad":

	        		break;
	        		case "rd":

	        		break;
	        		case "q":
	        			session=false;
	        			systeme.powerOff();
	        			System.exit(0);
	        		break;
        		}

        		

        	}
    	}else {
        	while(session) {
        		System.out.println("Bienvenue "+ui.getConnectedClient().getPerson().getFirstName()+ " "+ ui.getConnectedClient().getPerson().getLastName());
        		
        		if(ui.getConnectedClient().isSub()) {
        			System.out.println("Vous êtes abonné.");
        			System.out.println("Actions disponibles : \n Louer un film : l \n Rendre un film : r \n Voir la liste des films disponibles : v \n"
        					+ " Gérer la carte sub : g \n Quitter : q");
            		command = sc.nextLine();
            		switch(command) {
            		
    	        		case "q":
    	        			session=false;
    	        			systeme.powerOff();
    	        			System.exit(0);
    	        		break;
            		}
        		}else {
        			System.out.println("Actions disponibles : \n mode carte sub: s \n mode location : l\n Quitter : q");
            		command = sc.nextLine();
            		switch(command) {
    	        		case "q":
    	        			session=false;
    	        			systeme.powerOff();
    	        			System.exit(0);
    	        		break;
            		}
        		}
        		

        	}
    	}

    	
    	
    }
}