package org.com.Utils;

import org.com.Model.Cards.Card;
import org.com.Model.Cards.MainCard;
import org.com.Model.Client.Client;
import org.com.Model.Exception.RentException;
import org.com.Model.Movies.Film;
import org.com.Model.Rents.Rent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * Class MetaDataFormatter which methods allow to get calculated informations to  client(s)
 */
public class MetaDataFormatter {
    private Client client;
    private HashMap<Card, ArrayList<Rent>> history;

    public MetaDataFormatter(Client client) {
        this.client = client;
        this.history = new HashMap<>();
        this.buildHistory(client);
    }

    private void buildHistory(Client client){
        for( MainCard card : client.getMainCards()){
            this.history.put(card, card.getHistory());
            for ( Card sub : card.getSlaveCards()){
                this.history.put(sub, card.getHistory());
            }
        }
        for(Card card : client.getCreditCards()){
            this.history.put(card, card.getHistory());
        }
    }

    public HashMap<Card, ArrayList<Rent>> getHistory() {
        return history;
    }

    public Client getClient() {
        return client;
    }

    public long getRentPeriodMoy() {
        int moy = 0;

        for (Map.Entry<Card, ArrayList<Rent>> cardArrayListEntry : this.history.entrySet()) {
            ArrayList<Rent> rents = cardArrayListEntry.getValue();
            for (Rent rent : rents) {
                try {
                    moy += Card.getDateDiff(rent.getDateReturn(), rent.getDateRent(), TimeUnit.DAYS);
                } catch (RentException ignored) {}
            }
        }
        return moy/this.history.size();
    }

    public float getRentPriceMoy(){
        int moy = 0;

        for (Map.Entry<Card, ArrayList<Rent>> cardArrayListEntry : this.history.entrySet()) {
            ArrayList<Rent> rents = cardArrayListEntry.getValue();
            for (Rent rent : rents) {
                try {
                    moy += rent.getPrice();
                } catch (RentException ignored) {}
            }
        }
        return moy/(float)this.history.size();
    }

    public HashMap<Film, Integer> getFilmStat(){
        HashMap<Film, Integer> stats = new HashMap<>();

        for (Map.Entry<Card, ArrayList<Rent>> cardArrayListEntry : this.history.entrySet()) {
            ArrayList<Rent> rents = cardArrayListEntry.getValue();
            for (Rent rent : rents) {
                stats.put(rent.getDvd().getFilm(), rent.getDvd().getFilm().getNbRented());
            }
        }
        return stats;
    }

    @Override
    public String toString() {
        StringBuilder cards = new StringBuilder();

        for (Map.Entry<Card, ArrayList<Rent>> cardArrayListEntry : this.history.entrySet()) {
            ArrayList<Rent> h = cardArrayListEntry.getValue();
            Card card = cardArrayListEntry.getKey();
            cards.append("\t").append(card.getId()).append("\n");
            for (Rent rent : h) {
                cards.append("\t\t").append(rent.toString()).append("\n");
            }
        }

        return client.toString() + "\n" + cards;
    }
}
