package Model;

import Cards.Card;
import KAL2000.Client;
import KAL2000.Kal2000;
import Exception.CardException;
import Exception.PasswordException;


public interface Loggable {

    void connect(int id, String password, Kal2000 kal2000) throws PasswordException, CardException;
    void connect(Client client, Card card);

    void disconnect();
}
