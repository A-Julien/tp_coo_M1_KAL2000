package com.View;

import com.Model.Movies.Category;
import com.Model.Movies.DvD;
import com.Model.Movies.Film;
import com.Controller.Kal2000;
import com.Model.Client.Human;
import com.Model.Movies.State;
import com.Model.Exception.FilmException;

import java.util.*;

public abstract class AdminView {

	/**
	 * Allow an admin to add a film to the database.
	 * @param system kal2000 engine
	 * @param sc scanner
	 */
    public static void addFilm(Kal2000 system, Scanner sc){
        //Choix des attributs du film
        System.out.println("Entrez le titre du film :");
        String titre = sc.nextLine();

        System.out.println("Entrez le synopsis du film :");
        String synopsis = sc.nextLine();

        System.out.println("Entrez les acteurs principaux du film : ( s pour arrêter )");
        ArrayList<Human> actorsFilms = new ArrayList<>();
        boolean entreeEnCours = true;
        while (entreeEnCours) {
            String acteurs = sc.nextLine();
            if (acteurs.equals("s")) {
                entreeEnCours = false;
            } else {
                String[] nom = acteurs.split(" ");
                Human acteur = new Human(nom[0], nom[1]);
                actorsFilms.add(acteur);
            }
        }

        System.out.println("Entrez le réalisateur du film :");
        String real = sc.nextLine();
        String[] nom = real.split(" ");
        Human realisateur = new Human(nom[0], nom[1]);

        //TODO gerer multi category
        System.out.println("Entrez la catégorie principale du film : " + Category.toStringAll());
        String category = sc.nextLine();

        //Ajout a la base
        try {
            system.addFilm(new Film(titre, synopsis, actorsFilms, realisateur, null /*Category.valueOf(category)*/));
            System.out.println("Film ajouté");
        } catch (FilmException e) {
            e.printStackTrace();
        }
    }
    
	/**
	 * Allow an admin to delete a film from the database.
	 * @param system kal2000 engine
	 * @param sc scanner
	 */
    public static void removeFilm(Kal2000 system, Scanner sc){
        //Affichage de la liste des films pour la sélection de l'id
        System.out.println("Liste des films :");
        for (Film film : system.getFilms6beerVideo()) System.out.println(film.toString());

        System.out.println("Entrez l'identifiant du film à supprimer :");
        int idRemoveFilm = Integer.parseInt(sc.nextLine());

        //Recherche de l'index du film a supprimer
        for (int i = 0; i < system.getFilms6beerVideo().size(); i++) {
            if (system.getFilms6beerVideo().get(i).getId() == idRemoveFilm) {
                if (system.removeFilm(system.getFilms6beerVideo().get(i))){
                    System.out.println("Film supprimé");
                    return;
                }
            }
        }
        System.out.println("Film non trouvé !");
    }

	/**
	 * Allow an admin to add a dvd to the database.
	 * @param system kal2000 engine
	 * @param sc scanner
	 */
    //TODO A VERIFIER 
    public static void addDvd(Kal2000 system, Scanner sc){
        System.out.println("Entrez l'id du film présent sur le DVD");
        int idFilmDvd = Integer.parseInt(sc.nextLine());
        int indexFilmDvd = -1;
        for (int i = 0; i < system.getFilms6beerVideo().size(); i++) {
            if (system.getFilms6beerVideo().get(i).getId() == idFilmDvd) {
                indexFilmDvd = i;
            }
        }
        Film filmDvd;
        if (indexFilmDvd != -1) {
            filmDvd = system.getFilms6beerVideo().get(indexFilmDvd);
        } else {
            System.out.println("Film non trouvé !");
            return;
        }
        System.out.println("Entrez l'id du DVD");
        int idDvd = Integer.parseInt(sc.nextLine());

        system.addDvd(new DvD(filmDvd, State.Good, idDvd));
        System.out.println("Dvd ajouté");
    }
    
	/**
	 * Allow an admin to delete a dvd from the database.
	 * @param system kal2000 engine
	 * @param sc scanner
	 */
    public static void removeDvd(Kal2000 system, Scanner sc){
        System.out.println(system.printDvds());
        System.out.println("Entrez l'id du DVD à supprimer");
        int idDvdRemove = Integer.parseInt(sc.nextLine());

        Iterator<DvD> it = system.getDvds().keySet().iterator();
        DvD toRemove = null;
        while (it.hasNext()) {
            DvD current = it.next();
            if (current.getId() == idDvdRemove) {
                toRemove = current;
            }
        }

        if (toRemove != null) {
            system.removeDvd(toRemove);
            System.out.println("Dvd supprimé !");
        } else {
            System.out.println("Dvd non trouvé !");
        }


    }
    /**
     * Allows an admin to display stats from all films
     * @param system kal2000 engine
     */
    public static void displayStats(Kal2000 system){
        HashMap<Film, Integer> stats = system.getFilmStat();
        System.out.println("Stat des films :");
        for (Film current : stats.keySet()) {
            System.out.println(current.getTitle() + " : loué " + stats.get(current) + " fois");
        }


    }
}
