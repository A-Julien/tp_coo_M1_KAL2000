package Ui;

import Cards.Card;
import KAL2000.Client;
import KAL2000.Kal2000;

public interface Loggable {

    boolean connect(int id, String password, Kal2000 kal2000);
    void connect(Client client, Card card);

    void disconnect();
}
