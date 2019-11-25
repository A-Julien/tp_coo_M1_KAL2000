package org.com.View;

import org.com.Model.Cards.CreditCard;
import org.com.Model.Cards.MainCard;
import org.com.Model.Cards.SlaveCard;
import org.com.Model.Cards.SubCard;
import org.com.Model.Movies.Category;
import org.com.Model.Movies.DvD;
import org.com.Controller.Kal2000;
import org.com.Model.Rents.Rent;
import org.com.Controller.UserInterface;
import org.com.Model.Movies.State;
import org.com.Model.Exception.*;
import org.com.Model.Exception.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public abstract class UserView {
	
	/**
	 * Allow a client to return a dvd, withs according checks, and make him pay.
	 * @param ui user interface
	 * @param system kal2000 engine
	 * @param sc scanner
	 */
    public static void returnDvd(UserInterface ui, Kal2000 system, Scanner sc){
        System.out.println("Vos locations en cours : ");
        if (ui.getConnectedCard().getOnGoingRent().size() == 0) {
            System.out.println("Vous n'avez pas de dvd à rendre");
            return;
        }
        System.out.println(ui.getConnectedCard().printOnGoingRent()); //Affichage des locations en cours

        System.out.println("Entrez l'id du dvd que vous rendez : ");
        int idReturn = Integer.parseInt(sc.nextLine());
        Rent rent;
        try {
            rent = ui.getConnectedCard().getRent(idReturn);
        } catch (RentException e) {
            System.out.println(e.getMessage());
            return;
        }

        System.out.println("Entrez l'etat du dvd que vous rendez : Broken|Good|Missing ");
        State state = State.valueOf(sc.nextLine());
        rent.getDvd().setState(state);
        try {
            ui.getConnectedCard().returnDvd(rent);
            system.returnDvd(rent.getDvd());
        } catch (RentException | CardException | SubCardException e) {
            e.printStackTrace();
        }
        System.out.println("Dvd rendu");
    }

    /**
     * Allow a client to rent a dvd.
	 * @param ui user interface
	 * @param system kal2000 engine
	 * @param sc scanner
     */
    public static void rentDvd(UserInterface ui, Kal2000 system, Scanner sc){
        System.out.println("Choisissez un id de dvd parmis les suivants : ");
        for (DvD dvd : system.getDvds().keySet()) {
            System.out.println(dvd.getFilm().toString());
        }
        int idChosen = Integer.parseInt(sc.nextLine());
        DvD toRent;
        try {
            toRent = system.getDvdById(idChosen);
            ui.getConnectedCard().rentDvd(toRent);
        } catch (RentException | StatusDvdException | SystemException | SlaveCardException e) {
            System.out.println(e.getMessage());
            return;
        }
        try {
            system.giveDvd(toRent);
        } catch (RentException e) {
            e.printStackTrace();
        }
        System.out.println("Dvd loué");
    }

    /**
     * Allow KAL2000 to print the film list.
     * @param system kal2000 engine
     */
    public static void printListFilmDvd(Kal2000 system){
        System.out.println("Liste des DvDs : ");
        for (Map.Entry<DvD, Integer> dvds: system.getDvds().entrySet()){
            System.out.println(dvds.getKey().getFilm().toString());
        }
    }

    /**
     * Allow KAL2000 to disconnect a client.
     * @param ui user interface
     */
    public static void disconnect(UserInterface ui){
        System.out.println("Déconnexion ...");
        ui.disconnect();
    }

    /**
     * Menu where a client can manage his subcards.
     * @param ui user interface
     * @param sc scanner
     */
    public static void subCardManaging(UserInterface ui, Scanner sc){
        String command;
        boolean gestionEnCours = true;
        while (gestionEnCours) {
            System.out.println("Actions disponibles : \n Voir le solde : v \n Recharger la carte : r  "
                    + "\n Gérer cartes filles : g \n Retour : b");
            command = sc.nextLine();
            switch (command) {


                //Gestion des cartes filles
                case "g":
                    if(!(ui.getConnectedCard() instanceof MainCard)) {
                        System.out.println("Veuillez insérer une carte mère");
                        break;
                    }

                    boolean gestionMain = true;
                    MainCard main = (MainCard) ui.getConnectedCard();
                    ArrayList<SlaveCard> slaves = main.getSlaveCards();
                    int i;
                    while(gestionMain) {
                        System.out.println("Actions disponibles : \n Limiter le nombre de locations : l \n Afficher l'historique d'une carte: a \n"
                                + " Limiter les catégories d'une carte fille : lc \n Créer une carte fille : c \n Supprimer une carte fille : s\n Retour : b");
                        command = sc.nextLine();

                        switch(command) {
                            case "a" :
                                if(slaves.isEmpty()){
                                    System.out.println("Vous n'avez pas de carte fille associé à cette carte");
                                    break;
                                }
                                System.out.println("Entrez l'id de la carte fille dont vous voulez consulter l'historique :");
                                for(SlaveCard slaveCard : slaves){
                                    System.out.println(slaveCard.toString());
                                }

                                SlaveCard slaveCard;
                                try {
                                    slaveCard = main.getSlaveCardById(Integer.parseInt(sc.nextLine()));
                                } catch (SubCardException e) {
                                    System.out.println(e.getMessage());
                                    break;
                                }

                                System.out.println("Historique : \n"+ slaveCard.getHistory().toString());
                                break;
                            case "lc":
                                if(slaves.isEmpty()){
                                    System.out.println("Vous n'avez pas de carte fille associé à cette carte");
                                    break;
                                }
                                System.out.println("Entrez l'id de la carte fille à limiter : ");
                                for (SlaveCard slavecard : slaves){
                                    System.out.print(slavecard.getId() + " ");
                                    if(slavecard.getCategories().isEmpty()) {
                                        System.out.println("Catégories autorisées -> " + Category.toStringAll());
                                    }else {
                                        System.out.println("Catégories autorisées -> " + slavecard.getCategories().toString());

                                    }
                                }
                                SlaveCard slaveCat;
                                try {
                                    slaveCat = main.getSlaveCardById(Integer.parseInt(sc.nextLine()));
                                } catch (SubCardException e) {
                                    System.out.println(e.getMessage());
                                    break;
                                }

                                System.out.println("Entrez les catégories autorisées parmis les suivantes : (Action|SF|Fantasy|Comedy|Tragedy|Romance) s pour arrêter");
                                ArrayList<Category> categories = new ArrayList<>();
                                boolean entryOngoing=true;
                                while(entryOngoing){
                                    String cat = sc.nextLine();
                                    if (cat.equals("s")){
                                        entryOngoing=false;
                                    }else{
                                        categories.add(Category.valueOf(cat));
                                    }
                                }
                                slaveCat.limitCategories(categories);
                                System.out.println("Catégories limitées");
                                break;

                            case "l" :
                                if(slaves.isEmpty()){
                                    System.out.println("Vous n'avez pas de carte fille associé à cette carte");
                                    break;
                                }

                                System.out.println("Entrez l'id de la carte fille à limiter : ");
                                for (SlaveCard slavecard : slaves){
                                    System.out.println(slavecard.toString());
                                }
                                SlaveCard slave;
                                try {
                                    slave = main.getSlaveCardById(Integer.parseInt(sc.nextLine()));
                                } catch (SubCardException e) {
                                    System.out.println(e.getMessage());
                                    break;
                                }

                                System.out.println("Entrez le nouveau nombre de locations autorisées :");
                                int nbMaxRent= Integer.parseInt(sc.nextLine());
                                try{
                                    slave.setMaxRent(nbMaxRent);
                                }catch(SubCardException e){
                                    e.printStackTrace();
                                }
                                System.out.println("Locations limitées !");
                                break;

                            //Créer carte fille
                            case "c":
                                SlaveCard slavecard = ((MainCard)ui.getConnectedCard()).createSlaveCard();
                                System.out.println("Carte fille créée");
                                System.out.println("\tINFO CARD -> " + slavecard.toString());
                                break;

                            case "s":
                                if(slaves.isEmpty()){
                                    System.out.println("Vous n'avez pas de carte fille associé à cette carte");
                                    break;
                                }
                                System.out.println("Entrez l'id de la carte fille à supprimer : ");
                                for (SlaveCard s : slaves){
                                    System.out.println(s.toString());
                                }
                                try {
                                    main.deleteSlaveCardById(Integer.parseInt(sc.nextLine()));
                                } catch (SubCardException e) {
                                    System.out.println(e.getMessage());
                                }
                                System.out.println("Carte fille supprimée");
                                break;

                            //Retour
                            case "b":
                                gestionMain = false;
                                break;
                        }

                    }

                    break;
                //Afficher le solde de la carte
                case "v":
                    System.out.println("Solde actuel : " + ((SubCard) ui.getConnectedCard()).getCredit() + " euros");
                    break;

                //Rechargement du solde
                case "r":
                    refuelCredit(ui, sc);
                    break;
                //Retour au menu principal
                case "b":
                    gestionEnCours = false;
                    break;
            }
        }
    }

    public static void refuelCredit(UserInterface ui, Scanner sc){
        System.out.println("Entrez le montant que vous voulez recharger (minimum 10 euros)");
        float montant = Float.parseFloat(sc.nextLine());
        if (montant >= 10.0) {
            ((SubCard) ui.getConnectedCard()).addCredit(montant);
            System.out.println("Rechargement réussi");
        } else {
            System.out.println("Montant insuffisant ! ");
        }
    }

    /**
     * Allow a client to be sub.
     * @param ui user interface
     */
    public static void subscription(UserInterface ui){
        ui.getConnectedClient().getMainCards().add(new MainCard((CreditCard) ui.getConnectedCard()));
        ui.getConnectedClient().setSub(true);
        System.out.println("Vous êtes désormais abonné ! Votre carte a pour id : "
                + ui.getConnectedClient()
                    .getMainCards()
                    .get(ui.getConnectedClient().getMainCards().size() - 1).getId());
        ui.connect(ui.getConnectedClient(),ui.getConnectedClient().getMainCards().get(ui.getConnectedClient().getMainCards().size() - 1));

    }
}
