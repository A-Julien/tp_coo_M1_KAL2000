package com.View;

import com.Model.Cards.CreditCard;
import com.Model.Cards.MainCard;
import com.Model.Cards.SubCard;
import com.Model.Movies.DvD;
import com.Controleur.Kal2000;
import com.Model.Rents.Rent;
import com.Controleur.UserInterface;
import com.Model.Movies.State;
import com.Model.Exception.*;

import java.util.Map;
import java.util.Scanner;

public abstract class UserView {
    public static void returnDvd(UserInterface ui, Kal2000 system, Scanner sc){
        System.out.println("Vos locations en cours : ");
        if (ui.getConnectedCard().getOnGoingRent().size() == 0) {
            System.out.println("Vous n'avez pas de dvd à rendre");
            return;
        }
        System.out.println(ui.getConnectedCard().printOnGoingRent()); //Affichage des locations en cours

        System.out.println("Entrez l'id du dvd que vous rendez : ");
        int idReturn = Integer.parseInt(sc.nextLine());
        Rent rent = null;
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

    public static void rentDvd(UserInterface ui, Kal2000 system, Scanner sc){
        System.out.println("Choisissez un id de dvd parmis les suivants : ");
        for (DvD dvd : system.getDvds().keySet()) {
            System.out.println(dvd.getFilm().toString());
        }
        int idChosen = Integer.parseInt(sc.nextLine());

        try {
            DvD toRent = system.getDvdById(idChosen);
            ui.getConnectedCard().rentDvd(toRent);
            system.giveDvd(toRent);
        } catch (RentException | StatusDvdException | SystemException e) {
            e.printStackTrace();
            return;
        }
        System.out.println("Dvd loué");
    }

    public static void printListFilmDvd(Kal2000 system){
        System.out.println("Liste des DvDs : ");
        for (Map.Entry<DvD, Integer> dvds: system.getDvds().entrySet()){
            System.out.println(dvds.getKey().getFilm().toString());
        }
    }

    public static void disconnect(UserInterface ui){
        System.out.println("Déconnexion ...");
        ui.disconnect();
    }

    public static void subCardManaging(UserInterface ui, Scanner sc){
        String command;
        boolean gestionEnCours = true;
        while (gestionEnCours) {
            System.out.println("Actions disponibles : \n Voir le solde : v \n Recharger la carte : r \n Créer une carte fille : c "
                    + "\n Gérer cartes filles : g \n Retour : b");
            command = sc.nextLine();
            switch (command) {

                //Afficher le solde de la carte
                case "v":
                    try {
                        System.out.println("Solde actuel : " + ((SubCard) ui.getConnectedCard()).getCredit() + " euros");
                    } catch (SubCardException e) {
                        e.printStackTrace();
                    }
                    break;

                //Rechargement du solde
                case "r":
                    System.out.println("Entrez le montant que vous voulez recharger (minimum 10 euros)");
                    float montant = Float.parseFloat(sc.nextLine());
                    if (montant >= 10.0) {
                        ((SubCard) ui.getConnectedCard()).addCredit(montant);
                        System.out.println("Rechargement réussi");
                    } else {
                        System.out.println("Montant insuffisant ! ");
                    }
                    break;
                //Retour au menu principal
                case "b":
                    gestionEnCours = false;
                    break;
            }
        }
    }

    public static void subscription(UserInterface ui){
        ui.getConnectedClient().getMainCards().add(new MainCard((CreditCard) ui.getConnectedCard()));
        ui.getConnectedClient().setSub(true);
        System.out.println("Vous êtes désormais abonné ! Votre carte a pour id : "
                + ui.getConnectedClient()
                    .getMainCards()
                    .get(ui.getConnectedClient().getMainCards().size() - 1).getId());
    }
}
