package Ui;

import Cards.Card;
import KAL2000.Client;
import KAL2000.Kal2000;

public class UserInterface implements Logging{
    private Card connectedCard;
    private Client connectedClient;


    public Card getConnectedCard() {
        return connectedCard;
    }
    public Client getConnectedClient() {
        return connectedClient;
    }


    public void createClient(String password, int idCreditCard, Kal2000 kal2000){

    }

    @Override
    public void connect(int id, String password, Kal2000 kal2000){

    }

    @Override
    public void disconnect(){}
}
