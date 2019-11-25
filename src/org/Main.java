package org;

import org.com.Controller.Kal2000;
import org.com.Model.Cards.SubCard;
import org.com.Controller.UserInterface;
import org.com.View.AdminView;
import org.com.View.ConnectionView;
import org.com.View.UserView;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws InterruptedException, IOException, ClassNotFoundException {
        boolean session = false;
        boolean admin;

        //Initialisation
        Kal2000 system = new Kal2000();
        system.boot();
        UserInterface ui = new UserInterface();
        Scanner sc = new Scanner(System.in);

        /********************************************************/
        //            INTERFACE CONNECTION
        /********************************************************/
        while (true) {
            admin = false;
            System.out.println("-------------------------------------------------");
            System.out.println("Êtes vous déja client ?\n Se logger : l \n Créer un compte : c\n Quitter : q");
            System.out.println("-------------------------------------------------");
            String command = sc.nextLine();

            //Traitement de la commande
            switch (command) {
                case "l":
                    if (!ConnectionView.logging(system,ui,sc)) continue;
                    if (ui.isAdmin()) admin = true;
                    session = true;
                    break;

                case "c":
                    if(!ConnectionView.creatClient(system,ui,sc)) continue;
                    session = true;
                    break;
                case "q":
                    system.powerOff();
                    System.exit(0);
                    break;
                default:
                    continue;
            }

            /********************************************************/
            //            INTERFACE ADMIN
            /********************************************************/
            if (admin) {
                while (session) {
                    System.out.println("-------------------------------------------------");
                    System.out.println("Vous êtes administrateur");
                    System.out.println("Actions disponibles : \n Ajouter un film : af \n Retirer un film : rf \n"
                            + " Ajouter un dvd : ad \n Retirer un dvd : rd \n Afficher les stats des films : as \n Se déconnecter : d\n Quitter : q ");
                    System.out.println("-------------------------------------------------");
                    command = sc.nextLine();
                    switch (command) {
                        case "d":
                            session = false;
                            UserView.disconnect(ui);
                            break;
                        //Ajout d'un film
                        case "af":
                            AdminView.addFilm(system,sc);
                            break;
                        //Supprimer un film
                        case "rf":
                            AdminView.removeFilm(system,sc);
                            break;
                        //Ajout d'un DVD
                        case "ad":
                            AdminView.addDvd(system,sc);
                            break;
                        //Suppression d'un DVD
                        case "rd":
                            AdminView.removeDvd(system,sc);
                            break;
                        case "as":
                            AdminView.displayStats(system);
                            break;
                        case "q":
                            session = false;
                            system.powerOff();
                            System.exit(0);
                            break;
                    }
                }

            } else {
                /********************************************************/
                //            INTERFACE ABONNE
                /********************************************************/
                boolean sessionClient1 = true;
                while (sessionClient1) {
                    System.out.println("-------------------------------------------------");
                    System.out.println("Bienvenue " + ui.getConnectedClient().getPerson().getFirstName() + " " +
                            ui.getConnectedClient().getPerson().getLastName());
                    //Interface Sub
                    if (ui.getConnectedClient().isSub()) {
                        System.out.println("Vous êtes abonné.");
                        System.out.println("Credit : " +  ((SubCard)ui.getConnectedCard()).getCredit() + " euros");
                        String menu = "Actions disponibles : \n Louer un dvd : l \n Rendre un dvd : r \n " +
                                "Voir la liste des dvds : v \n Gérer la carte sub : g \n Se déconnecter : d\n " +
                                "Quitter : q";
                        if(ui.isSlave())
                            menu = "Actions disponibles : \n Louer un dvd : l \n Rendre un dvd : r \n " +
                                    "Voir la liste des dvds : v\n Recharger sodle : s" +
                                    " \n Se déconnecter : d\n Quitter : q";
                        System.out.println(menu);
                        System.out.println("-------------------------------------------------");
                        command = sc.nextLine();
                        switch (command) {
                            //Louer un dvd
                            case "l":
                                UserView.rentDvd(ui,system,sc);
                                break;
                            //Rendre un dvd
                            case "r":
                                UserView.returnDvd(ui,system,sc);
                                break;
                            //Gestion de la carte sub
                            case "g":
                                if(!ui.isSlave()) UserView.subCardManaging(ui,sc);
                                break;
                            case "s":
                                if(ui.isSlave()) UserView.refuelCredit(ui, sc);
                                break;
                            //Déconnexion
                            case "d":
                                sessionClient1 = false;
                                UserView.disconnect(ui);
                                break;
                            //Voir la liste des films
                            case "v":
                                UserView.printListFilmDvd(system);
                                break;
                            case "q":
                                sessionClient1 = false;
                                system.powerOff();
                                System.exit(0);
                                break;
                        }
                    /********************************************************/
                    //                 INTERFACE NON SUB
                    /********************************************************/
                    } else {
                        System.out.println("Actions disponibles : \n S'abonner : a \n Louer un dvd : l \n Rendre un dvd : r \n "
                                + "Voir la liste des dvds : v \n Se déconnecter : d\n Quitter : q");
                        System.out.println("-------------------------------------------------");
                        command = sc.nextLine();
                        switch (command) {
                            case "d":
                                session = false;
                                sessionClient1 = false;
                                System.out.println("Déconnexion ...");
                                ui.disconnect();
                                break;
                            //S'abonner
                            case "a":
                                UserView.subscription(ui);
                                break;
                            //Louer un dvd
                            case "l":
                                UserView.rentDvd(ui,system,sc);
                                break;
                            //Rendre un dvd
                            case "r":
                                UserView.returnDvd(ui,system,sc);
                                break;
                            //Voir la liste des dvds
                            case "v":
                                UserView.printListFilmDvd(system);
                                break;
                            case "q":
                                session = false;
                                system.powerOff();
                                System.exit(0);
                                break;
                        }
                    }
                }
            }
        }
    }
}