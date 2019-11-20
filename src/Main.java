import Cards.Card;
import Cards.CreditCard;
import Cards.MainCard;
import Cards.SubCard;
import Exception.CardException;
import Exception.FilmException;
import Exception.PasswordException;
import Exception.RentException;
import Exception.StatusDvdException;
import Exception.SubCardException;
import Exception.SystemException;
import KAL2000.DvD;
import KAL2000.Film;
import KAL2000.Kal2000;
import KAL2000.Rent;
import Ui.UserInterface;
import Util.Category;
import Util.Human;
import Util.State;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
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
    	
    	Scanner sc = new Scanner(System.in);
    	
    	while(true) {
	    	System.out.println("Êtes vous déja client ?\n Se logger : l \n Créer un compte : c\n Quitter : q");
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
	    			try {
	    				ui.connect(id,pw,systeme);
	    			}catch(PasswordException|CardException e) {
	    				e.printStackTrace();
	    			}
	    			
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
	    			try {
	    				ui.connect(id,pw,systeme);
	    			}catch(PasswordException|CardException e) {
	    				e.printStackTrace();
	    			}
	    			
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
	        		System.out.println("Actions disponibles : \n Ajouter un film : af \n Retirer un film : rf \n"
	        				+ " Ajouter un dvd : ad \n Retirer un dvd : rd  \n Se déconnecter : d\n Quitter : q ");
	        		command = sc.nextLine();
	        		switch(command) {
	        		case "d":
	        			session = false;
	        			System.out.println("Déconnexion ...");
	        			ui.disconnect();
	        		break;
	        			//Ajout d'un film
	        		case "af":
	        			//Choix des attributs du film
	        			System.out.println("Entrez le titre du film :");
	        			String titre = sc.nextLine();

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
						
	        			
	        			System.out.println("Entrez la catégorie principale du film : Action,XxX,SF,Fantasy,Comedy,Tragedy,Romance");
	        			String category = sc.nextLine();

	        			//Ajout a la base
	        			try {
		        			systeme.addFilm( new Film(titre,synopsis,actorsFilms,realisateur,Category.valueOf(category)));
		        			System.out.println("Film ajouté");
	        			}catch(FilmException e) {
	        				e.printStackTrace();
	        			}
	        			
	        		break;
	        		//Supprimer un film
	        		case "rf":
	        			//Affichage de la liste des films pour la sélection de l'id
	        			System.out.println("Liste des films :");
	        			for(int i=0;i<systeme.getFilms6beerVideo().size();i++) {
	        				//TODO tostring film
	        				System.out.println(systeme.getFilms6beerVideo().get(i).getTitle() + " : " + systeme.getFilms6beerVideo().get(i).getId());
	        			}
	
	        			System.out.println("Entrez l'identifiant du film à supprimer :");
	        			int idRemoveFilm = Integer.parseInt(sc.nextLine());

	        			//Recherche de l'index du film a supprimer
	        			for(int i=0;i<systeme.getFilms6beerVideo().size();i++) {
	        				if(systeme.getFilms6beerVideo().get(i).getId()==idRemoveFilm) {
	        					if(systeme.removeFilm(systeme.getFilms6beerVideo().get(i)))
									System.out.println("Film supprimé");
	        					else
									System.out.println("Film non trouvé !");
	        					break;
	        				}
	        			}


	        		break;
	        		//Ajout d'un DVD
	        		case "ad":
	        			System.out.println("Entrez l'id du film présent sur le DVD");
	        			int idFilmDvd = Integer.parseInt(sc.nextLine());
	        			int indexFilmDvd = -1;
	        			for(int i=0;i<systeme.getFilms6beerVideo().size();i++) {
	        				if(systeme.getFilms6beerVideo().get(i).getId()==idFilmDvd) {
	        					indexFilmDvd=i;
	        				}
	        			}
	        			Film filmDvd=null;
	        			if(indexFilmDvd != -1) {
	        				filmDvd = systeme.getFilms6beerVideo().get(indexFilmDvd);	
	        			}else {
	        				System.out.println("Film non trouvé !");
	        				break;
	        			}
	        			System.out.println("Entrez l'id du DVD");
	        			int idDvd = Integer.parseInt(sc.nextLine());
	        			
		        		systeme.addDvd(new DvD(filmDvd,State.Good,idDvd));
		        		System.out.println("Dvd ajouté");
	
	
	        		break;
	        		//Suppression d'un DVD
	        		case "rd":
	        			System.out.println("Entrez l'id du DVD à supprimer");
	        			int idDvdRemove = Integer.parseInt(sc.nextLine());
	        			
	        			Iterator<DvD> it = systeme.getDvds().keySet().iterator();
	        			DvD toRemove= null;
	        			while(it.hasNext()) {
	        				DvD current= it.next();
	        				if(current.getId()==idDvdRemove) {
	        					toRemove= current;
	        				}
	        			}
	        			if(toRemove!=null) {
		        			systeme.removeDvd(toRemove);
		        			System.out.println("Dvd supprimé !");
	        			}else {
	        				System.out.println("Dvd non trouvé !");
	        			}
	
	
	        		break;
	        		case "q":
	        			session=false;
	        			systeme.powerOff();
	        			System.exit(0);
	        		break;
	        		}
	        	}
	        //Interface Client
	    	}else {
	    		boolean sessionClient1=true;
	        	while(sessionClient1) {
	        		System.out.println("Bienvenue "+ui.getConnectedClient().getPerson().getFirstName()+ " "+ ui.getConnectedClient().getPerson().getLastName());
	        		//Interface Sub
	        		if(ui.getConnectedClient().isSub()) {
	        			System.out.println("Vous êtes abonné.");
	        			System.out.println("Actions disponibles : \n Louer un dvd : l \n Rendre un dvd : r \n Voir la liste des dvds : v \n"
	        					+ " Gérer la carte sub : g \n Se déconnecter : d\n Quitter : q");
	            		command = sc.nextLine();
	            		switch(command) {
	            		
	            		//Louer un dvd
	            		case "l" :
	            			System.out.println("Choisissez un id de dvd parmis les suivants : ");
	        				Iterator<DvD> it = systeme.getDvds().keySet().iterator();
		        			while(it.hasNext()) {
		        				DvD current= it.next();
		        				System.out.println(current.getFilm().getTitle()+" : "+current.getFilm().getId());
		        			}
		        			int idChosen= Integer.parseInt(sc.nextLine());
		        			
		        			it = systeme.getDvds().keySet().iterator();
		        			
		        			try {
		        				DvD toRent= systeme.getDvdById(idChosen);
		        				ui.getConnectedCard().rentDvd(toRent);
		        			}catch(RentException|StatusDvdException|SystemException e) {
		        				e.printStackTrace();
		        			}
		        			System.out.println("Dvd loué");
	            		break;
	            		//Rendre un dvd
	            		case "r" :
	            			System.out.println("Vos locations en cours : ");
	            			ArrayList<Rent> rents = ui.getConnectedCard().getOnGoingRent();
	            			if(rents==null) {
	            				System.out.println("Vous n'avez pas de dvd à rendre");
	            				break;
	            			}
	            			//Affichage des locations en cours
	        				for(int i=0; i<rents.size();i++) {
	        					System.out.println(rents.get(i).getDvd().getFilm().getTitle()+" : "+rents.get(i).getDvd().getId());
	        				}

	        				System.out.println("Entrez l'id du dvd que vous rendez : ");
	        				int idReturn = Integer.parseInt(sc.nextLine());
	        				Rent rent= null;

							int i=0;
	        				while (i < rents.size() && rents.get(i).getDvd().getId() != idReturn){ i++; }

							try {
								rent = rents.get(i);
							}catch (IndexOutOfBoundsException e){
								System.out.println("System error dvd not found");
								break;
							}

							System.out.println("Entrez l'etat du dvd que vous rendez : Broken|Good|Missing ");
							State state = State.valueOf(sc.nextLine());
							rent.getDvd().setState(state);
	        				try {
	            				ui.getConnectedCard().returnDvd(rent);
	        				}catch(RentException|CardException|SubCardException e) {
	        					e.printStackTrace();
	        				}
	        				System.out.println("Dvd rendu");
	            		break;
	            		//Gestion de la carte sub
	            		case "g" :
	            			boolean gestionEnCours=true;
	            			while(gestionEnCours) {
	            				System.out.println("Actions disponibles : \n Voir le solde : v \n Recharger la carte : r \n Créer une carte fille : c "
	            						+ "\n Gérer cartes filles : g \n Retour : b");
	    	            		command = sc.nextLine();
	    	            		switch(command) {
	    	            		
	    	            		
	    	            		
	    	            		//Afficher le solde de la carte
	    	            		case "v" :
	    	            			try {
	    	            				System.out.println("Solde actuel : "+((SubCard)ui.getConnectedCard()).getCredit()+" euros");
	    	            			}catch(SubCardException e) {
		    	            			e.printStackTrace();
	    	            			}
	    	            		break;	    	       

	    	            		//Rechargement du solde
	    	            		case "r" :
	    	            			System.out.println("Entrez le montant que vous voulez recharger (minimum 10 euros)");
	    	            			float montant=Float.parseFloat(sc.nextLine());
	    	            			if(montant>=10.0) {
	    	            				((SubCard)ui.getConnectedCard()).addCredit(montant);
	    	            				System.out.println("Rechargement réussi");
	    	            			}else {
	    	            				System.out.println("Montant insuffisant ! ");
	    	            			}
	    	            		break;
	    	            		//Retour au menu principal
	    	            		case "b" :
	    	            			gestionEnCours=false;
	    	            		break;
	    	            		}
	            			}
	            		break;
	            		//Déconnexion
	            		case "d":
		        			sessionClient1 = false;
		        			System.out.println("Déconnexion ...");
		        			ui.disconnect();
		        		break;
	            		//Voir la liste des films 
	        			case "v":
	        				System.out.println("Liste des DvDs : ");
	        				Iterator<DvD> itV = systeme.getDvds().keySet().iterator();
		        			while(itV.hasNext()) {
		        				DvD current= itV.next();
		        				System.out.println(current.getFilm().getTitle());
		        			}
	        			break;
		        		case "q":
		        			sessionClient1=false;
		        			systeme.powerOff();
		        			System.exit(0);
		        		break;
	            		}
	            	//Interface Classique
	        		}else {
	        			System.out.println("Actions disponibles : \n S'abonner : a \n Louer un dvd : l \n Rendre un dvd : r \n "
	        					+ "Voir la liste des dvds : v \n Se déconnecter : d\n Quitter : q");
	            		command = sc.nextLine();
	            		switch(command) {
	            		case "d":
		        			session = false;
		        			System.out.println("Déconnexion ...");
		        			ui.disconnect();
		        		break;
	            		//S'abonner	
	            		case "a":
	            			ui.getConnectedClient().setSub(true);
	            			System.out.println("Choisissez un identifiant de carte : ");
	            			int idCard = Integer.parseInt(sc.nextLine());
	            			ui.getConnectedClient().getMainCards().add(new MainCard((CreditCard)ui.getConnectedCard()));
	            			System.out.println("Vous êtes désormais abonné ! ");
	            		break;
	            		//Louer un dvd
	            		case "l":
	            			System.out.println("Choisissez un id de dvd parmis les suivants : ");
	        				Iterator<DvD> it = systeme.getDvds().keySet().iterator();
		        			while(it.hasNext()) {
		        				DvD current= it.next();
		        				System.out.println(current.getFilm().getTitle()+" : "+current.getFilm().getId());
		        			}
		        			int idChosen= Integer.parseInt(sc.nextLine());
		        			
		        			it = systeme.getDvds().keySet().iterator();
		        			
		        			try {
		        				DvD toRent= systeme.getDvdById(idChosen);
		        				ui.getConnectedCard().rentDvd(toRent);
		        			}catch(RentException|StatusDvdException|SystemException e) {
		        				e.printStackTrace();
		        			}
		        			System.out.println("Dvd loué");
		        			
	                	break;
	                	//Rendre un dvd
	            		case "r":
	            			System.out.println("Vos locations en cours : ");
	            			ArrayList<Rent> rents = ui.getConnectedCard().getOnGoingRent();
	            			if(rents==null) {
	            				System.out.println("Vous n'avez pas de dvd à rendre");
	            				break;
	            			}
	            			//Affichage des locations en cours
	        				for(int i=0; i<rents.size();i++) {
	        					System.out.println(rents.get(i).getDvd().getFilm().getTitle()+" : "+rents.get(i).getDvd().getId());
	        				}

	        				System.out.println("Entrez l'id du dvd que vous rendez : ");
	        				int idReturn = Integer.parseInt(sc.nextLine());
	        				Rent rent= null;

							int i=0;
	        				while (i < rents.size() && rents.get(i).getDvd().getId() != idReturn){ i++; }

							try {
								rent = rents.get(i);
							}catch (IndexOutOfBoundsException e){
								System.out.println("System error dvd not found");
								break;
							}

							System.out.println("Entrez l'etat du dvd que vous rendez : Broken|Good|Missing ");
							State state = State.valueOf(sc.nextLine());
							rent.getDvd().setState(state);
	        				try {
	            				ui.getConnectedCard().returnDvd(rent);
	        				}catch(RentException|CardException|SubCardException e) {
	        					e.printStackTrace();
	        				}
	        				System.out.println("Dvd rendu");
	                	break;
	        			//Voir la liste des dvds 
	        			case "v":
	        				System.out.println("Liste des DvDs : ");
	        				Iterator<DvD> it2 = systeme.getDvds().keySet().iterator();
		        			while(it2.hasNext()) {
		        				DvD current= it2.next();
		        				System.out.println(current.getFilm().getTitle()+" : "+current.getFilm().getId());
		        			}
	        			break;
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
}