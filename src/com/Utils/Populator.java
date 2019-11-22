package com.Utils;

import com.Model.Cards.CreditCard;
import com.Model.Client.Client;
import com.Model.Client.Human;
import com.Model.Movies.Category;
import com.Model.Movies.DvD;
import com.Model.Movies.Film;
import com.Model.Movies.State;

import java.util.*;

public abstract class Populator {

    private final static String synopsis =
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
            "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud " +
            "exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure " +
            "dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. " +
            "Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt " +
            "mollit anim id est laborum.";

    private static String generateRandomWord(int wordLength) {
        Random r = new Random();
        StringBuilder sb = new StringBuilder(wordLength);
        for (int i = 0; i < wordLength; i++) {
            char tmp = (char) ('a' + r.nextInt('z' - 'a'));
            sb.append(tmp);
        }
        return sb.toString();
    }

    private static ArrayList<Human> generateActor(){
        Random r = new Random();
        ArrayList<Human> humans = new ArrayList<>();
        int nbActor = r.nextInt((30 - 10) + 1) + 10;
        for (int i = 0; i < nbActor; i++) {
            humans.add(new Human(generateRandomWord(8),generateRandomWord(8)));
        }
        return humans;
    }

    public static HashMap<DvD, Integer> populateDvd(int nbDvd) {
        HashMap<DvD, Integer> dvds = new HashMap<>();
        Random r = new Random();
        int dvdCount = 0;
        for (int i = 0; i < nbDvd; i += dvdCount) {
            dvdCount =  r.nextInt(5);
            ArrayList<Category> categories = new ArrayList<Category>();
            categories.add(Category.randomCategory());
            categories.add(Category.randomCategory());
            dvds.put(
                    new DvD(
                        new Film(
                                generateRandomWord(8),
                                synopsis,
                                generateActor(),
                                new Human(generateRandomWord(8),generateRandomWord(8)),
                                categories
                        ),
                        State.Good,
               r.nextInt((30 - 10) + 1) + 10
                    ),
                    dvdCount
            );
        }
        return dvds;
    }

    public static ArrayList<Film> extractFilms(HashMap<DvD, Integer>dvds){
        ArrayList<Film> films = new ArrayList<>();
        for (Map.Entry<DvD, Integer> dvd : dvds.entrySet()) {
            Map.Entry elem = dvd;
            films.add(((DvD)elem.getKey()).getFilm());
        }
        return films;
    }


    public static ArrayList<Client> populateClient(int nbClient){
        ArrayList<Client> clients = new ArrayList<>();
        Random r = new Random();
        for (int i = 0; i < nbClient; i++) {
            Client client = new Client(
                    generateRandomWord(8),
                    generateRandomWord(8),
                    generateRandomWord(20),
                    new CreditCard(r.nextInt(16))
            );

            for (int j = 0; j < r.nextInt( 10); j++) {
                client.createMainCard(
                        client.getCreditCards().get(client.getCreditCards().size() -1)
                );
                for (int k = 0; k < r.nextInt(10); k++) {
                    client.getMainCards().get(client.getMainCards().size() - 1).createSlaveCard();
                }
            }
            clients.add(client);
        }
        return clients;
    }
}
